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
public abstract class ClientRequest extends TrailMessage {
    //
    protected ClientRequest() {
        //
        super(TrailMessageType.ClientRequest);
    }

    protected ClientRequest(TrailInfo trailInfo) {
        //
        super(trailInfo, TrailMessageType.ClientRequest);
    }
}