/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant

import io.naraway.accent.util.numeral36.Numeral36
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class PavilionKeyTest extends Specification {
    //
    @Subject
    PavilionKey key

    @Shared
    def squareKey = SquareKey.fromId('1:1')

    //
    def setup() {
        key = PavilionKey.newKey(squareKey, 10)
    }

    //
    def 'from valid id'() {
        when:
        def fromId = PavilionKey.fromId('1:1:1')

        then:
        fromId.type == TenantType.Pavilion
    }

    //
    def 'from invalid id'() {
        when:
        PavilionKey.fromId('invalid id')

        then:
        thrown(IllegalArgumentException)
    }

    //
    def 'new key'() {
        when:
        def newKey = PavilionKey.newKey(squareKey, 100)

        then:
        def id = newKey.id
        println id
        TenantKey.getTenantType(id) == TenantType.Pavilion
        id.endsWith(TenantKey.HIERARCHY_DELIMITER + Numeral36.newInstance().getStr36(100))
    }

    //
    def 'check gen ids'() {
        expect:
        key.genSquareId() == '1:1'
        key.genStationId() == '1'
    }
}
