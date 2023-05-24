/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type.lang;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Locale;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Lang {
    //
    public static final String KOREAN = Locale.KOREAN.getLanguage();
    public static final String CHINESE = Locale.CHINESE.getLanguage();
    public static final String ENGLISH = Locale.US.getLanguage();
    public static final String JAPANESE = Locale.JAPANESE.getLanguage();
}