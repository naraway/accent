/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail;

import com.fasterxml.jackson.annotation.JsonSetter;
import io.naraway.accent.domain.type.Offset;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

@Getter
@Setter
public abstract class AbstractQuery<T> extends TrailMessage {
    //
    private QueryResponse<T> response;
    private Offset offset;

    protected AbstractQuery(TrailMessageType messageType) {
        //
        super(messageType);
        this.setResponse(new QueryResponse<>(offset));
    }

    protected AbstractQuery(TrailInfo trailInfo, TrailMessageType messageType) {
        //
        super(trailInfo, messageType);
    }

    public QueryResponse<T> getResponse() {
        //
        if (this.response == null) {
            this.response = new QueryResponse<>();
        }

        return this.response;
    }

    @JsonSetter
    public void setResponse(QueryResponse<T> response) {
        //
        this.response = response;
    }

    public void setResponse(T t) {
        //
        this.response = new QueryResponse<>(t);
    }

    public void setResponse(Page page) {
        //
        this.response = new QueryResponse<>((T) page.getContent());
        this.offset.setTotalCount(page.getTotalElements());
        this.offset.setPrevious(page.hasPrevious());
        this.offset.setNext(page.hasNext());
        this.response.setOffset(this.offset);
    }

    public void setResponse(Slice slice) {
        //
        this.response = new QueryResponse<>((T) slice.getContent());
        this.offset.setPrevious(slice.hasPrevious());
        this.offset.setNext(slice.hasNext());
    }

    public void setResponse(FailureMessage failureMessage) {
        //
        this.response = new QueryResponse<>(failureMessage);
    }

    public void setOffset(Offset offset) {
        //
        this.offset = offset;
        if (this.getResponse() == null) {
            this.setResponse(new QueryResponse<>(offset));
        } else {
            this.getResponse().setOffset(offset);
        }
    }

    public String toJsonWithoutQueryResult() {
        //
        T queryResult = this.getResponse().getQueryResult();
        this.response.setQueryResult(null);

        String jsonWithoutQueryResult = toJson();
        this.response.setQueryResult(queryResult);

        return jsonWithoutQueryResult;
    }
}
