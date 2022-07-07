/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.office;

import io.naraway.accent.domain.key.tenant.StationKey;
import io.naraway.accent.util.json.JsonUtil;
import io.naraway.accent.util.numeral36.Numeral36;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StationServantKey extends ServantKey {
    //
    public StationServantKey(String stationServantId) {
        //
        super(stationServantId, ServantType.StationServant);
    }

    public StationServantKey(StationKey stationKey, long stationServantSequence) {
        //
        this(String.format("%s%s%s",
                Numeral36.newInstance().getStr36(stationServantSequence),
                SERVANT_DELIMITER,
                stationKey.getId()));
    }

    public static StationServantKey newKey(StationKey stationKey) {
        //
        long defaultSequence = 1;
        return new StationServantKey(stationKey, defaultSequence);
    }

    public static StationServantKey newKey(StationKey stationKey, long stationServantSequence) {
        //
        return new StationServantKey(stationKey, stationServantSequence);
    }

    public static StationServantKey fromId(String stationServantId) {
        //
        return new StationServantKey(stationServantId);
    }

    public StationKey genStationKey() {
        //
        return new StationKey(parseToOfficeKey());
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static StationServantKey fromJson(String json) {
        //
        return JsonUtil.fromJson(json, StationServantKey.class);
    }

    public static StationServantKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static StationServantKey sample(long sampleSequence) {
        //
        return StationServantKey.newKey(StationKey.sample(), sampleSequence);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
        System.out.println(sample(2).toPrettyJson());
    }
}
