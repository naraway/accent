/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.entity;

import io.naraway.accent.domain.tenant.ActorKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class StageEntity extends DomainEntity {
    //
    protected ActorKey requesterKey;
    protected String stageId;
    protected String pavilionId;

    protected StageEntity(String id, ActorKey requesterKey) {
        //
        super(id);
        this.assignContext(requesterKey);
    }

    protected StageEntity(ActorKey requesterKey) {
        //
        super();
        this.assignContext(requesterKey);
    }

    private void assignContext(ActorKey requesterKey) {
        //
        this.requesterKey = requesterKey;
        this.stageId = requesterKey.genStageId();
        this.pavilionId = requesterKey.genPavilionId();
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
}