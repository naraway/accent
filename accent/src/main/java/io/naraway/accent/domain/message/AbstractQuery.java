/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.message;

import com.fasterxml.jackson.annotation.JsonSetter;
import io.naraway.accent.domain.type.Offset;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

@Getter
@Setter
public abstract class AbstractQuery<T> implements DomainMessage {
    //
    private QueryResponse<T> response;
    private Offset offset;

    protected AbstractQuery() {
        //
        this.offset = Offset.newUnlimited();
    }

    protected AbstractQuery(Offset offset) {
        //
        this.offset = offset;
    }

    public QueryResponse<T> getResponse() {
        //
        if (this.response == null) {
            this.response = new QueryResponse<>();
        }

        return this.response;
    }

    @JsonSetter
    public void setResponse(QueryResponse<T> queryResponse) {
        //
        this.response = queryResponse;
        this.response.setOffset(this.offset);
    }

    public void setResponse(Object queryResult) {
        //
        if (queryResult == null) {
            this.response = new QueryResponse<>();
            this.response.setOffset(this.offset);
            return;
        }

        QueryResultType queryResultType = getResponseType(queryResult);

        switch (queryResultType) {
            case PAGE:
            case SLICE:
                this.response = new QueryResponse<>(getContent(queryResult));
                if (queryResultType == QueryResultType.PAGE) {
                    this.offset.setTotalCount(getTotalElements(queryResult));
                }
                this.offset.setPrevious(hasPrevious(queryResult));
                this.offset.setNext(hasNext(queryResult));
                this.response.setOffset(this.offset);
                break;
            default:
                this.response = new QueryResponse<>((T) queryResult);
                this.response.setOffset(this.offset);
        }
    }

    public void setResponse(FailureMessage failureMessage) {
        //
        this.response = new QueryResponse<>(failureMessage);
        this.response.setOffset(this.offset);
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

    @SuppressWarnings("java:S2589")
    private QueryResultType getResponseType(Object queryResult) {
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

    private T getContent(Object queryResult) {
        //
        String nameForContent = "getContent";

        try {
            return (T) queryResult.getClass().getMethod(nameForContent).invoke(queryResult);
        } catch (Exception e) {
            //
        }

        return null;
    }

    private long getTotalElements(Object queryResult) {
        //
        String nameForTotalName = "getTotalElements";

        try {
            return (long) queryResult.getClass().getMethod(nameForTotalName).invoke(queryResult);
        } catch (Exception e) {
            //
        }

        return 0;
    }

    private boolean hasNext(Object queryResult) {
        //
        String nameForNext = "hasNext";

        try {
            return (boolean) queryResult.getClass().getMethod(nameForNext).invoke(queryResult);
        } catch (Exception e) {
            //
        }

        return false;
    }

    private boolean hasPrevious(Object queryResult) {
        //
        String nameForPrevious = "hasPrevious";

        try {
            return (boolean) queryResult.getClass().getMethod(nameForPrevious).invoke(queryResult);
        } catch (Exception e) {
            //
        }

        return false;
    }

    private enum QueryResultType {
        //
        PAGE,
        SLICE,
        OBJECT
    }
}