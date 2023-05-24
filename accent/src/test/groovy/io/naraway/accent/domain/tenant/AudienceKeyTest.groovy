/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant


import spock.lang.Specification
import spock.lang.Subject

class AudienceKeyTest extends Specification {
    //
    @Subject
    AudienceKey key

    //
    def setup() {
        key = AudienceKey.newKey(CitizenKey.fromId('a@1:1:1'), CineroomKey.fromId('1:1:1:1'))
    }

    //
    def 'from valid id'() {
        when:
        def fromId = AudienceKey.fromId('1@1:1:1:1')

        then:
        fromId.type == TenantType.Audience
    }

    //
    def 'from invalid id'() {
        when:
        AudienceKey.fromId('invalid id')

        then:
        thrown(IllegalArgumentException)
    }

    //
    def 'id type'() {
        when:
        def id = key.id

        then:
        println id
        TenantKey.getTenantType(id) == TenantType.Audience
    }

    //
    def 'check gen ids'() {
        expect:
        key.genCineroomChartId() == '1:1:1:1'
        key.genCineroomId() == '1:1:1:1'
        key.genPavilionId() == '1:1:1'
        key.genSquareId() == '1:1'
        key.genStationId() == '1'
        key.genCitizenId() == 'a@1:1:1'
        key.genDenizenId() == 'a@1:1'
    }
}
