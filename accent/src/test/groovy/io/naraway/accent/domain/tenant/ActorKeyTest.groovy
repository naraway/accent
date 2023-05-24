/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant


import spock.lang.Specification
import spock.lang.Subject

class ActorKeyTest extends Specification {
    //
    @Subject
    ActorKey key

    //
    def setup() {
        key = ActorKey.newKey(AudienceKey.fromId('a@1:1:1:1'), StageKey.fromId('1:1:1:1-1'))
    }

    //
    def 'from valid id'() {
        when:
        def fromId = ActorKey.fromId('1@1:1:1:1-1')

        then:
        fromId.type == TenantType.Actor
    }

    //
    def 'from invalid id'() {
        when:
        ActorKey.fromId('invalid id')

        then:
        thrown(IllegalArgumentException)
    }

    //
    def 'id type'() {
        when:
        def id = key.id

        then:
        println id
        TenantKey.getTenantType(id) == TenantType.Actor
    }

    //
    def 'check gen ids'() {
        expect:
        key.genStageId() == '1:1:1:1-1'
        key.genCineroomChartId() == '1:1:1:1'
        key.genCineroomId() == '1:1:1:1'
        key.genPavilionId() == '1:1:1'
        key.genSquareId() == '1:1'
        key.genStationId() == '1'
        key.genAudienceId() == 'a@1:1:1:1'
        key.genCitizenId() == 'a@1:1:1'
        key.genDenizenId() == 'a@1:1'
    }
}
