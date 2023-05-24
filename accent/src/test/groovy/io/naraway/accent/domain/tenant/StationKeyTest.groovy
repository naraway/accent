/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant

import io.naraway.accent.util.numeral36.Numeral36
import spock.lang.Specification
import spock.lang.Subject

class StationKeyTest extends Specification {
    //
    @Subject
    StationKey key

    //
    def setup() {
        key = StationKey.newKey(10)
    }

    //
    def 'from valid id'() {
        when:
        def fromId = StationKey.fromId('1')

        then:
        fromId.type == TenantType.Station
    }

    //
    def 'from invalid id'() {
        when:
        StationKey.fromId('invalid id')

        then:
        thrown(IllegalArgumentException)
    }

    //
    def 'sequence exceeded'() {
        when:
        StationKey.newKey(36)

        then:
        thrown(IllegalArgumentException)
    }

    //
    def 'new key'() {
        when:
        def newKey = StationKey.newKey(25)

        then:
        def id = newKey.id
        println id
        TenantKey.getTenantType(id) == TenantType.Station
        id == Numeral36.newInstance().getStr36(25)
    }
}
