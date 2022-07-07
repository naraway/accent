/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.StringTokenizer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdName implements JsonSerializable {
    //
    private String id;
    private String name;

    public static IdName of(String id, String name) {
        //
        return new IdName(id, name);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public String toSimpleString() {
        //
        return id + ":" + name;
    }

    public static IdName fromSimpleString(String idNameStr) {
        //
        StringTokenizer tokenizer = new StringTokenizer(idNameStr, ":");
        String id = tokenizer.nextToken();
        String name = tokenizer.nextToken();

        return new IdName(id, name);
    }

    public static IdName fromJson(String json) {
        //
        return JsonUtil.fromJson(json, IdName.class);
    }

    @Override
    public boolean equals(Object target) {
        //
        if (this == target) {
            return true;
        }

        if (target == null || getClass() != target.getClass()) {
            return false;
        }

        IdName idName = (IdName) target;
        return Objects.equals(this.id, idName.id)
                && Objects.equals(this.name, idName.name);
    }

    @Override
    public int hashCode() {
        //
        return Objects.hash(id + name);
    }

    public static IdName sample() {
        //
        String id = "1234";
        String name = "Steve Jobs";

        return new IdName(id, name);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
        System.out.println(IdName.fromSimpleString(sample().toSimpleString()));
    }
}
