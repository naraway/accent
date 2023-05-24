/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.entity

import io.naraway.accent.util.crypto.ValueEncryptor
import io.naraway.accent.util.entity.entity.FooV4_0
import io.naraway.accent.util.entity.entity.FooV4_1
import io.naraway.accent.util.json.JsonUtil
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import java.time.LocalDateTime

class EntitiesTest extends Specification {
    //
    @Subject
    Entities entities

    @Shared
    FooV4_0 entity_4_0

    @Shared
    FooV4_1 entity_4_1

    //
    def setup() {
        entity_4_0 = new FooV4_0(
                'name',
                'password',
                '99999999',
                new FooV4_0.PhoneCodeName('home', '+821010001000', LocalDateTime.now()),
                true,
                LocalDateTime.now())
        entity_4_1 = new FooV4_1(
                'name',
                'password',
                '99999999',
                new FooV4_1.PhoneCodeName('home', '+821010001000', LocalDateTime.now()),
                true,
                LocalDateTime.now(),
                "reference-id")
    }

    //
    def 'get updatable attributes v4.0.x'() {
        when:
        def attributes = Entities.getUpdatableAttributes(entity_4_0)

        then:
        println(attributes)
        attributes.containsAll(['password', 'name', 'phoneCodeName', 'dateTime'])
    }

    //
    def 'get updatable attributes v4.1.x'() {
        when:
        def attributes = Entities.getUpdatableAttributes(entity_4_1)

        then:
        println(attributes)
        attributes.containsAll(['password', 'name', 'phoneCodeName', 'dateTime'])
    }

    //
    def 'get encrypt attributes'() {
        when:
        def attributes = Entities.getEncryptAttributes(entity_4_0)

        then:
        println(attributes)
        attributes.size() == 2
        attributes.containsAll(['password', 'socialNumber'])
    }

    //
    def 'get changed nameValues between two entities v4.0.x'() {
        given:
        def editedEntity = JsonUtil.fromJson(entity_4_0.toJson(), FooV4_0.class)
        editedEntity.getPhoneCodeName().setCode("work")
        editedEntity.setName("other name")
        editedEntity.setPassword("new password")
        editedEntity.getPhoneCodeName().setDateTime(LocalDateTime.now())
        editedEntity.setDateTime(LocalDateTime.now())
        editedEntity.setEnabled(!entity_4_0.isEnabled())

        when:
        def nameValues = Entities.getModifiedNameValues(entity_4_0, editedEntity)

        then:
        println(nameValues)
        nameValues.size() == 4
    }

    //
    def 'get changed nameValues between two entities v4.1.x'() {
        given:
        def editedEntity = JsonUtil.fromJson(entity_4_1.toJson(), FooV4_0.class)
        editedEntity.getPhoneCodeName().setCode("work")
        editedEntity.setName("other name")
        editedEntity.setPassword("new password")
        editedEntity.getPhoneCodeName().setDateTime(LocalDateTime.now())
        editedEntity.setDateTime(LocalDateTime.now())
        editedEntity.setEnabled(!entity_4_1.isEnabled())

        when:
        def nameValues = Entities.getModifiedNameValues(entity_4_1, editedEntity)

        then:
        println(nameValues)
        nameValues.size() == 4
    }

    //
    def 'copy attributes with ignore'() {
        given:
        def editedEntity = JsonUtil.fromJson(entity_4_0.toJson(), FooV4_0.class)
        editedEntity.getPhoneCodeName().setCode("work")
        editedEntity.setName("other name")
        editedEntity.getPhoneCodeName().setDateTime(LocalDateTime.now())
        editedEntity.setDateTime(LocalDateTime.now())
        editedEntity.setEnabled(!entity_4_0.isEnabled())

        when:
        Beans.copyProperties(editedEntity, entity_4_0)

        then:
        entity_4_0.name == 'other name'
        entity_4_0.phoneCodeName.code == 'work'

        when:
        editedEntity.setName("not change")
        Beans.copyProperties(editedEntity, entity_4_0, 'name')

        then:
        entity_4_0.name != 'not change'
    }

    //
    def 'encrypt/decrypt entity with @Encrypt annotation'() {
        given:
        System.getProperties().setProperty(ValueEncryptor.SECRET_PROPERTY, "nara-secret")

        when:
        Entities.encrypt(entity_4_0)
        then:
        println(entity_4_0)

        when:
        Entities.decrypt(entity_4_0)
        then:
        println(entity_4_0)
    }
}
