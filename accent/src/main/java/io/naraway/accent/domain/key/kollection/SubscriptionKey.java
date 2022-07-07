/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.kollection;

import io.naraway.accent.domain.key.tenant.PavilionKey;
import io.naraway.accent.util.json.JsonSerializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionKey implements JsonSerializable {
    //
    public static final String DELIMITER = "-";

    private String id;

    public SubscriptionKey(String subscriptionId) {
        //
        this.id = subscriptionId;
        if (!isValid()) {
            throw new IllegalArgumentException("Invalid key: " + this.id);
        }
    }

    public SubscriptionKey(String paviliionId, String kollectionVersionId) {
        //
        this(String.format("%s%s%s", paviliionId, DELIMITER, kollectionVersionId));
    }

    public static SubscriptionKey fromId(String subscriptionId) {
        //
        return new SubscriptionKey(subscriptionId);
    }

    public static SubscriptionKey newInstance(String pavilionId, String kollectionVersionId) {
        //
        return new SubscriptionKey(pavilionId, kollectionVersionId);
    }

    public PavilionKey genPavilionKey() {
        //
        return PavilionKey.fromId(this.id.substring(0, this.id.indexOf(DELIMITER)));
    }

    public String genPavilionId() {
        //
        return genPavilionKey().getId();
    }

    public KollectionVersionKey genKollectionVersionKey() {
        //
        return KollectionVersionKey.fromId(this.id.substring(this.id.indexOf(DELIMITER) + 1));
    }

    public String genKollectionVersionId() {
        //
        return genKollectionVersionKey().getId();
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    private boolean isValid() {
        //
        return !this.id.startsWith(DELIMITER)
                && !this.id.endsWith(DELIMITER)
                && this.id.contains(DELIMITER);
    }

    public static SubscriptionKey sample() {
        //
        return SubscriptionKey.newInstance(
                PavilionKey.sample().getId(),
                KollectionVersionKey.sample().getId());
    }

    public static void main(String[] args) {
        //
        SubscriptionKey sample = sample();
        System.out.println(sample.toPrettyJson());
        System.out.println("Pavilion Id: " + sample.genPavilionId());
        System.out.println("Kollection Version Id: " + sample.genKollectionVersionId());
    }
}
