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

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KollectionRole implements JsonSerializable {
    //
    private String code;
    private String name;
    private String description;
    private boolean defaultRole;
    private List<DramaRole> dramaRoles;

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static KollectionRole fromJson(String json) {
        //
        return JsonUtil.fromJson(json, KollectionRole.class);
    }

    public static KollectionRole sample() {
        //
        return new KollectionRole(
                "member",
                "Member",
                "Member Role",
                true,
                Arrays.asList(DramaRole.sample())
        );
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}
