/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.message;


import io.naraway.accent.domain.message.dynamic.QueryParams;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class DynamicQueryRequest<T> extends AbstractQuery<T> {
    //
    protected QueryParams queryParams;

    protected DynamicQueryRequest() {
        //
        super();
        this.queryParams = QueryParams.newInstance();
    }
}