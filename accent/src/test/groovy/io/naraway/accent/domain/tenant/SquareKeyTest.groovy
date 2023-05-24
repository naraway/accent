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

class SquareKeyTest extends Specification {
    //
    @Subject
    SquareKey key

    @Shared
    def stationKey = StationKey.fromId('1')

    //
    def setup() {
        key = SquareKey.newKey(stationKey, 10)
    }

    //
    def 'from valid id'() {
        when:
        def fromId = SquareKey.fromId('1:1')

        then:
        fromId.type == TenantType.Square
    }

    //
    def 'from invalid id'() {
        when:
        SquareKey.fromId('invalid id')

        then:
        thrown(IllegalArgumentException)
    }

    //
    def 'new key'() {
        when:
        def newKey = SquareKey.newKey(stationKey, 100)

        then:
        def id = newKey.id
        println id
        TenantKey.getTenantType(id) == TenantType.Square
        id.endsWith(TenantKey.HIERARCHY_DELIMITER + Numeral36.newInstance().getStr36(100))
    }

    //
    def 'check gen ids'() {
        expect:
        key.genStationId() == '1'
    }
}
