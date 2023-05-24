package io.naraway.accent.domain.rolemap;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SceneKey implements JsonSerializable {
    //
    private String domain;
    private String kollection;
    private String kollectie;
    private String scene;

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static SceneKey fromJson(String json) {
        //
        return JsonUtil.fromJson(json, SceneKey.class);
    }

    public String genUrl() {
        //
        return String.format("%s/%s/%s%s", domain, kollection, kollectie, scene);
    }

    public static SceneKey sample() {
        //
        return SceneKey
                .builder()
                .domain("http://lab.naraway.io")
                .kollection("metropolis")
                .kollectie("workspace")
                .scene("/cineroom-role-books/modify")
                .build();
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
        System.out.println(sample().genUrl());
    }
}