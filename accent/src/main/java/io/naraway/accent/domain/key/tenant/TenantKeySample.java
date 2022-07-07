/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.tenant;

import io.naraway.accent.util.json.JsonUtil;

import java.util.ArrayList;
import java.util.List;

public class TenantKeySample {
    //
    private static final int GEN_LIMIT = 10;

    public static List<StationKey> genStationKeys() {
        //
        List<StationKey> stationKeys = new ArrayList<>();
        for (long sequareSequence = 1; sequareSequence <= GEN_LIMIT; sequareSequence++) {
            //
            stationKeys.add(StationKey.newKey(sequareSequence));
        }

        return stationKeys;
    }

    public static List<SquareKey> genSquareKeys() {
        //
        List<SquareKey> squareKeys = new ArrayList<>();
        for (long sequareSequence = 1; sequareSequence <= GEN_LIMIT; sequareSequence++) {
            //
            squareKeys.add(SquareKey.newKey(StationKey.sample(), sequareSequence));
        }

        return squareKeys;
    }

    public static List<DenizenKey> genDenizenKeys() {
        //
        List<DenizenKey> denizenKeys = new ArrayList<>();
        SquareKey squareKey = SquareKey.sample();
        for (long DenizenSequence = 1; DenizenSequence <= GEN_LIMIT; DenizenSequence++) {
            denizenKeys.add(DenizenKey.newKey(squareKey, DenizenSequence));
        }

        return denizenKeys;
    }

    public static List<PavilionKey> genPavilionKeys() {
        //
        List<PavilionKey> pavilionKeys = new ArrayList<>();
        SquareKey squareKey = SquareKey.sample();
        for (long pavilionSequence = 1; pavilionSequence <= GEN_LIMIT; pavilionSequence++) {
            pavilionKeys.add(PavilionKey.newKey(squareKey, pavilionSequence));
        }

        return pavilionKeys;
    }

    public static List<CitizenKey> genCitizenKeys() {
        //
        List<CitizenKey> citizenKeys = new ArrayList<>();
        PavilionKey pavilionKey = PavilionKey.sample();
        for (io.naraway.accent.domain.key.tenant.DenizenKey DenizenKey : genDenizenKeys()) {
            citizenKeys.add(CitizenKey.newKey(DenizenKey, pavilionKey));
        }

        return citizenKeys;
    }

    public static List<CineroomKey> genCineroomKeys() {
        //
        List<CineroomKey> cineroomKeys = new ArrayList<>();
        PavilionKey pavilionKey = PavilionKey.sample();
        for (long cineroomKeySequence = 1; cineroomKeySequence <= GEN_LIMIT; cineroomKeySequence++) {
            cineroomKeys.add(CineroomKey.newKey(pavilionKey, cineroomKeySequence));
        }

        return cineroomKeys;
    }

    public static List<AudienceKey> genAudienceKeys() {
        //
        List<AudienceKey> audienceKeys = new ArrayList<>();
        CineroomKey cineroomKey = CineroomKey.sample();
        for (io.naraway.accent.domain.key.tenant.DenizenKey DenizenKey : genDenizenKeys()) {
            audienceKeys.add(AudienceKey.newKey(DenizenKey, cineroomKey));
        }

        return audienceKeys;
    }

    public static void main(String[] args) {
        //
        System.out.println(JsonUtil.toPrettyJson(genStationKeys()));
        System.out.println(JsonUtil.toPrettyJson(genSquareKeys()));
        System.out.println(JsonUtil.toPrettyJson(genDenizenKeys()));
        System.out.println(JsonUtil.toPrettyJson(genPavilionKeys()));
        System.out.println(JsonUtil.toPrettyJson(genCitizenKeys()));
        System.out.println(JsonUtil.toPrettyJson(genCineroomKeys()));
        System.out.println(JsonUtil.toPrettyJson(genAudienceKeys()));
    }
}
