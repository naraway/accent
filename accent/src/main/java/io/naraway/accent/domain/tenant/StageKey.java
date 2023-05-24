/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant;

import io.naraway.accent.util.json.JsonUtil;

public class StageKey extends TenantKey {
    //
    public StageKey(String stageId) {
        //
        super(stageId, TenantType.Stage);
    }

    public static StageKey newKey(CineroomChartKey cineroomChartKey) {
        //
        long defaultSequence = 1;
        return newKey(cineroomChartKey, defaultSequence);
    }

    public static StageKey newKey(CineroomChartKey cineroomChartKey, long stageSequence) {
        //
        return new StageKey(buildStageSpaceKey(cineroomChartKey.genCineroomKey().getId(), stageSequence));
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

    public CineroomChartKey genCineroomChartKey() {
        //
        return CineroomChartKey.fromId(genCineroomId());
    }

    public String genCineroomChartId() {
        //
        return genCineroomChartKey().getId();
    }

    public CineroomKey genCineroomKey() {
        //
        return CineroomKey.fromId(getId().substring(0, getId().indexOf(SPACE_DELIMITER)));
    }

    public String genCineroomId() {
        //
        return genCineroomKey().getId();
    }

    public PavilionKey genPavilionKey() {
        //
        return genCineroomKey().genPavilionKey();
    }

    public String genPavilionId() {
        //
        return genPavilionKey().getId();
    }

    public SquareKey genSquareKey() {
        //
        return genCineroomKey().genSquareKey();
    }

    public String genSquareId() {
        //
        return genSquareKey().getId();
    }

    public StationKey genStationKey() {
        //
        return genCineroomKey().genStationKey();
    }

    public String genStationId() {
        //
        return genStationKey().getId();
    }

    public static StageKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static StageKey sample(long sampleSequence) {
        //
        return newKey(CineroomChartKey.sample(), sampleSequence);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toJson());
        System.out.println(sample(2).toJson());
    }
}