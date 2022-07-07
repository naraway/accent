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
public class CineroomKey extends TenantKey {
    //
    public CineroomKey(String cineroomId) {
        //
        super(cineroomId, TenantType.Cineroom);
    }

    public static CineroomKey newKey(PavilionKey pavilionKey, long cineroomSequence) {
        //
        return new CineroomKey(buildSpaceKey(pavilionKey.getId(), cineroomSequence));
    }

    public static CineroomKey fromId(String cineroomId) {
        //
        return new CineroomKey(cineroomId);
    }

    public AudienceKey newAudienceKey(String citizenId) {
        //
        return AudienceKey.newKey(citizenId, getId());
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static CineroomKey fromJson(String json) {
        //
        return JsonUtil.fromJson(json, CineroomKey.class);
    }

    public static CineroomKey fromKey(String key) {
        //
        return new CineroomKey(key);
    }

    public String genPavilionId() {
        //
        return genPavilionKey().getId();
    }

    public PavilionKey genPavilionKey() {
        //
        return new PavilionKey(parseToParentSpaceKey());
    }

    public static CineroomKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static CineroomKey sample(long sampleSequence) {
        //
        return newKey(PavilionKey.sample(), sampleSequence);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
        System.out.println(sample(2).toPrettyJson());
        System.out.println(sample().genPavilionKey().toPrettyJson());
    }
}
