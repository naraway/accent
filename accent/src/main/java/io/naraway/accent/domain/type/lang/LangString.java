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
public class LangString implements JsonSerializable {
    //
    private String language;
    private String string;

    private LangString(Locale locale,
                       String string) {
        //
        this.language = locale.getLanguage();
        this.string = string;
    }

    public static LangString newString(String langCode,
                                       String string) {
        //
        return new LangString(langCode, string);
    }

    public static LangString newString(Locale locale, String string) {
        //
        return new LangString(locale.getLanguage(), string);
    }

    public static LangString newString(String string) {
        //
        return new LangString(Locale.getDefault(), string);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static LangString fromJson(String json) {
        //
        return JsonUtil.fromJson(json, LangString.class);
    }

    public static LangString sample() {
        //
        return new LangString(Locale.KOREA.getLanguage(), "학습");
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
    }
}
