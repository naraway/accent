/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenOptions {
    //
    RdbProperty[] properties() default {};
    String[] notNull() default {};
    String[] immutable() default {};

    /**
     * Updatable field meta descriptor
     *
     * @deprecated This descriptor is no longer acceptable in next version.
     * Use {@link FieldImmutable}, {@link FieldNotNull} instead.
     */
    @Deprecated(since = "4.1.0", forRemoval = true)
    String[] updatable() default {}; // NOSONAR
}