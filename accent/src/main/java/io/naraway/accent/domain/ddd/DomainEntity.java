/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.ddd;

import io.naraway.accent.domain.type.IdName;
import io.naraway.accent.domain.type.NameValueList;
import io.naraway.accent.util.json.JsonSerializable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public abstract class DomainEntity implements JsonSerializable {
    //
    private String id;
    private long entityVersion;
    private long registrationTime;
    private long modificationTime;

    protected DomainEntity() {
        //
        this.id = UUID.randomUUID().toString();
        this.registrationTime = System.currentTimeMillis();
    }

    protected DomainEntity(String id) {
        //
        this.id = id;
        this.registrationTime = System.currentTimeMillis();
    }

    protected DomainEntity(DomainEntity domainEntity) {
        //
        this.id = domainEntity.getId();
        this.entityVersion = domainEntity.getEntityVersion();
    }

    public IdName genEntityIdName() {
        //
        return new IdName(this.id, this.getClass().getSimpleName());
    }

    public boolean equals(Object target) {
        //
        if (this == target) {
            return true;
        }

        if (target == null || getClass() != target.getClass()) {
            return false;
        }

        DomainEntity entity = (DomainEntity) target;

        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public void modify(NameValueList nameValues) {
        //
        this.modifyAttributes(nameValues);
        this.modificationTime = System.currentTimeMillis();
    }

    protected abstract void modifyAttributes(NameValueList nameValues);
}
