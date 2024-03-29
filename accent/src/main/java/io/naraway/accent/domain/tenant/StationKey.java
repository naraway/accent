/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant;

import io.naraway.accent.util.json.JsonUtil;
import io.naraway.accent.util.numeral36.Numeral36;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StationKey extends TenantKey {
    //
    public StationKey(String stationId) {
        //
        super(stationId, TenantType.Station);
    }

    public static StationKey newKey() {
        //
        long defaultSequence = 1;
        return newKey(defaultSequence);
    }

    public static StationKey newKey(long stationSequence) {
        //
        if (stationSequence > 35) {
            throw new IllegalArgumentException("'stationSequence' must less than 36");
        }

        String stationKey = String.format("%s", Numeral36.newInstance().getStr36(stationSequence));
        return new StationKey(stationKey);
    }

    public static StationKey fromId(String stationId) {
        //
        return new StationKey(stationId);
    }

    public static StationKey nextId(String stationId) {
        //
        return StationKey.newKey(Numeral36.newInstance().getLong36(stationId) + 1);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static StationKey fromJson(String json) {
        //
        return JsonUtil.fromJson(json, StationKey.class);
    }

    public static StationKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static StationKey sample(long sampleSequence) {
        //
        return StationKey.newKey(sampleSequence);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toJson());
        System.out.println(sample(2).toJson());
    }
}