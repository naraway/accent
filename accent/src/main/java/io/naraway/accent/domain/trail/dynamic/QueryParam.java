/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail.dynamic;

import io.naraway.accent.util.json.JsonSerializable;
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
    private String fieldName;
    private Operator operation;
    private String value;
    private Connector connector;

    public String toSqlString() {
        //
        StringBuilder rsql = new StringBuilder();

        rsql.append(fieldName);
        rsql.append(operation.operationString());
        rsql.append(value);
        rsql.append(connector.connectorString());

        return rsql.toString().trim();
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