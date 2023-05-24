/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.message.dynamic;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryParam implements JsonSerializable {
    //
    private String name;
    private Operator operator;
    private String value;
    private Connector connector;

    public String toSqlString() {
        //
        String rsql = name +
                operator.operatorString() +
                value +
                connector.connectorString();

        return rsql.trim();
    }

    public static QueryParam and(String name, Operator operator, String value) {
        //
        return new QueryParam(name, operator, value, Connector.And);
    }

    public static QueryParam or(String name, Operator operator, String value) {
        //
        return new QueryParam(name, operator, value, Connector.Or);
    }

    public static QueryParam end(String name, Operator operator, String value) {
        //
        return new QueryParam(name, operator, value, Connector.End);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static QueryParam fromJson(String json) {
        //
        return JsonUtil.fromJson(json, QueryParam.class);
    }

    public static QueryParam sample() {
        //
        String fieldName = "memberName";
        Operator operator = Operator.Equal;
        String value = "Steve";
        Connector connector = Connector.End;

        return new QueryParam(
                fieldName,
                operator,
                value,
                connector
        );
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}