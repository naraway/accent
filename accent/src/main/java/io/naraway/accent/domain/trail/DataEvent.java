/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail;

import io.naraway.accent.domain.ddd.DomainEntity;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
public abstract class DataEvent extends TrailMessage {
    //
    private DataEventType dataEventType;

    protected DataEvent() {
        //
        super(TrailMessageType.DataEvent);
        // for faster xml
    }

    protected DataEvent(DataEventType dataEventType) {
        //
        super(TrailMessageType.DataEvent);
        this.dataEventType = dataEventType;
    }

    protected DataEvent(TrailInfo trailInfo, DataEventType dataEventType) {
        //
        this(dataEventType);
        setTrailInfo(trailInfo);
    }

    @SuppressWarnings("java:S4838")
    public String getEntityId() {
        //
        Field[] attributes = getClass().getDeclaredFields();
        for (Object attribute : attributes) {
            if (attribute instanceof DomainEntity) {
                DomainEntity domainEntity = (DomainEntity) attribute;
                return domainEntity.getId();
            }
        }

        return null;
    }
}