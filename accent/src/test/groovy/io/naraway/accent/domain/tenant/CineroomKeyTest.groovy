/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant

import io.naraway.accent.util.numeral36.Numeral36
import spock.lang.Specification
import spock.lang.Subject

class CineroomKeyTest extends Specification {
    //
    @Subject
    CineroomKey key

    def pavilionKey = PavilionKey.fromId('1:1:1')

    //
    def setup() {
        key = CineroomKey.newKey(pavilionKey, 10)
    }

    //
    def 'from valid id'() {
        when:
        def fromId = CineroomKey.fromId('1:1:1:1')

        then:
        fromId.type == TenantType.Cineroom
    }

    //
    def 'from invalid id'() {
        when:
        CineroomKey.fromId('invalid id')

        then:
        thrown(IllegalArgumentException)
    }

    //
    def 'new key'() {
        when:
        def newKey = CineroomKey.newKey(pavilionKey, 100)

        then:
        def id = newKey.id
        println id
        TenantKey.getTenantType(id) == TenantType.Cineroom
        id.endsWith(TenantKey.HIERARCHY_DELIMITER + Numeral36.newInstance().getStr36(100))
    }

    //
    def 'check gen ids'() {
        expect:
        key.genPavilionId() == '1:1:1'
        key.genSquareId() == '1:1'
        key.genStationId() == '1'
    }
}
