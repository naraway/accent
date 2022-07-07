/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.stage;

import io.naraway.accent.domain.key.tenant.*;
import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ActorKey extends SpaceKey {
    //
    // [AudienceSequence]@n:n:n-[Numeral36Numeral36]
    public ActorKey(String actorId) {
        //
        super(actorId, SpaceType.Actor);
    }

    public ActorKey(String audienceId, String stageId) {
        //
        this(String.format("%s%s%s",
                TenantKey.parseToSequence36(audienceId),
                MEMBER_DELIMITER,
                stageId));
    }

    public ActorKey(AudienceKey audienceKey, StageKey stageKey) {
        //
        this(audienceKey.getId(), stageKey.getId());
    }

    public static ActorKey newKey(String audienceId, String stageId) {
        //
        return new ActorKey(audienceId, stageId);
    }

    public static ActorKey newKey(AudienceKey audienceKey, StageKey stageKey) {
        //
        return new ActorKey(audienceKey, stageKey);
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

    public String genStageId() {
        //
        return genStageKey().getId();
    }

    public StageKey genStageKey() {
        //
        return StageKey.fromId(getId().substring(getId().indexOf('@') + 1));
    }

    public String genCineroomChartId() {
        //
        return genCineroomChartKey().getId();
    }

    public CineroomChartKey genCineroomChartKey() {
        //
        return genStageKey().genCineroomChartKey();
    }

    public String genCineroomId() {
        //
        return genCineroomKey().getId();
    }

    public CineroomKey genCineroomKey() {
        //
        return genStageKey().genCineroomKey();
    }

    public String genAudienceId() {
        //
        return genAudienceKey().getId();
    }

    public AudienceKey genAudienceKey() {
        //
        return AudienceKey.fromId(String.format("%s%s%s",
                TenantKey.parseToSequence36(this.getId()),
                MEMBER_DELIMITER,
                genCineroomKey().getId()));
    }

    public String genCitizenId() {
        //
        return genCitizenKey().getId();
    }

    public CitizenKey genCitizenKey() {
        //
        return genAudienceKey().genCitizenKey();
    }

    public String genPavilionId() {
        //
        return genPavilionKey().getId();
    }

    public PavilionKey genPavilionKey() {
        //
        return genCitizenKey().genPavilionKey();
    }

    public static ActorKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static ActorKey sample(long sampleSequence) {
        //
        return new ActorKey(AudienceKey.sample(sampleSequence), StageKey.sample());
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
        System.out.println(sample(1));
        System.out.println(sample().genCineroomChartKey());
        System.out.println(sample().genCineroomKey());
        System.out.println(sample().genAudienceKey());
    }
}
