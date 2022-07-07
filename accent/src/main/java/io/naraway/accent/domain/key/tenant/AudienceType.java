/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.key.tenant;

public enum AudienceType {
    //
    Regular("r"),
    Guest("g");

    private final String initial;

    AudienceType(String initial) {
        //
        this.initial = initial;
    }

    public String initial() {
        //
        return initial;
    }

    public AudienceType fromInitial(String initial) {
        //
        switch (initial) {
            case "r":
                return Regular;
            case "g":
                return Guest;
            default:
                throw new IllegalArgumentException("Invalid initial: " + initial);
        }
    }
}
