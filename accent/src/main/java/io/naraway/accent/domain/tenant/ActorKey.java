/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant;

import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActorKey extends TenantKey {
    //
    public ActorKey(String actorId) {
        //
        super(actorId, TenantType.Actor);
    }

    public static ActorKey newKey(AudienceKey audienceKey, StageKey stageKey) {
        //
        return new ActorKey(buildMemberKey(audienceKey, stageKey.getId()));
    }

    public static ActorKey fromId(String actorId) {
        //
        return new ActorKey(actorId);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static ActorKey fromJson(String json) {
        //
        return JsonUtil.fromJson(json, ActorKey.class);
    }

    // gen tenant

    public AudienceKey genAudienceKey() {
        //
        return AudienceKey.fromId(String.format("%s%s%s",
                TenantKey.parseSequence36(this.getId()),
                MEMBER_DELIMITER,
                genStageKey().genCineroomId()));
    }

    public String genAudienceId() {
        //
        return genAudienceKey().getId();
    }

    public CitizenKey genCitizenKey() {
        //
        return genAudienceKey().genCitizenKey();
    }

    public String genCitizenId() {
        //
        return genCitizenKey().getId();
    }

    public DenizenKey genDenizenKey() {
        //
        return genAudienceKey().genDenizenKey();
    }

    public String genDenizenId() {
        //
        return genDenizenKey().getId();
    }

    // gen space

    public StageKey genStageKey() {
        //
        return StageKey.fromId(parseSpaceKey());
    }

    public String genStageId() {
        //
        return genStageKey().getId();
    }

    public CineroomChartKey genCineroomChartKey() {
        //
        return genStageKey().genCineroomChartKey();
    }

    public String genCineroomChartId() {
        //
        return genCineroomChartKey().getId();
    }

    public CineroomKey genCineroomKey() {
        //
        return genStageKey().genCineroomKey();
    }

    public String genCineroomId() {
        //
        return genCineroomKey().getId();
    }

    public PavilionKey genPavilionKey() {
        //
        return genStageKey().genPavilionKey();
    }

    public String genPavilionId() {
        //
        return genPavilionKey().getId();
    }

    public SquareKey genSquareKey() {
        //
        return genStageKey().genSquareKey();
    }

    public String genSquareId() {
        //
        return genSquareKey().getId();
    }

    public StationKey genStationKey() {
        //
        return genStageKey().genStationKey();
    }

    public String genStationId() {
        //
        return genStationKey().getId();
    }

    public static ActorKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static ActorKey sample(long sampleSequence) {
        //
        return ActorKey.newKey(AudienceKey.sample(sampleSequence), StageKey.sample());
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
        System.out.println(sample(2));
    }
}