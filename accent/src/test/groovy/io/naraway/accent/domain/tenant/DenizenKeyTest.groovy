/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant


import spock.lang.Specification
import spock.lang.Subject

class DenizenKeyTest extends Specification {
    //
    @Subject
    DenizenKey key

    //
    def setup() {
        key = DenizenKey.newKey(SquareKey.fromId('1:1'), 100)
    }

    //
    def 'from valid id'() {
        when:
        def fromId = DenizenKey.fromId('1@1:1')

        then:
        fromId.type == TenantType.Denizen
    }

    //
    def 'from invalid id'() {
        when:
        DenizenKey.fromId('invalid id')

        then:
        thrown(IllegalArgumentException)
    }

    //
    def 'id type'() {
        when:
        def id = key.id

        then:
        println id
        TenantKey.getTenantType(id) == TenantType.Denizen
    }

    //
    def 'check gen ids'() {
        expect:
        key.genSquareId() == '1:1'
        key.genStationId() == '1'
    }
}
