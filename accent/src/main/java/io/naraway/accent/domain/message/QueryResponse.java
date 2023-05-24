/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.message;

import io.naraway.accent.domain.type.Offset;
import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import io.naraway.accent.util.uuid.TinyUUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class QueryResponse<T> implements JsonSerializable {
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

    @SuppressWarnings({"java:S3740", "unchecked"})
    public static QueryResponse<Objects> fromJson(String json) {
        //
        return JsonUtil.fromJson(json, QueryResponse.class);
    }

    @SuppressWarnings("java:S3740")
    public static QueryResponse<String> sample() {
        //
        return new QueryResponse<>(TinyUUID.random());
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}