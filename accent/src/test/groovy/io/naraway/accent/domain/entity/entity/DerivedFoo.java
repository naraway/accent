/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.entity.entity;

import io.naraway.accent.domain.annotation.Encrypt;
import io.naraway.accent.domain.annotation.Updatable;
import io.naraway.accent.domain.entity.DomainEntity;
import io.naraway.accent.domain.type.NameValue;
import io.naraway.accent.domain.type.NameValueList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("java:S2160")
public class DerivedFoo extends DomainEntity {
    //
    @Updatable
    private String name;
    @Encrypt
    private String password;
    @Encrypt
    private String socialNumber;
    @Updatable
    private boolean enabled;
    @Updatable
    private LocalDateTime dateTime;

    public DerivedFoo(DerivedFooCdo derivedFooCdo) {
        //
        super(derivedFooCdo.genId());
        derivedFooCdo.copyAttributes(this, List.of("name"));
    }

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
                case "dateTime":
                    this.dateTime = LocalDateTime.parse(value);
                    break;
                default:
                    throw new IllegalArgumentException("Update not allowed: " + nameValue);
            }
        }
    }
}
