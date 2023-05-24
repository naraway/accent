/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Beans {
    //
    public static boolean hasGetter(Object bean, String property) {
        //
        return getGetter(bean, property) != null;
    }

    public static boolean hasSetter(Object bean, String property) {
        //
        return getSetter(bean, property) != null;
    }

    public static Object readValue(Object bean, String property) {
        //
        if (hasGetter(bean, property)) {
            Method getter = getGetter(bean, property);
            try {
                if (getter != null) {
                    return getter.invoke(bean);
                }
            } catch (Exception e) {
                // skip
            }
        } else {
            throw new IllegalArgumentException("No such property on bean, property = " + property);
        }

        return null;
    }

    public static Object writeValue(Object bean, String property, Object... values) {
        //
        if (hasSetter(bean, property)) {
            Method setter = getSetter(bean, property);
            try {
                if (setter != null) {
                    return setter.invoke(bean, values);
                }
            } catch (Exception e) {
                // skip
            }
        } else {
            throw new IllegalArgumentException("No such property on bean, property = " + property);
        }

        return null;
    }

    private static Method getGetter(Object bean, String property) {
        //
        Method[] methods = bean.getClass().getMethods();

        Method getter = null;
        for (Method method : methods) {
            String getterMethodName = String.format("get%s%s",
                    property.substring(0, 1).toUpperCase(), property.substring(1));
            String getterMethodNameForBoolean = String.format("is%s%s",
                    property.substring(0, 1).toUpperCase(), property.substring(1));

            if (method.getParameters().length == 0 &&
                    (method.getName().equals(getterMethodName) ||
                            method.getName().equals(getterMethodNameForBoolean))) {
                getter = method;
                break;
            }
        }

        return getter;
    }

    private static Method getSetter(Object bean, String property) {
        //
        Method[] methods = bean.getClass().getMethods();

        Method setter = null;
        for (Method method : methods) {
            String setterMethodName = String.format("set%s%s",
                    property.substring(0, 1).toUpperCase(), property.substring(1));

            if (method.getParameters().length == 1 && method.getName().equals(setterMethodName)) {
                setter = method;
                break;
            }
        }

        return setter;
    }

    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        //
        Set<String> ignores;
        if (ignoreProperties == null) {
            ignores = Collections.emptySet();
        } else {
            ignores = new HashSet<>(Arrays.asList(ignoreProperties));
        }

        Set<String> sourceProperties = getProperties(source.getClass());

        for (String attribute : sourceProperties) {
            if (ignores.contains(attribute) ||
                    !Beans.hasGetter(source, attribute) ||
                    !Beans.hasSetter(target, attribute)) {
                continue;
            }

            Object value = Beans.readValue(source, attribute);
            Beans.writeValue(target, attribute, value);
        }
    }

    public static Set<String> getProperties(Class clazz) {
        //
        Set<String> proerties = new HashSet<>();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            proerties.add(field.getName());
        }

        return proerties;
    }
}
