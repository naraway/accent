/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import io.naraway.accent.util.uuid.TinyUUID;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdNameValueList implements JsonSerializable {
    //
    private String id;
    @Setter(AccessLevel.NONE)
    private NameValueList nameValues;

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static IdNameValueList fromJson(String json) {
        //
        return JsonUtil.fromJson(json, IdNameValueList.class);
    }

    public static IdNameValueList sample() {
        //
        return new IdNameValueList(
                TinyUUID.random(),
                NameValueList.of("name", "value"));
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}