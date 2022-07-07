/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.store.mongo;

import io.naraway.accent.domain.ddd.StageEntity;
import io.naraway.accent.domain.key.stage.ActorKey;
import io.naraway.accent.domain.key.stage.StageKey;
import io.naraway.accent.domain.key.tenant.CineroomKey;
import io.naraway.accent.domain.key.tenant.CitizenKey;
import io.naraway.accent.domain.key.tenant.PavilionKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StageEntityDoc extends DomainEntityDoc {
    //
    protected String requesterPavilionId;
    protected String requesterCineroomId;
    protected String requesterCitizenId;
    protected String requesterStageId;
    protected String requesterActorId;

    public StageEntityDoc(StageEntity stageEntity) {
        //
        super(stageEntity);
        ActorKey requesterKey = stageEntity.getRequesterKey();
        if (requesterKey != null) {
            this.requesterPavilionId = requesterKey.genPavilionId();
            this.requesterCineroomId = requesterKey.genCineroomId();
            this.requesterCitizenId = requesterKey.genCitizenId();
            this.requesterStageId = requesterKey.genStageId();
            this.requesterActorId = requesterKey.getId();
        }
    }

    public PavilionKey genPavilionKey() {
        //
        return ActorKey.fromId(requesterActorId).genPavilionKey();
    }

    public CitizenKey getCitizenKey() {
        //
        return ActorKey.fromId(requesterActorId).genCitizenKey();
    }

    public CineroomKey getCineroomKey() {
        //
        return ActorKey.fromId(requesterActorId).genCineroomKey();
    }

    public StageKey genStageKey() {
        //
        return ActorKey.fromId(requesterActorId).genStageKey();
    }

    public ActorKey genActorKey() {
        //
        return ActorKey.fromId(requesterActorId);
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
