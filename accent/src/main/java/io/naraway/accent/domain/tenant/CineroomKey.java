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
public class CineroomKey extends TenantKey {
    //
    public CineroomKey(String cineroomId) {
        //
        super(cineroomId, TenantType.Cineroom);
    }

    public static CineroomKey newKey(PavilionKey pavilionKey) {
        //
        long defaultSequence = 1;
        return newKey(pavilionKey, defaultSequence);
    }

    public static CineroomKey newKey(PavilionKey pavilionKey, long cineroomSequence) {
        //
        return new CineroomKey(buildSpaceKey(pavilionKey.getId(), cineroomSequence));
    }

    public static CineroomKey fromId(String cineroomId) {
        //
        return new CineroomKey(cineroomId);
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

    public PavilionKey genPavilionKey() {
        //
        return new PavilionKey(parseParentKey());
    }

    public String genPavilionId() {
        //
        return genPavilionKey().getId();
    }

    public SquareKey genSquareKey() {
        //
        return genPavilionKey().genSquareKey();
    }

    public String genSquareId() {
        //
        return genSquareKey().getId();
    }

    public StationKey genStationKey() {
        //
        return genPavilionKey().genStationKey();
    }

    public String genStationId() {
        //
        return genStationKey().getId();
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
        System.out.println(sample().toJson());
        System.out.println(sample(2).toJson());
    }
}