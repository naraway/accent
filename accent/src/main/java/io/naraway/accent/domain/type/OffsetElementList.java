/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.*;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OffsetElementList<T> implements JsonSerializable {
    //
    private List<T> results;
    private long total;

    // to use restful paginated list
    public static <T> OffsetElementList<T> from(Object results) {
        //
        if (results == null) {
            return new OffsetElementList<>(Collections.emptyList(), -1);
        }

        QueryResultType resultsType = getResponseType(results);
        switch (resultsType) {
            case PAGE:
            case SLICE:
                List<T> elements = getContent(results);
                long total = -1;
                if (resultsType == QueryResultType.PAGE) {
                    total = getTotalElements(results);
                }
                return new OffsetElementList<>(elements, total);
            default:
                if (List.class.isAssignableFrom(results.getClass())) {
                    return new OffsetElementList<>((List) results, ((List) results).size());
                } else {
                    return new OffsetElementList<>(List.of((T) results), 1);
                }
        }
    }

    @SuppressWarnings("java:S2589")
    private static QueryResultType getResponseType(Object queryResult) {
        //
        String nameForContent = "getContent";
        String nameForTotal = "getTotalElements";
        String nameForNext = "hasNext";
        String nameForPrevious = "hasPrevious";

        boolean hasMethodForContent = false;
        boolean hasMethodForTotal = false;
        boolean hasMethodForNext = false;
        boolean hasMethodForPrevious = false;

        Method[] methods = queryResult.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(nameForContent)) {
                hasMethodForContent = true;
            } else if (method.getName().equals(nameForTotal)) {
                hasMethodForTotal = true;
            } else if (method.getName().equals(nameForNext)) {
                hasMethodForNext = true;
            } else if (method.getName().equals(nameForPrevious)) {
                hasMethodForPrevious = true;
            }
        }

        if (hasMethodForContent && hasMethodForNext && hasMethodForPrevious && hasMethodForTotal) {
            return QueryResultType.PAGE;
        } else if (hasMethodForContent && hasMethodForNext && hasMethodForPrevious) {
            return QueryResultType.SLICE;
        }

        return QueryResultType.OBJECT;
    }

    private static <T> T getContent(Object queryResult) {
        //
        String nameForContent = "getContent";

        try {
            return (T) queryResult.getClass().getMethod(nameForContent).invoke(queryResult);
        } catch (Exception e) {
            //
        }

        return null;
    }

    private static long getTotalElements(Object queryResult) {
        //
        String nameForTotalName = "getTotalElements";

        try {
            return (long) queryResult.getClass().getMethod(nameForTotalName).invoke(queryResult);
        } catch (Exception e) {
            //
        }

        return 0;
    }

    private enum QueryResultType {
        //
        PAGE,
        SLICE,
        OBJECT
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static OffsetElementList fromJson(String json) {
        //
        return JsonUtil.fromJson(json, OffsetElementList.class);
    }

    public static OffsetElementList sample() {
        //
        return new OffsetElementList<String>(Collections.emptyList(), 0);
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}
