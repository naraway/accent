/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail;


import io.naraway.accent.domain.trail.dynamic.QueryParams;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class DynamicQueryRequest<T> extends AbstractQuery<T> {
    //
    private QueryParams queryParams;

    protected DynamicQueryRequest() {
        //
        super(TrailMessageType.DynamicQueryRequest);
        this.queryParams = QueryParams.newInstance();
    }

    protected DynamicQueryRequest(TrailInfo trailInfo) {
        //
        super(trailInfo, TrailMessageType.DynamicQueryRequest);
    }

    public String genSqlString() {
        //
        return (queryParams == null) ? "" : queryParams.toSqlString();
    }
}