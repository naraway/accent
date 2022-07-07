/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.trail.dynamic;

public enum Connector {
    //
    And(";"),
    Or(","),
    End("");

    private final String connectorString;

    Connector(String connectorString) {
        this.connectorString = connectorString;
    }

    public String connectorString() {
        //
        return connectorString;
    }
}