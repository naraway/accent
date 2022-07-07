/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.stage;

import io.naraway.accent.domain.key.tenant.CineroomKey;
import io.naraway.accent.util.json.JsonUtil;
import io.naraway.accent.util.numeral36.Numeral36;

public class StageKey extends SpaceKey {
    //
    // stage key == sequence of cineroom
    //
    // n:n:n-[Numeral36Numeral36]
    public static final String SPACE_DELIMITER = "-";
    private static final long STAGE_MAX_SEQUENCE = 36 ^ 2;

    public StageKey(String stageId) {
        //
        super(stageId, SpaceType.Stage);
    }

    public StageKey(String cineroomChartId, long stageSequence) {
        //
        // Max stage sequence = 36^2
        super();
        if (stageSequence > STAGE_MAX_SEQUENCE) {
            throw new IllegalArgumentException("stageSequence should be less than: " + STAGE_MAX_SEQUENCE);
        }

        super.setId(String.format("%s%s%s",
                cineroomChartId,
                SPACE_DELIMITER,
                Numeral36.newInstance().getStr36(stageSequence)));
        super.setType(SpaceType.Stage);
    }

    public StageKey(CineroomChartKey cineroomChartKey, long stageSequence) {
        //
        this(cineroomChartKey.getId(), stageSequence);
    }

    public static StageKey newInstance(String cineroomChartId, long stageSequence) {
        //
        return new StageKey(cineroomChartId, stageSequence);
    }

    public static StageKey newInstance(CineroomChartKey cineroomChartKey, long stageSequence) {
        //
        return new StageKey(cineroomChartKey, stageSequence);
    }

    public static StageKey fromId(String stageId) {
        //
        return new StageKey(stageId);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static StageKey fromJson(String json) {
        //
        return JsonUtil.fromJson(json, StageKey.class);
    }

    public String genCineroomChartId() {
        //
        return genCineroomChartKey().getId();
    }

    public CineroomChartKey genCineroomChartKey() {
        //
        return CineroomChartKey.fromId(genCineroomId());
    }

    public String genCineroomId() {
        //
        return genCineroomKey().getId();
    }

    public CineroomKey genCineroomKey() {
        //
        return CineroomKey.fromId(getId().substring(0, getId().indexOf(SPACE_DELIMITER)));
    }

    public static StageKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static StageKey sample(long sampleSequence) {
        //
        return new StageKey(CineroomChartKey.sample(), sampleSequence);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
        System.out.println(sample(12).toPrettyJson());
        System.out.println(sample().genCineroomChartKey());
        System.out.println(sample().genCineroomKey());
    }
}
