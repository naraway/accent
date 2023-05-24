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
public class AudienceKey extends TenantKey {
    //
    public AudienceKey(String audienceId) {
        //
        super(audienceId, TenantType.Audience);
    }

    public static AudienceKey newKey(CitizenKey citizenKey, CineroomKey cineroomKey) {
        //
        return new AudienceKey(buildMemberKey(citizenKey, cineroomKey.getId()));
    }

    public static AudienceKey fromId(String audienceId) {
        //
        return new AudienceKey(audienceId);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static AudienceKey fromJson(String json) {
        //
        return JsonUtil.fromJson(json, AudienceKey.class);
    }

    // gen tenant

    public CitizenKey genCitizenKey() {
        //
        String citizenId = buildMemberKey(genSequence36(), genCineroomKey().genPavilionId());
        return new CitizenKey(citizenId);
    }

    public String genCitizenId() {
        //
        return genCitizenKey().getId();
    }

    public DenizenKey genDenizenKey() {
        //
        return genCitizenKey().genDenizenKey();
    }

    public String genDenizenId() {
        //
        return genDenizenKey().getId();
    }

    // gen space

    public CineroomChartKey genCineroomChartKey() {
        //
        return CineroomChartKey.newKey(genCineroomKey());
    }

    public String genCineroomChartId() {
        //
        return genCineroomChartKey().getId();
    }

    public CineroomKey genCineroomKey() {
        //
        return new CineroomKey(parseSpaceKey());
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

    public String genSequence36() {
        //
        return parseSequence36();
    }

    public static AudienceKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static AudienceKey sample(long sampleSequence) {
        //
        return newKey(CitizenKey.sample(sampleSequence), CineroomKey.sample());
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
        System.out.println(sample(2));
    }
}