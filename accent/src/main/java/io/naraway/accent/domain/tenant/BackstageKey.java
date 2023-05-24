/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant;

public class BackstageKey extends TenantKey {
    //
    public BackstageKey(String backstageId) {
        //
        super(backstageId, TenantType.Backstage);
    }

    public static BackstageKey newKey(CineroomChartKey cineroomChartKey) {
        //
        long defaultSequence = 1;
        return newKey(cineroomChartKey, defaultSequence);
    }

    public static BackstageKey newKey(CineroomChartKey cineroomChartKey, long stageSequence) {
        //
        return new BackstageKey(buildBackstageKey(cineroomChartKey.genCineroomKey().getId(), stageSequence));
    }

    public static BackstageKey fromId(String backstageId) {
        //
        return new BackstageKey(backstageId);
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
        return CineroomKey.fromId(getId().substring(0, getId().indexOf(GROUP_DELIMITER)));
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

    public static BackstageKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static BackstageKey sample(long sampleSequence) {
        //
        return newKey(CineroomChartKey.sample(), sampleSequence);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toJson());
        System.out.println(sample(2).toJson());
    }
}