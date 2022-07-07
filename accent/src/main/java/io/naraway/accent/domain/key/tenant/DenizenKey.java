/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.tenant;

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
        return parseToSequence36();
    }

    public SquareKey genSquareKey() {
        //
        return new SquareKey(parseToSpaceKey());
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
        System.out.println(sample().toPrettyJson());
        System.out.println(sample().genSquareKey());
        System.out.println(sample().genSequence36());
    }
}
