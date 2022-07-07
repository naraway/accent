/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type;

public enum Tier {
    //
    Primary,
    Secondary,
    Third;

    public boolean isPrimary() {
        //
        return this.equals(Primary);
    }

    public boolean isSecondary() {
        //
        return this.equals(Secondary);
    }

    public boolean isThird() {
        //
        return this.equals(Third);
    }

    public static void main(String[] args) {
        //
        System.out.println(Tier.Primary.isPrimary());
    }
}
