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
public class Kollectie implements JsonSerializable {
    //
    private String path;
    private String name;
    private String description;
    private List<String> requiredRoles;

    public static Kollectie fromJson(String json) {
        //
        return JsonUtil.fromJson(json, Kollectie.class);
    }

    @Override
    public String toString() {
        //
        return this.toJson();
    }

    public static Kollectie sample() {
        //
        KollectionRole requiredRole = new KollectionRole(
                "manager",
                "Course Manager",
                "Course Manager Role",
                false
        );

        return new Kollectie(
                "course",
                "Course Management",
                "Course Management Role",
                Arrays.asList(requiredRole.getCode())
        );
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}
