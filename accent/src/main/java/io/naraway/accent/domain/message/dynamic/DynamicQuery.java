/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.message.dynamic;

import java.util.List;

public interface DynamicQuery<T> {
    //
    T findOne();
    List<T> findAll();
}
