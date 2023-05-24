/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.entity.entity;

import io.naraway.accent.domain.entity.CopyDataObject;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@SuppressWarnings("java:S2160")
public class DerivedFooCdo extends CopyDataObject<Foo> {
    //
    public DerivedFooCdo(Foo sourceEntity) {
        this.sourceEntity = sourceEntity;
    }
}
