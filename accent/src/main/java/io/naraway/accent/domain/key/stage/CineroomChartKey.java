/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.stage;

import io.naraway.accent.domain.key.tenant.CineroomKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CineroomChartKey extends SpaceKey {
    //
    public CineroomChartKey(String cineroomChartId) {
        //
        super(cineroomChartId, SpaceType.CineroomChart);
    }

    public CineroomChartKey(CineroomKey cineroomKey) {
        //
        this(cineroomKey.getId());
    }

    public static CineroomChartKey newInstance(String cineroomChartId) {
        //
        return new CineroomChartKey(cineroomChartId);
    }

    public static CineroomChartKey newInstanceWithCineroomKey(CineroomKey cineroomKey) {
        //
        return new CineroomChartKey(cineroomKey);
    }

    public static CineroomChartKey fromId(String cineroomChartId) {
        //
        return new CineroomChartKey(cineroomChartId);
    }

    public String genCineroomId() {
        //
        return genCineroomKey().getId();
    }

    public CineroomKey genCineroomKey() {
        //
        return CineroomKey.fromId(this.getId());
    }

    public static CineroomChartKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static CineroomChartKey sample(long sampleSequence) {
        //
        return new CineroomChartKey(CineroomKey.sample(sampleSequence));
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
        System.out.println(sample(2).toPrettyJson());
    }
}
