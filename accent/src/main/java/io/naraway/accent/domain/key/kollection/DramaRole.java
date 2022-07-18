/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.kollection;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DramaRole implements JsonSerializable {
    //
    private static final String DELIMITER = ":";

    private String code;
    private String name;
    private String description;
    private boolean defaultRole;
    private String dramaId;

    public String genRoleId() {
        //
        return String.format("%s%s%s", dramaId, DELIMITER, code);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static DramaRole fromJson(String json) {
        //
        return JsonUtil.fromJson(json, DramaRole.class);
    }

    public static DramaRole sample() {
        //
        return new DramaRole(
                "manager",
                "Course Manager",
                "Course Manager",
                true,
                "course"
        );
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}
