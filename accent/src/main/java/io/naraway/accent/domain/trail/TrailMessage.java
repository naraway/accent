/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail;

import io.naraway.accent.util.json.JsonSerializable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class TrailMessage implements JsonSerializable {
    //
    private String id;
    private TrailMessageType messageType;
    private TrailInfo trailInfo;

    protected TrailMessage() {
        //
        this.id = UUID.randomUUID().toString();
        if (TrailContext.get() != null) {
            this.trailInfo = TrailContext.get().copy();
            this.trailInfo.setMessageId(this.id);
            this.trailInfo.setMessage(this.getClass().getSimpleName());
        }
    }

    protected TrailMessage(TrailMessageType messageType) {
        //
        this();
        this.messageType = messageType;
    }

    protected TrailMessage(TrailInfo trailInfo, TrailMessageType messageType) {
        //
        this(messageType);
        this.trailInfo = trailInfo;
    }

    protected TrailMessage(String id, TrailInfo trailInfo, TrailMessageType messageType) {
        //
        this(trailInfo, messageType);
        this.id = id;
    }

    public void calculateWaitingTime() {
        //
        this.trailInfo.calculateWaitingTime();
    }
}