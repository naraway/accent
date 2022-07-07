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
public class AudienceKey extends TenantKey {
    //
    public AudienceKey(String audienceId) {
        //
        super(audienceId, TenantType.Audience);
    }

    public static AudienceKey newKey(DenizenKey denizenKey, CineroomKey cineroomKey) {
        //
        return new AudienceKey(buildMemberKey(denizenKey, cineroomKey.getId()));
    }

    public static AudienceKey newKey(String citizenId, String cineroomId) {
        //
        String audienceId = String.format("%s@%s", TenantKey.parseToSequence36(citizenId), cineroomId);
        return new AudienceKey(audienceId);
    }

    public static AudienceKey newKey(CitizenKey citizenKey, CineroomKey cineroomKey) {
        //
        String audienceId = String.format("%s@%s", citizenKey.parseToSequence36(), cineroomKey.getId());
        return new AudienceKey(audienceId);
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

    public String genCitizenId() {
        //
        return genCitizenKey().getId();
    }

    public CitizenKey genCitizenKey() {
        //
        String citizenKey = buildMemberKey(genSequence36(), genCineroomKey().genPavilionId());
        return new CitizenKey(citizenKey);
    }

    public String genCineroomId() {
        //
        return genCineroomKey().getId();
    }

    public CineroomKey genCineroomKey() {
        //
        return new CineroomKey(super.parseToSpaceKey());
    }

    public String genSequence36() {
        //
        return parseToSequence36();
    }

    public static AudienceKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static AudienceKey sample(long sampleSequence) {
        //
        return newKey(DenizenKey.sample(sampleSequence), CineroomKey.sample());
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
        System.out.println(sample().genCineroomKey().getId());
        System.out.println(sample().genSequence36());
        System.out.println(newKey(CitizenKey.sample(), CineroomKey.sample()));
    }
}
