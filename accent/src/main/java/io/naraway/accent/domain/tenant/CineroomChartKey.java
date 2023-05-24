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
public class CineroomChartKey extends TenantKey {
    //
    public CineroomChartKey(String cineroomChartId) {
        //
        super(cineroomChartId, TenantType.CineroomChart);
    }

    public static CineroomChartKey newKey(CineroomKey cineroomKey) {
        //
        return new CineroomChartKey(cineroomKey.getId());
    }

    public static CineroomChartKey fromId(String cineroomChartId) {
        //
        return new CineroomChartKey(cineroomChartId);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static CineroomChartKey fromJson(String json) {
        //
        return JsonUtil.fromJson(json, CineroomChartKey.class);
    }

    public CineroomKey genCineroomKey() {
        //
        return CineroomKey.fromId(this.getId());
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

    public static CineroomChartKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static CineroomChartKey sample(long sampleSequence) {
        //
        return newKey(CineroomKey.sample(sampleSequence));
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toJson());
        System.out.println(sample(2).toJson());
    }
}