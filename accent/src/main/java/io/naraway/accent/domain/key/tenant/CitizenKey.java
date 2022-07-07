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
public class CitizenKey extends TenantKey {
    //
    public CitizenKey(String citizenKey) {
        //
        super(citizenKey, TenantType.Citizen);
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

    public DenizenKey genDenizenKey() {
        //
        String denizenId = String.format("%s%s%s",
                genSequence36(),
                MEMBER_DELIMITER,
                genPavilionKey().genSquareKey().getId());

        return DenizenKey.fromId(denizenId);
    }

    public String genPavilionId() {
        //
        return genPavilionKey().getId();
    }

    public PavilionKey genPavilionKey() {
        //
        return new PavilionKey(parseToSpaceKey());
    }

    public String genSequence36() {
        //
        return parseToSequence36();
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
        System.out.println(sample().toPrettyJson());
        System.out.println(sample().genPavilionKey());
        System.out.println(sample().genDenizenKey().toPrettyJson());
    }
}
