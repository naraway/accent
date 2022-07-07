/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.granule;

import io.naraway.accent.domain.ddd.ValueObject;
import io.naraway.accent.domain.type.Tier;
import io.naraway.accent.util.json.JsonUtil;
import io.naraway.accent.util.validation.EmailValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"verified", "tier"})
public class Email implements ValueObject {
    //
    @SuppressWarnings("java:S1700")
    private String email;
    private Tier tier;
    private boolean verified;

    public Email(String email, Tier tier, boolean verified) {
        //
        if (!EmailValidator.isValid(email)) {
            throw new IllegalArgumentException("Invalid email: " + email);
        }

        this.email = email;
        this.tier = tier;
        this.verified = verified;
    }

    public Email(String email, Tier tier) {
        //
        this(email, tier, false);
    }

    public Email(String email) {
        //
        this(email, Tier.Primary, false);
    }

    public static Email newPrimary(String email) {
        //
        return new Email(email, Tier.Primary, false);
    }

    public static Email newSecondary(String email) {
        //
        return new Email(email, Tier.Secondary, false);
    }

    public static Email newVerifiedPrimary(String email) {
        //
        return new Email(email, Tier.Primary, true);
    }

    public static Email newVerifiedSecondary(String email) {
        //
        return new Email(email, Tier.Secondary, true);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static Email fromJson(String json) {
        return JsonUtil.fromJson(json, Email.class);
    }

    public static Email sample() {
        //
        String email = "guest@nara.app";
        return newPrimary(email);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
    }
}
