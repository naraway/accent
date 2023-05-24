/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.tenant;

public enum MemberType {
    //
    Regular("R"),
    Guest("G");

    private final String initial;

    MemberType(String initial) {
        //
        this.initial = initial;
    }

    public String initial() {
        //
        return initial;
    }

    public MemberType fromInitial(String initial) {
        //
        switch (initial) {
            case "R":
                return Regular;
            case "G":
                return Guest;
            default:
                throw new IllegalArgumentException("Invalid initial: " + initial);
        }
    }
}