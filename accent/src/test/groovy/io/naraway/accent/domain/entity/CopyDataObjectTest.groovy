/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.entity

import io.naraway.accent.domain.entity.entity.DerivedFoo
import io.naraway.accent.domain.entity.entity.DerivedFooCdo
import io.naraway.accent.domain.entity.entity.Foo
import io.naraway.accent.domain.entity.entity.FooCdo
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class CopyDataObjectTest extends Specification {
    @Subject
    CopyDataObject beans

    @Shared
    Foo entity

    //
    def setup() {
        entity = new Foo(FooCdo.sample())
    }

    //
    def 'copy entity attributes except some attributes'() {
        given:
        def cdo = new DerivedFooCdo(entity)
        cdo.sourceEntity.name = 'name1'
        cdo.sourceEntity.registeredBy = null

        when:
        def derivedEntity = new DerivedFoo(cdo)

        then:
        entity.registeredBy != derivedEntity.registeredBy
        entity.name != derivedEntity.name
    }
}
