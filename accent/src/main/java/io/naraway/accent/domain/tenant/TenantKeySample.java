/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant;

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
            stationKeys.add(StationKey.newKey(sequareSequence));
        }

        return stationKeys;
    }

    public static List<SquareKey> genSquareKeys() {
        //
        List<SquareKey> squareKeys = new ArrayList<>();
        for (long sequareSequence = 1; sequareSequence <= GEN_LIMIT; sequareSequence++) {
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
        for (io.naraway.accent.domain.tenant.DenizenKey DenizenKey : genDenizenKeys()) {
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
        for (CitizenKey citizenKey : genCitizenKeys()) {
            audienceKeys.add(AudienceKey.newKey(citizenKey, cineroomKey));
        }

        return audienceKeys;
    }

    public static List<CineroomChartKey> genCineroomChartKeys() {
        //
        List<CineroomChartKey> cineroomChartKeys = new ArrayList<>();
        CineroomKey cineroomKey = CineroomKey.sample();
        for (long cineroomChartKeySequence = 1; cineroomChartKeySequence <= GEN_LIMIT; cineroomChartKeySequence++) {
            cineroomChartKeys.add(CineroomChartKey.newKey(cineroomKey));
        }

        return cineroomChartKeys;
    }

    public static List<BackstageKey> genBackstageKeys() {
        //
        List<BackstageKey> backstageKeys = new ArrayList<>();
        CineroomChartKey cineroomChartKey = CineroomChartKey.sample();
        for (long backstageKeySequence = 1; backstageKeySequence <= GEN_LIMIT; backstageKeySequence++) {
            backstageKeys.add(BackstageKey.newKey(cineroomChartKey, backstageKeySequence));
        }

        return backstageKeys;
    }

    public static List<StageKey> genStageKeys() {
        //
        List<StageKey> stageKeys = new ArrayList<>();
        CineroomChartKey cineroomChartKey = CineroomChartKey.sample();
        for (long stageSequence = 1; stageSequence <= GEN_LIMIT; stageSequence++) {
            stageKeys.add(StageKey.newKey(cineroomChartKey, stageSequence));
        }

        return stageKeys;
    }

    public static List<ActorKey> genActorKeys() {
        //
        List<ActorKey> actorKeys = new ArrayList<>();
        StageKey stageKey = StageKey.sample();
        for (AudienceKey audienceKey : genAudienceKeys()) {
            actorKeys.add(ActorKey.newKey(audienceKey, stageKey));
        }

        return actorKeys;
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
        System.out.println(JsonUtil.toPrettyJson(genCineroomChartKeys()));
        System.out.println(JsonUtil.toPrettyJson(genBackstageKeys()));
        System.out.println(JsonUtil.toPrettyJson(genStageKeys()));
        System.out.println(JsonUtil.toPrettyJson(genActorKeys()));
    }
}