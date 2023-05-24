/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.entity;

import io.naraway.accent.domain.context.StageContext;
import io.naraway.accent.domain.context.StageRequest;
import io.naraway.accent.domain.tenant.ActorKey;
import io.naraway.accent.domain.tenant.TenantKey;
import io.naraway.accent.domain.type.NameValue;
import io.naraway.accent.domain.type.NameValueList;
import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.uuid.TinyUUID;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public abstract class CreationDataObject implements JsonSerializable {
    //
    protected ActorKey requesterKey;
    protected NameValueList additionalAttributes;

    protected CreationDataObject() {
        //
        String actorId = StageContext.get().getActorId();

        if (actorId == null || actorId.trim().length() == 0 || TenantKey.getTenantType(actorId) == null) {
            actorId = StageRequest.anonymous().getActorId();
        }

        requesterKey = ActorKey.fromId(actorId);
    }

    public String genId() {
        //
        return TinyUUID.random();
    }

    public ActorKey getRequesterKey() {
        //
        return requesterKey;
    }

    public void setAdditionalAttributes(NameValueList additionalAttributes) {
        //
        this.additionalAttributes = additionalAttributes;
    }

    public void addAdditionalAttributes(String name, Object value) {
        //
        this.getAdditionalAttributes().add(NameValue.of(name, value));
    }

    public void addAdditionalAttributes(Object... nameValues) {
        //
        if (nameValues.length == 0 || nameValues.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid name-value pairs");
        }

        for (int i = 0; i < nameValues.length / 2; i++) {
            this.addAdditionalAttributes(nameValues[i * 2].toString(), nameValues[i * 2 + 1]);
        }
    }

    public void addAdditionalAttributes(NameValueList nameValues) {
        //
        for (NameValue nameValue : nameValues.list()) {
            this.getAdditionalAttributes().add(nameValue);
        }
    }

    public NameValueList getAdditionalAttributes() {
        //
        if (this.additionalAttributes == null) {
            this.additionalAttributes = NameValueList.newInstance();
        }

        return this.additionalAttributes;
    }

    public final boolean hasAdditionalAttributes() {
        //
        return !this.getAdditionalAttributes().getNameValues().isEmpty();
    }

    public final boolean hasAnyNullValues() {
        //
        List<String> nullValueAttributes = listNullValueAttributes();

        return !nullValueAttributes.isEmpty();
    }

    public final void validate() {
        //
        List<String> nullValueAttributes = listNullValueAttributes();

        if (!nullValueAttributes.isEmpty()) {
            throw new IllegalArgumentException(
                    "Null value is not allowed in CDO: " + String.join(", ", nullValueAttributes));
        }
    }

    @SuppressWarnings("java:S3011")
    private List<String> listNullValueAttributes() {
        //
        List<String> nullValueAttributes = new ArrayList<>();

        Field[] fields = this.getClass().getDeclaredFields();

        for (Field field : fields) {
            if (field.getName().equals("additionalAttributes")) {
                continue;
            }

            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (value == null) {
                    nullValueAttributes.add(field.getName());
                }
            } catch (Exception e) {
                nullValueAttributes.add(field.getName());
            }
        }

        return nullValueAttributes;
    }
}