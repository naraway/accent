/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.message;

import io.naraway.accent.domain.entity.DomainEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // empty constructor for jackson
public abstract class DataEvent extends EventMessage {
    //
    private String entityId;
    private DataEventType dataEventType;

    protected DataEvent(DataEventType dataEventType, DomainEntity domainEntity) {
        //
        this.dataEventType = dataEventType;
        this.entityId = domainEntity.getId();
    }
}