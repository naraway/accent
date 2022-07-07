package io.naraway.accent.util.entity;

import io.naraway.accent.domain.ddd.*;
import io.naraway.accent.domain.type.NameValue;
import io.naraway.accent.domain.type.NameValueList;
import io.naraway.accent.util.json.JsonUtil;
import io.naraway.accent.util.security.bcrypt.EncryptionUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EntityUtil {
    private EntityUtil() {
        //
    }

    public static NameValueList genNameValueList(DomainEntity oldEntity, DomainEntity newEntity) {
        //
        Set<String> updatableFields = extractUpdatableFields(oldEntity.getClass());

        NameValueList nameValues = NameValueList.newEmptyInstance();
        updatableFields.forEach(field -> {
            Object oldValue = readEntityValue(oldEntity, field);
            Object newValue = readEntityValue(newEntity, field);

            if (!Objects.deepEquals(oldValue, newValue)) {
                boolean compositeType =
                        !ReflectionUtils.findField(oldEntity.getClass(), field).getType().isPrimitive()
                                && !ReflectionUtils.findField(oldEntity.getClass(), field).getType().isEnum()
                                && !ReflectionUtils.findField(oldEntity.getClass(), field).getType().equals(String.class);

                if (compositeType) {
                    nameValues.add(new NameValue(field, JsonUtil.toJson(newValue)));
                } else {
                    nameValues.add(new NameValue(field, newValue.toString()));
                }
            }
        });
        return nameValues;
    }

    private static Object readEntityValue(Object entity, String field) {
        //
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(entity.getClass(), field);
        try {
            return Objects.requireNonNull(propertyDescriptor).getReadMethod().invoke(entity);
        } catch (Exception e) {
            // skip
        }
        return null;
    }

    private static Object writeEntityValue(Object entity, String field, Object... args) {
        //
        PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(entity.getClass(), field);
        try {
            return Objects.requireNonNull(propertyDescriptor).getWriteMethod().invoke(entity, args);
        } catch (Exception e) {
            // skip
        }
        return null;
    }

    private static Set<String> extractUpdatableFields(Class<? extends DomainEntity> clazz) {
        //
        Set<String> updatableFieldNames = new HashSet<>();

        // class annotation
        Annotation classAnnotation = clazz.getAnnotation(GenOptions.class);
        if (classAnnotation != null) {
            try {
                String[] fields = (String[]) classAnnotation.annotationType().getMethod("updatable").invoke(classAnnotation);
                if (fields != null && fields.length > 0) {
                    updatableFieldNames.addAll(Arrays.asList(fields));
                }
            } catch (Exception e) {
                // skip
            }
        }

        // field annotation
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation fieldAnnotation = field.getAnnotation(Updatable.class);
            if (fieldAnnotation != null) {
                updatableFieldNames.add(field.getName());
            }
        }
        return updatableFieldNames;
    }

    public static void encrypt(DomainEntity entity) {
        //
        encryptBean(entity);

        Set<String> fields = extractFields(entity.getClass());
        fields.forEach(field -> {
            boolean compositeType =
                    !ReflectionUtils.findField(entity.getClass(), field).getType().isPrimitive()
                            && !ReflectionUtils.findField(entity.getClass(), field).getType().isEnum()
                            && !ReflectionUtils.findField(entity.getClass(), field).getType().equals(String.class);
            if (compositeType) {
                encryptBean(readEntityValue(entity, field));
            }
        });
    }

    public static void decrypt(DomainEntity entity) {
        //
        decryptBean(entity);

        Set<String> fields = extractFields(entity.getClass());
        fields.forEach(field -> {
            boolean compositeType =
                    !ReflectionUtils.findField(entity.getClass(), field).getType().isPrimitive()
                            && !ReflectionUtils.findField(entity.getClass(), field).getType().isEnum()
                            && !ReflectionUtils.findField(entity.getClass(), field).getType().equals(String.class);
            if (compositeType) {
                decryptBean(readEntityValue(entity, field));
            }
        });
    }

    private static void encryptBean(Object bean) {
        //
        Set<String> encryptFields = extractEncryptFields((Class<Object>) bean.getClass());

        encryptFields.forEach(field -> {
            if (ReflectionUtils.findField(bean.getClass(), field).getType().equals(String.class)) {
                String originalValue = (String) readEntityValue(bean, field);
                String encryptedValue = EncryptionUtil.encrypt(originalValue);
                writeEntityValue(bean, field, encryptedValue);
            } else {
                boolean compositeType =
                        !ReflectionUtils.findField(bean.getClass(), field).getType().isPrimitive()
                                && !ReflectionUtils.findField(bean.getClass(), field).getType().isEnum()
                                && !ReflectionUtils.findField(bean.getClass(), field).getType().equals(String.class);
                if (compositeType) {
                    encryptBean(readEntityValue(bean, field));
                }
            }
        });
    }

    private static void decryptBean(Object bean) {
        //
        Set<String> encryptFields = extractEncryptFields((Class<Object>) bean.getClass());

        encryptFields.forEach(field -> {
            if (ReflectionUtils.findField(bean.getClass(), field).getType().equals(String.class)) {
                String originalValue = (String) readEntityValue(bean, field);
                String decryptedValue = EncryptionUtil.decrypt(originalValue);
                writeEntityValue(bean, field, decryptedValue);
            } else {
                boolean compositeType =
                        !ReflectionUtils.findField(bean.getClass(), field).getType().isPrimitive()
                                && !ReflectionUtils.findField(bean.getClass(), field).getType().isEnum()
                                && !ReflectionUtils.findField(bean.getClass(), field).getType().equals(String.class);
                if (compositeType) {
                    decryptBean(readEntityValue(bean, field));
                }
            }
        });
    }

    private static Set<String> extractFields(Class<? extends DomainEntity> clazz) {
        //
        Set<String> fieldNames = new HashSet<>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            fieldNames.add(field.getName());
        }
        return fieldNames;
    }

    private static Set<String> extractEncryptFields(Class<Object> clazz) {
        //
        Set<String> encryptFieldNames = new HashSet<>();

        // field annotation
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            Annotation fieldAnnotation = field.getAnnotation(Encrypt.class);
            if (fieldAnnotation != null) {
                encryptFieldNames.add(field.getName());
            }
        }
        return encryptFieldNames;
    }

    // test

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @SuppressWarnings("java:S2160")
    private static class TestEntity extends DomainEntity {
        //
        @Updatable
        private String name;
        @Encrypt
        private String password;
        @Encrypt
        private String socialNumber;
        @Updatable
        private PhoneCodeName phoneCodeName;

        @Override
        protected void modifyAttributes(NameValueList nameValues) {
            throw new UnsupportedOperationException();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class PhoneCodeName implements ValueObject {
        //
        private String code;
        @Encrypt
        private String name;
    }

    public static void main(String[] args) {
        //
        String secret = "nara-secret";
        System.getProperties().setProperty(EncryptionUtil.SECRET_PROPS, secret);

        TestEntity testEntity = new TestEntity("name", "password", "99999999", new PhoneCodeName("home", "+821010001000"));

        // encrypt
        System.out.println("encrypt - original: " + testEntity.toPrettyJson());
        encrypt(testEntity);
        System.out.println("encrypt - encrypted: " + testEntity.toPrettyJson());
        decrypt(testEntity);
        System.out.println("encrypt - decrypted: " + testEntity.toPrettyJson());

        TestEntity testEntityEdited = JsonUtil.fromJson(testEntity.toJson(), TestEntity.class);
        testEntityEdited.getPhoneCodeName().setCode("work");
        testEntityEdited.setName("other name");
        System.out.println("nameValues: " + JsonUtil.toPrettyJson(genNameValueList(testEntity, testEntityEdited)));
    }
}
