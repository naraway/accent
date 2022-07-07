/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.tenant;

import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SquareKey extends TenantKey {
    //
    public SquareKey(String squareId) { // keyString = nara key or square key
        //
        super(squareId, TenantType.Square);
    }

    public static SquareKey newKey(StationKey stationKey) {
        //
        long defaultSequence = 1;
        return new SquareKey(buildSpaceKey(stationKey.getId(), defaultSequence));
    }

    public static SquareKey newKey(StationKey stationKey, long squareSequence) {
        //
        return new SquareKey(buildSpaceKey(stationKey.getId(), squareSequence));
    }

    public static SquareKey fromId(String squareId) {
        //
        return new SquareKey(squareId);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static SquareKey fromJson(String json) {
        //
        return JsonUtil.fromJson(json, SquareKey.class);
    }

    public StationKey genStationKey() {
        //
        return new StationKey(parseToParentSpaceKey());
    }

    public static SquareKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static SquareKey sample(long sampleSequence) {
        //
        return newKey(StationKey.sample(), sampleSequence);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
        System.out.println(sample(2).toPrettyJson());
    }
}
