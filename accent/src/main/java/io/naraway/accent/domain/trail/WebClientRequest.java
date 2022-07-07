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
public class WebClientRequest extends ClientRequest {
    //
    protected WebClientRequest() {
        //
        super();
    }

    protected WebClientRequest(TrailInfo trailInfo) {
        //
        super(trailInfo);
    }
}