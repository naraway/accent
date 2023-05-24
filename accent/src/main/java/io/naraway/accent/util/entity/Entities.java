/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.entity;

import io.naraway.accent.domain.annotation.Enum;
import io.naraway.accent.domain.annotation.*;
import io.naraway.accent.domain.entity.DomainEntity;
import io.naraway.accent.domain.type.NameValue;
import io.naraway.accent.domain.type.NameValueList;
import io.naraway.accent.util.clazz.ClassUtils;
import io.naraway.accent.util.crypto.ValueEncryptor;
import io.naraway.accent.util.json.JsonUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Entities {
    //
    public static Set<String> getAttributes(DomainEntity entity) {
        //
        if (entity == null) {
            return Collections.emptySet();
        }

        return Beans.getProperties(entity.getClass());
    }

    public static Set<String> getUpdatableAttributes(DomainEntity entity) {
        //
        if (entity == null) {
            return Collections.emptySet();
        }

        return getUpdatableProperties(entity.getClass());
    }

    public static Set<String> getEncryptAttributes(DomainEntity entity) {
        //
        if (entity == null) {
            return Collections.emptySet();
        }

        return getEncryptProperties(entity.getClass());
    }

    public static NameValueList getModifiedNameValues(DomainEntity oldEntity, DomainEntity newEntity) {
        //
        Set<String> updatableAttributes = getUpdatableAttributes(oldEntity);

        NameValueList nameValues = NameValueList.newInstance();
        updatableAttributes.forEach(attribute -> {
            Object oldValue = Beans.readValue(oldEntity, attribute);
            Object newValue = Beans.readValue(newEntity, attribute);

            if (!Objects.deepEquals(oldValue, newValue)) {
                if (isCompositeType(oldEntity, attribute)) {
                    nameValues.add(new NameValue(attribute, newValue == null ? null : JsonUtil.toJson(newValue)));
                } else {
                    nameValues.add(new NameValue(attribute, newValue == null ? null : newValue.toString()));
                }
            }
        });

        return nameValues;
    }

    public static <T> T generateStub(Class entityClazz) {
        //
        Method sampleMethod = null;
        Method[] methods = entityClazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals("sample") &&
                    Modifier.isStatic(method.getModifiers()) &&
                    Modifier.isPublic(method.getModifiers()) &&
                    method.getParameterCount() == 0) {
                sampleMethod = method;
                break;
            }
        }

        if (sampleMethod != null) {
            try {
                return (T) sampleMethod.invoke(null);
            } catch (Exception e) {
                // skip error
            }
        }

        Constructor noArgsConstructor = null;
        Constructor[] constructors = entityClazz.getConstructors();
        for (Constructor constructor : constructors) {
            if (Modifier.isPublic(constructor.getModifiers()) &&
                    constructor.getParameterCount() == 0) {
                noArgsConstructor = constructor;
                break;
            }
        }

        if (noArgsConstructor != null) {
            try {
                return (T) noArgsConstructor.newInstance();
            } catch (Exception e) {
                // skip error
            }
        }

        return null;
    }

    @SuppressWarnings("java:S3776")
    private static Set<String> getUpdatableProperties(Class<? extends DomainEntity> clazz) {
        //
        if (isLegacyDescriptorUsed(clazz)) {
            return getUpdatablePropertiesForLegacy(clazz);
        }

        Set<String> properties = new HashSet<>();

        Set<String> immutables = new HashSet<>();

        // class annotation
        Annotation entityImmutableAnnotation = clazz.getAnnotation(EntityImmutable.class);
        if (entityImmutableAnnotation != null) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                immutables.add(field.getName());
            }
        }

        Annotation genOptionsAnnotation = clazz.getAnnotation(GenOptions.class);
        if (genOptionsAnnotation != null) {
            try {
                String[] immutableProperties = (String[]) genOptionsAnnotation
                        .annotationType().getMethod("immutable").invoke(genOptionsAnnotation);
                if (immutableProperties != null && immutableProperties.length > 0) {
                    immutables.addAll(Arrays.asList(immutableProperties));
                }
            } catch (Exception e) {
                // skip
            }
        }

        // field annotation
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation fieldImmutableAnnotation = field.getAnnotation(FieldImmutable.class);
            if (fieldImmutableAnnotation != null) {
                immutables.add(field.getName());
            }

            Annotation fieldSourceId = field.getAnnotation(FieldSourceId.class);
            if (fieldSourceId != null) {
                immutables.add(field.getName());
            }
        }

        for (Field field : fields) {
            if (!immutables.contains(field.getName())) {
                properties.add(field.getName());
            }
        }

        return properties;
    }

    private static Set<String> getUpdatablePropertiesForLegacy(Class<? extends DomainEntity> clazz) {
        //
        Set<String> properties = new HashSet<>();

        // class annotation
        Annotation classAnnotation = clazz.getAnnotation(GenOptions.class);
        if (classAnnotation != null) {
            try {
                String[] updatableProperties = (String[]) classAnnotation
                        .annotationType().getMethod("updatable").invoke(classAnnotation);
                if (updatableProperties != null && updatableProperties.length > 0) {
                    properties.addAll(Arrays.asList(updatableProperties));
                }
            } catch (Exception e) {
                // skip
            }
        }

        // field annotation
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation fieldAnnotation = field.getAnnotation(Updatable.class); // NOSONAR
            if (fieldAnnotation != null) {
                properties.add(field.getName());
            }
        }

        return properties;
    }

    private static boolean isLegacyDescriptorUsed(Class<? extends DomainEntity> clazz) {
        //
        Annotation classAnnotation = clazz.getAnnotation(GenOptions.class);
        if (classAnnotation != null) {
            try {
                String[] updatableProperties = (String[]) classAnnotation
                        .annotationType().getMethod("updatable").invoke(classAnnotation);
                if (updatableProperties != null && updatableProperties.length > 0) {
                    return true;
                }
            } catch (Exception e) {
                // skip
            }
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            Annotation updatableAnnotation = field.getAnnotation(Updatable.class); // NOSONAR
            if (updatableAnnotation != null) {
                return true;
            }

            Annotation enumAnnotation = field.getAnnotation(Enum.class); // NOSONAR
            if (enumAnnotation != null) {
                return true;
            }
        }

        return false;
    }

    public static void encrypt(DomainEntity entity) {
        //
        encryptBean(entity);

        Set<String> fields = Beans.getProperties(entity.getClass());
        fields.forEach(field -> {
            if (isCompositeType(entity, field)) {
                encryptBean(Beans.readValue(entity, field));
            }
        });
    }

    public static void decrypt(DomainEntity entity) {
        //
        decryptBean(entity);

        Set<String> properties = Beans.getProperties(entity.getClass());
        properties.forEach(property -> {
            if (isCompositeType(entity, property)) {
                decryptBean(Beans.readValue(entity, property));
            }
        });
    }

    private static void encryptBean(Object bean) {
        //
        Set<String> properties = getEncryptProperties(bean.getClass());

        properties.forEach(property -> {
            Field field = getField(bean, property);

            if (field.getType().equals(String.class)) {
                String originalValue = (String) Beans.readValue(bean, property);
                String encryptedValue = ValueEncryptor.encrypt(originalValue);
                Beans.writeValue(bean, property, encryptedValue);
            } else {
                if (isCompositeType(bean, property)) {
                    encryptBean(Beans.readValue(bean, property));
                }
            }
        });
    }

    private static void decryptBean(Object bean) {
        //
        Set<String> properties = getEncryptProperties(bean.getClass());

        properties.forEach(property -> {
            Field field = getField(bean, property);

            if (field.getType().equals(String.class)) {
                String originalValue = (String) Beans.readValue(bean, property);
                String decryptedValue = ValueEncryptor.decrypt(originalValue);
                Beans.writeValue(bean, property, decryptedValue);
            } else {
                if (isCompositeType(bean, property)) {
                    decryptBean(Beans.readValue(bean, property));
                }
            }
        });
    }

    private static Set<String> getEncryptProperties(Class clazz) {
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

    private static boolean isCompositeType(Object bean, String property) {
        //
        Field field = getField(bean, property);

        if (field == null) {
            return false;
        }

        return ClassUtils.isCompositeType(field.getType());
    }

    private static Field getField(Object bean, String property) {
        //
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(property)) {
                return field;
            }
        }

        return null;
    }
}
