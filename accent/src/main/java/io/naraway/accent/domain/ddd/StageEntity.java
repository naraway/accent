/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.ddd;

import io.naraway.accent.domain.key.stage.ActorKey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class StageEntity extends DomainEntity {
    //
    protected ActorKey requesterKey;

    protected StageEntity(String id, ActorKey requesterKey) {
        //
        super(id);
        this.requesterKey = requesterKey;
    }

    protected StageEntity(ActorKey requesterKey) {
        //
        super();
        this.requesterKey = requesterKey;
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
