/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.clazz;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassUtils {
    //
    private static final Map<Class<?>, Class<?>> WRAPPER_TYPE_MAP = Map.of(
            Integer.class, int.class,
            Byte.class, byte.class,
            Character.class, char.class,
            Boolean.class, boolean.class,
            Double.class, double.class,
            Float.class, float.class,
            Long.class, long.class,
            Short.class, short.class,
            Void.class, void.class
    );

    public static boolean isPrimitiveType(Object value) {
        //
        return value == null || isPrimitiveType(value.getClass());
    }

    public static boolean isPrimitiveType(Class clazz) {
        //
        return clazz == null || WRAPPER_TYPE_MAP.containsKey(clazz);
    }

    public static boolean isCompositeType(Object value) {
        //
        return isCompositeType(value.getClass());
    }

    public static boolean isCompositeType(Class clazz) {
        //
        return (clazz != null)
                && !isPrimitiveType(clazz)
                && !clazz.isEnum()
                && !String.class.equals(clazz)
                && !LocalDate.class.equals(clazz)
                && !LocalTime.class.equals(clazz)
                && !LocalDateTime.class.equals(clazz)
                && !OffsetDateTime.class.equals(clazz);
    }
}
