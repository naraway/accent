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
public class PavilionKey extends TenantKey {
    //
    public PavilionKey(String pavilionId) {
        //
        super(pavilionId, TenantType.Pavilion);
    }

    public static PavilionKey newKey(SquareKey squareKey) {
        //
        long defaultSequence = 1;
        return newKey(squareKey, defaultSequence);
    }

    public static PavilionKey newKey(SquareKey squareKey, long pavilionSequence) {
        //
        return new PavilionKey(buildSpaceKey(squareKey.getId(), pavilionSequence));
    }

    public static PavilionKey fromId(String pavilionId) {
        //
        return new PavilionKey(pavilionId);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static PavilionKey fromJson(String json) {
        //
        return JsonUtil.fromJson(json, PavilionKey.class);
    }

    public SquareKey genSquareKey() {
        //
        return new SquareKey(parseParentKey());
    }

    public String genSquareId() {
        //
        return genSquareKey().getId();
    }

    public StationKey genStationKey() {
        //
        return genSquareKey().genStationKey();
    }

    public String genStationId() {
        //
        return genStationKey().getId();
    }

    public static PavilionKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static PavilionKey sample(long sampleSequence) {
        //
        return newKey(SquareKey.sample(), sampleSequence);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toJson());
        System.out.println(sample(2).toJson());
    }
}