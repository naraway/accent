/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail.dynamic;

public enum Operator {
    //
    Equal("=="),
    NotEqual("!="),
    GreaterThan("=gt="),
    GreaterThanOrEqual("=ge="),
    LessThan("=lt="),
    LessThanOrEqual("=le="),
    In("=in="),
    Like("=like="),
    Regex("=re="),
    NotIn("=out=");

    private final String operationString;

    Operator(String operationString) {
        this.operationString = operationString;
    }

    public String operationString() {
        //
        return operationString;
    }
}