/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.message.dynamic;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Getter
@Setter
public class QueryParams implements JsonSerializable {
    //
    private static final List<Operator> specialOperator = Arrays.asList(Operator.Like, Operator.In, Operator.NotIn);
    @SuppressWarnings("java:S1700")
    private List<QueryParam> queryParams;

    public QueryParams() {
        //
        this.queryParams = new ArrayList<>();
    }

    public static QueryParams newInstance() {
        //
        return new QueryParams();
    }

    public static QueryParams newInstance(QueryParam queryParam) {
        //
        return new QueryParams().add(queryParam);
    }

    public static QueryParams newInstance(List<QueryParam> queryParams) {
        //
        return new QueryParams().addAll(queryParams);
    }

    public static QueryParams of(QueryParam... queryParams) {
        //
        return QueryParams.newInstance(List.of(queryParams));
    }

    public static QueryParams dynamic(QueryParam... queryParams) {
        //
        QueryParams params = QueryParams.newInstance();

        for (QueryParam param : queryParams) {
            if (param.getValue() != null && param.getValue().trim().length() > 0) {
                params.add(param);
            }
        }

        return params;
    }

    public boolean isEmpty() {
        //
        if (queryParams == null) {
            return true;
        }

        return queryParams.isEmpty();
    }

    public Stream<QueryParam> stream() {
        //
        if (queryParams == null) {
            return Collections.<QueryParam>emptyList().stream();
        }
        return queryParams.stream();
    }

    public QueryParams add(QueryParam queryParam) {
        //
        this.queryParams.add(queryParam);
        return this;
    }

    public QueryParams addAll(List<QueryParam> queryParams) {
        //
        this.queryParams.addAll(queryParams);
        return this;
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static QueryParams fromJson(String json) {
        //
        return JsonUtil.fromJson(json, QueryParams.class);
    }

    public static QueryParams sample() {
        //
        QueryParams queryParams = newInstance();
        queryParams.add(new QueryParam(
                "memberName",
                Operator.Equal,
                "Steve",
                Connector.And
        ));
        queryParams.add(new QueryParam(
                "memberAge",
                Operator.GreaterThan,
                "10",
                Connector.End
        ));

        return queryParams;
    }

    public static void main(String[] args) {
        //
        QueryParams queryParams = new QueryParams();
        queryParams.setQueryParams(List.of(new QueryParam("fieldName", Operator.In, "name", Connector.End)));
        String json = queryParams.toJson();

        System.out.println(QueryParams.fromJson(json).toPrettyJson());
        System.out.println(QueryParams.sample().toPrettyJson());
    }
}