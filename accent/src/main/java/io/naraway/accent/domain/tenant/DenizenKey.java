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
public class DenizenKey extends TenantKey {
    //
    public DenizenKey(String denizenId) {
        //
        super(denizenId, TenantType.Denizen);
    }

    public static DenizenKey newKey(SquareKey squareKey) {
        //
        long defaultSequence = 1;
        return newKey(squareKey, defaultSequence);
    }

    public static DenizenKey newKey(SquareKey squareKey, long denizenSequence) {
        //
        String denizenId = String.format("%s%s%s",
                Numeral36.newInstance().getStr36(denizenSequence),
                MEMBER_DELIMITER,
                squareKey.getId());

        return new DenizenKey(denizenId);
    }

    public static DenizenKey fromId(String denizenId) {
        //
        return new DenizenKey(denizenId);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static DenizenKey fromJson(String json) {
        //
        return JsonUtil.fromJson(json, DenizenKey.class);
    }

    public String genSequence36() {
        //
        return parseSequence36();
    }

    public SquareKey genSquareKey() {
        //
        return new SquareKey(parseSpaceKey());
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

    public static DenizenKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static DenizenKey sample(long sampleSequence) {
        //
        return newKey(SquareKey.sample(), sampleSequence);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toJson());
        System.out.println(sample(2).toJson());
    }
}