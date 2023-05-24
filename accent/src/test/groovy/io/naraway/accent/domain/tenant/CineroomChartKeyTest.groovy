/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant


import spock.lang.Specification
import spock.lang.Subject

class CineroomChartKeyTest extends Specification {
    //
    @Subject
    CineroomChartKey key

    def cineroomKey = CineroomKey.fromId('1:1:1:1')

    //
    def setup() {
        key = CineroomChartKey.newKey(cineroomKey)
    }

    //
    def 'from valid id'() {
        when:
        def fromId = CineroomChartKey.fromId('1:1:1:1')

        then:
        fromId.type == TenantType.CineroomChart
    }

    //
    def 'from invalid id'() {
        when:
        CineroomChartKey.fromId('invalid id')

        then:
        thrown(IllegalArgumentException)
    }

    //
    def 'new key'() {
        when:
        def newKey = CineroomChartKey.newKey(cineroomKey)

        then:
        def id = newKey.id
        println id
        TenantKey.getTenantType(id) == TenantType.Cineroom
        id == cineroomKey.id
    }

    //
    def 'check gen ids'() {
        expect:
        key.genCineroomId() == '1:1:1:1'
        key.genPavilionId() == '1:1:1'
        key.genSquareId() == '1:1'
        key.genStationId() == '1'
    }
}
