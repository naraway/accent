/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.rolemap;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
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

    @Override
    public String toString() {
        //
        return this.toJson();
    }

    public static Kollectie fromJson(String json) {
        //
        return JsonUtil.fromJson(json, Kollectie.class);
    }

    public static Kollectie sample() {
        //
        return new Kollectie(
                "course",
                "Course Management",
                "Course Management Role",
                Collections.singletonList(KollectionRole.sample().getCode())
        );
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}