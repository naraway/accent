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
public class DramaVersionKey implements JsonSerializable {
    //
    private static final String DELIMITER = "_";

    private String id;

    public DramaVersionKey(String dramaVersionId) {
        //
        this.id = dramaVersionId;
        if (!isValid()) {
            throw new IllegalArgumentException("Invalid key: " + this.id);
        }
    }

    public DramaVersionKey(String dramaId, String version) {
        //
        this(String.format("%s%s%s", dramaId, DELIMITER, version));
    }

    public static DramaVersionKey fromId(String dramaVersionId) {
        //
        return new DramaVersionKey(dramaVersionId);
    }

    public static DramaVersionKey newInstance(String dramaId, String version) {
        //
        return new DramaVersionKey(dramaId, version);
    }

    public String genDramaId() {
        //
        return this.id.substring(0, this.id.indexOf(DELIMITER));
    }

    public String genDramaVersion() {
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

    public static DramaVersionKey sample() {
        //
        return DramaVersionKey.newInstance(
                "timecard",
                "1.0.0");
    }

    public static void main(String[] args) {
        //
        DramaVersionKey sample = sample();
        System.out.println(sample.toPrettyJson());
        System.out.println("Drama Id: " + sample.genDramaId());
        System.out.println("Drama Version: " + sample.genDramaVersion());
    }
}
