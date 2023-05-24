/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Enumeration field meta descriptor
 *
 * @deprecated This descriptor is no longer acceptable in next version.
 * Use {@link FieldEnum} instead.
 */
@Deprecated(since = "4.1.0", forRemoval = true)
@Target({ElementType.FIELD})
public @interface Enum { // NOSONAR
    // marker interface
}