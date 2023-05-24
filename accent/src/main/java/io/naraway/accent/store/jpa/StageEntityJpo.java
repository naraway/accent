/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.store.jpa;

import io.naraway.accent.domain.entity.StageEntity;
import io.naraway.accent.domain.tenant.ActorKey;
import io.naraway.accent.domain.tenant.TenantKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class StageEntityJpo extends DomainEntityJpo {
    //
    protected String actorId;
    protected String stageId;
    protected String pavilionId;

    protected StageEntityJpo(StageEntity stageEntity) {
        //
        super(stageEntity);
        if (stageEntity.getRequesterKey() != null) {
            // check internal user
            ActorKey requesterKey = stageEntity.getRequesterKey().getId().contains(TenantKey.MEMBER_DELIMITER)
                    ? stageEntity.getRequesterKey()
                    : ActorKey.fromId(String.format("%s@0:0:0:0-0", stageEntity.getRequesterKey().getId()));
            this.pavilionId = stageEntity.getPavilionId();
            this.actorId = requesterKey.getId();
            this.stageId = stageEntity.getStageId();
        }
    }

    @Override
    public boolean equals(Object target) {
        //
        return super.equals(target);
    }

    @Override
    public int hashCode() {
        //
        return super.hashCode();
    }

    protected ActorKey genRequesterKey() {
        //
        if (actorId == null || actorId.trim().isEmpty() || TenantKey.getTenantType(this.actorId) == null) {
            return ActorKey.fromId("0@0:0:0:0-0"); // return stub actor key
        }

        return ActorKey.fromId(this.actorId);
    }
}