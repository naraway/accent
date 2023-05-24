/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.context;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StageContext {
    //
    private static final ThreadLocal<StageRequest> context = new ThreadLocal<>();

    public static StageRequest get() {
        //
        if (!exists()) {
            setDefault();
        }

        return context.get();
    }

    public static void set(StageRequest request) {
        //
        context.set(request);
    }

    public static void clear() {
        //
        context.remove();
    }

    public static void setDefault() {
        //
        context.set(StageRequest.anonymous());
    }

    private static boolean exists() {
        //
        return context.get() != null;
    }
}