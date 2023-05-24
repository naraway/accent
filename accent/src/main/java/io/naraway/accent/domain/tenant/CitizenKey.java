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
public class CitizenKey extends TenantKey {
    //
    public CitizenKey(String citizenKey) {
        //
        super(citizenKey, TenantType.Citizen);
    }

    public static CitizenKey newKey(PavilionKey pavilionKey) {
        //
        long defaultSequence = 1;
        return newKey(pavilionKey, defaultSequence);
    }

    public static CitizenKey newKey(PavilionKey pavilionKey, long citizenSequence) {
        //
        String citizenId = String.format("%s%s%s",
                Numeral36.newInstance().getStr36(citizenSequence),
                MEMBER_DELIMITER,
                pavilionKey.getId());

        return new CitizenKey(citizenId);
    }

    public static CitizenKey newKey(DenizenKey denizenKey, PavilionKey pavilionKey) {
        //
        return new CitizenKey(buildMemberKey(denizenKey, pavilionKey.getId()));
    }

    public static CitizenKey fromId(String citizenId) {
        //
        return new CitizenKey(citizenId);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static CitizenKey fromJson(String json) {
        //
        return JsonUtil.fromJson(json, CitizenKey.class);
    }

    //

    public DenizenKey genDenizenKey() {
        //
        String denizenId = buildMemberKey(genSequence36(), genPavilionKey().genSquareId());
        return DenizenKey.fromId(denizenId);
    }

    public String genDenizenId() {
        //
        return genDenizenKey().getId();
    }

    // gen space

    public PavilionKey genPavilionKey() {
        //
        return new PavilionKey(parseSpaceKey());
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

    public String genSequence36() {
        //
        return parseSequence36();
    }

    public static CitizenKey sample() {
        //
        long sampleSequence = 1;
        return sample(sampleSequence);
    }

    public static CitizenKey sample(long sampleSequence) {
        //
        return newKey(DenizenKey.sample(sampleSequence), PavilionKey.sample());
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toJson());
        System.out.println(sample(2).toJson());
    }
}