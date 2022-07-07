/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type.lang;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocaleString implements JsonSerializable {
    //
    private String locale;
    private String string;

    private LocaleString(Locale locale, String string) {
        //
        this.locale = locale.getLanguage();
        this.string = string;
    }

    public static LocaleString newInstance(String locale, String string) {
        //
        return new LocaleString(locale, string);
    }

    public static LocaleString newInstance(Locale locale, String string) {
        //
        return newInstance(locale.getLanguage(), string);
    }

    public static LocaleString newInstance(String string) {
        //
        return newInstance(Locale.getDefault(), string);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static LocaleString fromJson(String json) {
        //
        return JsonUtil.fromJson(json, LocaleString.class);
    }

    public static LocaleString sample() {
        //
        return LocaleString.newInstance(Locale.KOREA.getLanguage(), "학습");
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}
