package io.naraway.accent.domain.entity;

import io.naraway.accent.util.entity.Beans;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public abstract class CopyDataObject<T extends DomainEntity> extends CreationDataObject {
    //
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Set<String> ignoreProperties;

    protected T sourceEntity;

    protected CopyDataObject() {
        //
        super();
        this.ignoreProperties = new HashSet<>(Arrays.asList(
                "id",
                "entityVersion",
                "registeredBy",
                "registeredOn",
                "modifiedBy",
                "modifiedOn"));
    }

    protected CopyDataObject(T sourceEntity) {
        //
        this();
        this.sourceEntity = sourceEntity;
    }

    @Override
    public String genId() {
        //
        return sourceEntity.getId();
    }

    public void copyAttributes(DomainEntity targetEntity, String... ignoreAttributes) {
        //
        if (ignoreAttributes != null) {
            this.ignoreProperties.addAll(Arrays.asList(ignoreAttributes));
        }

        copyAttributes(targetEntity);
    }

    public void copyAttributes(DomainEntity targetEntity, List<String> ignoreAttributes) {
        //
        if (ignoreAttributes != null) {
            this.ignoreProperties.addAll(ignoreAttributes);
        }

        copyAttributes(targetEntity);
    }

    private void copyAttributes(DomainEntity targetEntity) {
        //
        Beans.copyProperties(this.sourceEntity, targetEntity, this.ignoreProperties.toArray(new String[0]));
    }
}