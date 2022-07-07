/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail;

import io.naraway.accent.domain.type.Offset;
import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class QueryResponse<T> implements JsonSerializable, TrailResponsible {
    //
    private T queryResult;
    private Offset offset;
    private boolean requestFailed;
    private FailureMessage failureMessage;

    public QueryResponse(T queryResult) {
        //
        this.queryResult = queryResult;
    }

    public QueryResponse(Offset offset) {
        //
        this.offset = offset;
    }

    public QueryResponse(FailureMessage failureMessage) {
        //
        if (failureMessage != null) {
            this.failureMessage = failureMessage;
            this.requestFailed = true;
        }
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static QueryResponse fromJson(String json) {
        //
        return JsonUtil.fromJson(json, QueryResponse.class);
    }

    @Override
    public boolean isRequestFailed() {
        //
        return this.requestFailed;
    }

    @Override
    public FailureMessage getFailureMessage() {
        //
        return this.failureMessage;
    }

    public static QueryResponse sample() {
        //
        return new QueryResponse(
                UUID.randomUUID().toString()
        );
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}
