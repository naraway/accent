/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant


import spock.lang.Specification
import spock.lang.Subject

class CitizenKeyTest extends Specification {
    //
    @Subject
    CitizenKey key

    //
    def setup() {
        key = CitizenKey.newKey(DenizenKey.fromId('a@1:1'), PavilionKey.fromId('1:1:1'))
    }

    //
    def 'from valid id'() {
        when:
        def fromId = CitizenKey.fromId('1@1:1:1')

        then:
        fromId.type == TenantType.Citizen
    }

    //
    def 'from invalid id'() {
        when:
        CitizenKey.fromId('invalid id')

        then:
        thrown(IllegalArgumentException)
    }

    //
    def 'id type'() {
        when:
        def id = key.id

        then:
        println id
        TenantKey.getTenantType(id) == TenantType.Citizen
    }

    //
    def 'check gen ids'() {
        expect:
        key.genPavilionId() == '1:1:1'
        key.genSquareId() == '1:1'
        key.genStationId() == '1'
        key.genDenizenId() == 'a@1:1'
    }
}
