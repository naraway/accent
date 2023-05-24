/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.entity.entity;

import io.naraway.accent.domain.annotation.Encrypt;
import io.naraway.accent.domain.annotation.FieldImmutable;
import io.naraway.accent.domain.annotation.FieldSourceId;
import io.naraway.accent.domain.entity.DomainEntity;
import io.naraway.accent.domain.entity.ValueObject;
import io.naraway.accent.domain.type.NameValue;
import io.naraway.accent.domain.type.NameValueList;
import io.naraway.accent.util.json.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("java:S2160")
public class FooV4_1 extends DomainEntity {
    //
    private String name;
    private String password;
    @FieldImmutable
    private String socialNumber;
    private PhoneCodeName phoneCodeName;
    @FieldImmutable
    private boolean enabled;
    private LocalDateTime dateTime;

    @FieldSourceId
    private String referenceId;

    @Override
    protected void modifyAttributes(NameValueList nameValues) {
        //
        for (NameValue nameValue : nameValues.list()) {
            String value = nameValue.getValue();
            switch (nameValue.getName()) {
                case "name":
                    this.name = value;
                    break;
                case "password":
                    this.password = value;
                    break;
                case "phoneCodeName":
                    this.phoneCodeName = PhoneCodeName.fromJson(value);
                    break;
                case "dateTime":
                    this.dateTime = LocalDateTime.parse(value);
                    break;
                default:
                    throw new IllegalArgumentException("Update not allowed: " + nameValue);
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PhoneCodeName implements ValueObject {
        //
        private String code;
        @Encrypt
        private String name;
        private LocalDateTime dateTime;

        @Override
        public String toString() {
            //
            return toJson();
        }

        public static PhoneCodeName fromJson(String json) {
            //
            return JsonUtil.fromJson(json, PhoneCodeName.class);
        }
    }
}
