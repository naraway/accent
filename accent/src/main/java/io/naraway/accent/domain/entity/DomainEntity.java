/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.entity;

import io.naraway.accent.domain.context.StageContext;
import io.naraway.accent.domain.type.IdName;
import io.naraway.accent.domain.type.NameValueList;
import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.uuid.TinyUUID;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@Setter
public abstract class DomainEntity implements JsonSerializable {
    //
    private String id;
    private long entityVersion;
    private String registeredBy;
    private long registeredOn;
    private String modifiedBy;
    private long modifiedOn;

    protected DomainEntity() {
        //
        this.id = TinyUUID.random();
        this.entityVersion = 0L;
        this.registeredBy = StageContext.get().getUsername();
        this.registeredOn = System.currentTimeMillis();
        this.modifiedBy = registeredBy;
        this.modifiedOn = registeredOn;
    }

    protected DomainEntity(String id) {
        //
        this();
        this.id = id;
    }

    protected DomainEntity(DomainEntity domainEntity) {
        //
        this();
        this.id = domainEntity.getId();
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

        DomainEntity entity = (DomainEntity) target;

        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        //
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public IdName genEntityIdName() {
        //
        return IdName.of(this.id, this.getClass().getSimpleName());
    }

    public void modify(NameValueList nameValues) {
        //
        NameValueList modificationNameValues = NameValueList.from(nameValues);

        if (hasEntityVersion(modificationNameValues)) {
            this.entityVersion = Long.parseLong(modificationNameValues.getValueOf("entityVersion"));
            modificationNameValues.remove("entityVersion");
        }

        this.modifyAttributes(modificationNameValues);
        this.modifiedBy = StageContext.get().getUsername();
        this.modifiedOn = System.currentTimeMillis();
    }

    protected abstract void modifyAttributes(NameValueList nameValues);

    private boolean hasEntityVersion(NameValueList nameValues) {
        //
        if (nameValues.containsName("entityVersion")) {
            return Pattern.compile("[+-]?\\d+").matcher(nameValues.getValueOf("entityVersion")).matches();
        }
        return false;
    }
}