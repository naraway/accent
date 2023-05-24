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

class StageKeyTest extends Specification {
    //
    @Subject
    StageKey key

    @Shared
    def cineroomChartKey = CineroomChartKey.fromId('1:1:1:1')

    //
    def setup() {
        key = StageKey.newKey(cineroomChartKey, 10)
    }

    //
    def 'from valid id'() {
        when:
        def fromId = StageKey.fromId('1:1:1:1-1')

        then:
        fromId.type == TenantType.Stage
    }

    //
    def 'from invalid id'() {
        when:
        StageKey.fromId('invalid id')

        then:
        thrown(IllegalArgumentException)
    }

    //
    def 'new key'() {
        when:
        def newKey = StageKey.newKey(cineroomChartKey, 100)

        then:
        def id = newKey.id
        println id
        TenantKey.getTenantType(id) == TenantType.Stage
        id.endsWith(TenantKey.SPACE_DELIMITER + Numeral36.newInstance().getStr36(100))
    }

    //
    def 'check gen ids'() {
        expect:
        key.genCineroomChartId() == '1:1:1:1'
        key.genCineroomId() == '1:1:1:1'
        key.genPavilionId() == '1:1:1'
        key.genSquareId() == '1:1'
        key.genStationId() == '1'
    }
}
