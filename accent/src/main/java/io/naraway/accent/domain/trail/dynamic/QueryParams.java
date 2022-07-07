/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail.dynamic;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class QueryParams implements JsonSerializable {
    //
    private final List<Operator> specialOperator = Arrays.asList(Operator.Like, Operator.In, Operator.NotIn);
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

    public String toSqlString() {
        //
        StringBuilder rsql = new StringBuilder();

        queryParams.stream().forEach(queryParam -> {
            rsql.append(queryParam.getFieldName());
            rsql.append(queryParam.getOperation().operationString());
            if (specialOperator.contains(queryParam.getOperation())) {
                rsql.append(parseValue(queryParam.getOperation(), queryParam.getValue()));
            } else {
                rsql.append(queryParam.getValue());
            }
            rsql.append(queryParam.getConnector().connectorString());
        });

        String result = rsql.toString().trim();

        if (result.endsWith(Connector.And.connectorString()) || result.endsWith(Connector.Or.connectorString())) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    @SuppressWarnings("java:S3776")
    public Criteria toMongoCriteria() {
        //
        if (Optional.ofNullable(queryParams).isEmpty() || queryParams.isEmpty()) {
            return Criteria.where("id").ne("*");
        }
        return queryParams.stream().map(queryParam -> {
                    String fieldName = queryParam.getFieldName();
                    Connector connector = queryParam.getConnector();
                    String value = queryParam.getValue();
                    switch (queryParam.getOperation()) {
                        case NotEqual:
                            return Map.entry(
                                    new Criteria()
                                            .orOperator(parseValue(value).stream()
                                                    .map(v -> Criteria.where(fieldName).ne(v))
                                                    .collect(Collectors.toList())
                                            )
                                    , connector);
                        case In:
                            return Map.entry(new Criteria().orOperator(
                                    Criteria.where(fieldName).in(JsonUtil.fromJsonList(value, Object.class)),
                                    Criteria.where(fieldName).in(JsonUtil.fromJsonList(value, String.class))
                            ), connector);
                        case NotIn:

                            return Map.entry(new Criteria().orOperator(
                                    Criteria.where(fieldName).nin(JsonUtil.fromJsonList(value, Object.class)),
                                    Criteria.where(fieldName).nin(JsonUtil.fromJsonList(value, String.class))
                            ), connector);
                        case Like:
                        case Regex:
                            return Map.entry(Criteria.where(fieldName).regex(value), connector);
                        case LessThan:
                            return Map.entry(
                                    new Criteria()
                                            .orOperator(parseValue(value).stream()
                                                    .map(v -> Criteria.where(fieldName).lt(v))
                                                    .collect(Collectors.toList()))
                                    , connector);
                        case LessThanOrEqual:
                            return Map.entry(
                                    new Criteria()
                                            .orOperator(parseValue(value).stream()
                                                    .map(v -> Criteria.where(fieldName).lte(v))
                                                    .collect(Collectors.toList()))
                                    , connector);
                        case GreaterThan:

                            return Map.entry(
                                    new Criteria()
                                            .orOperator(parseValue(value).stream()
                                                    .map(v -> Criteria.where(fieldName).gt(v))
                                                    .collect(Collectors.toList()))
                                    , connector);
                        case GreaterThanOrEqual:

                            return Map.entry(
                                    new Criteria()
                                            .orOperator(parseValue(value).stream()
                                                    .map(v -> Criteria.where(fieldName).gte(v))
                                                    .collect(Collectors.toList()))
                                    , connector);
                        case Equal:
                        default:
                            return Map.entry(
                                    new Criteria()
                                            .orOperator(parseValue(value).stream()
                                                    .map(v -> Criteria.where(fieldName).is(v))
                                                    .collect(Collectors.toList()))
                                    , connector);
                    }
                }).reduce((prevCriteriaEntry, nextCriteriaEntry) -> {
                    Criteria prev = prevCriteriaEntry.getKey();
                    Criteria next = nextCriteriaEntry.getKey();
                    // And & Or Operator's priority is only depends on order.
                    switch (prevCriteriaEntry.getValue()) {
                        case Or:
                            return Map.entry(new Criteria().orOperator(prev, next), nextCriteriaEntry.getValue());
                        case And:
                        default:
                            return Map.entry(new Criteria().andOperator(prev, next), nextCriteriaEntry.getValue());
                    }
                }).map(Map.Entry::getKey)
                .orElseGet(() -> Criteria.where("id").is("*"));
    }

    @SuppressWarnings("java:S5669")
    private List<Object> parseValue(String value) {
        //
        if (value == null || value.equals("null")) {
            return Arrays.asList((Object) null);
        } else if (Arrays.asList("true", "false").contains(value)) {
            return Arrays.asList(Boolean.parseBoolean(value));
        } else if (isObject(value)) {
            return Arrays.asList(JsonUtil.fromJson(value, Object.class));
        } else {
            List<Object> objects = new ArrayList<>();
            objects.add(value);

            if (isNumeric(value)) {
                objects.add(JsonUtil.fromJson(value, Object.class));
            }
            return objects;
        }
    }

    private static boolean isNumeric(String strNum) {
        //
        return strNum.matches("[+-]?\\d*(\\.\\d+)?");
    }

    private static boolean isObject(String strNum) {
        //
        return strNum.matches("[{].*[}]");
    }

    public String toMariaSqlString() {
        //
        StringBuilder rsql = new StringBuilder();

        queryParams.stream().forEach(queryParam -> {
            rsql.append(queryParam.getFieldName());
            if (specialOperator.contains(queryParam.getOperation())) {
                Operator operator = replaceSpecialOperators(queryParam.getOperation());
                rsql.append(operator.operationString());
                rsql.append(parseValueForMaria(queryParam.getOperation(), queryParam.getValue()));
            } else {
                rsql.append(queryParam.getOperation().operationString());
                rsql.append(queryParam.getValue());
            }
            rsql.append(queryParam.getConnector().connectorString());
        });

        String result = rsql.toString().trim();

        if (result.endsWith(Connector.And.connectorString()) || result.endsWith(Connector.Or.connectorString())) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    @SuppressWarnings("java:S5361")
    private String parseValue(Operator operator, String value) {
        //
        String parsedQueryValues = "";
        String inQueryValues = value.trim();

        switch (operator) {
            case Like:
                String regex = ".*";
                inQueryValues = regex.concat(inQueryValues);
                parsedQueryValues = inQueryValues.concat(regex);
                break;
            case In:
            case NotIn:
                //["a","b"]
                inQueryValues = inQueryValues.replaceAll("\"", "");
                inQueryValues = inQueryValues.replace("[", "(");
                parsedQueryValues = inQueryValues.replace("]", ")");
                break;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator.operationString());
        }

        return parsedQueryValues;
    }

    private Operator replaceSpecialOperators(Operator operator) {
        //
        switch (operator) {
            case Like:
                return Operator.Equal;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator.operationString());
        }
    }

    @SuppressWarnings("java:S5361")
    private String parseValueForMaria(Operator operator, String value) {
        //
        String parsedQueryValues = "";
        String inQueryValues = value.trim();

        switch (operator) {
            case Like:
                String regex = "*";
                inQueryValues = regex.concat(inQueryValues);
                parsedQueryValues = inQueryValues.concat(regex);
                break;
            case In:
            case NotIn:
                //["a","b"]
                inQueryValues = inQueryValues.replaceAll("\"", "");
                inQueryValues = inQueryValues.replace("[", "(");
                parsedQueryValues = inQueryValues.replace("]", ")");
                break;
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator.operationString());
        }

        return parsedQueryValues;
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
        queryParams.setQueryParams(Arrays.asList(new QueryParam("fieldName", Operator.In, "name", Connector.End)));
        String json = queryParams.toJson();

        System.out.println(QueryParams.fromJson(json).toPrettyJson());
        System.out.println(QueryParams.sample().toPrettyJson());
    }
}
