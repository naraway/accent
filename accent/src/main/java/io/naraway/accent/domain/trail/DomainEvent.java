/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class DomainEvent extends TrailMessage {
    //
    protected DomainEvent() {
        //
        super(TrailMessageType.DomainEvent);
    }

    protected DomainEvent(TrailInfo trailInfo) {
        //
        this();
        super.setTrailInfo(trailInfo);
    }
}