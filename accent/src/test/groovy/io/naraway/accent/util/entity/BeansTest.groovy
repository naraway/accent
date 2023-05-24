/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.entity

import io.naraway.accent.util.entity.entity.FooV4_0
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class BeansTest extends Specification {
    //
    @Subject
    Beans beans

    @Shared
    FooV4_0 entity

    //
    def setup() {
        entity = new FooV4_0(
                'name',
                'password',
                '99999999',
                new FooV4_0.PhoneCodeName('home', '+821010001000', LocalDateTime.now()),
                true,
                LocalDateTime.now())
    }

    //
    def 'has getter'() {
        expect:
        Beans.hasGetter(entity, 'name') == true
        Beans.hasGetter(entity, 'id') == true
        Beans.hasGetter(entity, 'enabled') == true
        Beans.hasGetter(entity, 'others') == false
    }

    //
    def 'has setter'() {
        expect:
        Beans.hasSetter(entity, 'name') == true
        Beans.hasSetter(entity, 'id') == true
        Beans.hasSetter(entity, 'enabled') == true
        Beans.hasSetter(entity, 'others') == false
    }

    //
    def 'read value'() {
        given:
        def value = 'new one'
        entity.name = value

        when:
        def readed = Beans.readValue(entity, 'name')

        then:
        value == readed

        when:
        Beans.readValue(entity, 'otherName')

        then:
        thrown(IllegalArgumentException)
    }

    //
    def 'write value'() {
        given:
        def value = 'new one'

        when:
        Beans.writeValue(entity, 'name', value)

        then:
        entity.name == value

        when:
        Beans.writeValue(entity, 'otherName', value)

        then:
        thrown(IllegalArgumentException)
    }
}
