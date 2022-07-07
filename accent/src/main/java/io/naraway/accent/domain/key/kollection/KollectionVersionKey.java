/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.kollection;

import io.naraway.accent.util.json.JsonSerializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KollectionVersionKey implements JsonSerializable {
    //
    public static final String DELIMITER = "_";

    private String id;

    public KollectionVersionKey(String kollectionVersionId) {
        //
        this.id = kollectionVersionId;
        if (!isValid()) {
            throw new IllegalArgumentException("Invalid key: " + this.id);
        }
    }

    public KollectionVersionKey(String kollectionId, String version) {
        //
        this(String.format("%s%s%s", kollectionId, DELIMITER, version));
    }

    public static KollectionVersionKey fromId(String kollectionVersionId) {
        //
        return new KollectionVersionKey(kollectionVersionId);
    }

    public static KollectionVersionKey newInstance(String kollectionId, String version) {
        //
        return new KollectionVersionKey(kollectionId, version);
    }

    public String genKollectionId() {
        //
        return this.id.substring(0, this.id.indexOf(DELIMITER));
    }

    public String genKollectionVersion() {
        //
        return this.id.substring(this.id.indexOf(DELIMITER) + 1);
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

    public static KollectionVersionKey sample() {
        //
        return KollectionVersionKey.newInstance(
                "namoosori",
                "1.0.0");
    }

    public static void main(String[] args) {
        //
        KollectionVersionKey sample = sample();
        System.out.println(sample.toPrettyJson());
        System.out.println("Kollection Id: " + sample.genKollectionId());
        System.out.println("Kollection Version: " + sample.genKollectionVersion());
    }
}
