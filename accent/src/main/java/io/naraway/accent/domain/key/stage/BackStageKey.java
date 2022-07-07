/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.stage;

import io.naraway.accent.domain.key.tenant.CineroomKey;

public class BackStageKey extends SpaceKey {
    //
    public static final String SPACE_DELIMITER = "_";

    public BackStageKey(String backStageId) {
        //
        super(backStageId, SpaceType.BackStage);
    }

    public static BackStageKey newKey(CineroomChartKey cineroomChartKey) {
        //
        long defaultSequence = 1;
        return new BackStageKey(String.format("%s%s%s", cineroomChartKey.getId(), SPACE_DELIMITER, defaultSequence));
    }

    public static BackStageKey newKey(CineroomChartKey cineroomChartKey, long backStageSequence) {
        //
        return new BackStageKey(String.format("%s%s%s", cineroomChartKey.getId(), SPACE_DELIMITER, backStageSequence));
    }

    public static BackStageKey fromId(String backStageId) {
        //
        return new BackStageKey(backStageId);
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

    public static BackStageKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static BackStageKey sample(long sampleSequence) {
        //
        return BackStageKey.newKey(CineroomChartKey.sample(), sampleSequence);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
        System.out.println(sample(2).toPrettyJson());
        System.out.println(sample().genCineroomKey().toPrettyJson());
    }
}
