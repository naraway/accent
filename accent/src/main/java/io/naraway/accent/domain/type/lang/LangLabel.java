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
public class LangLabel implements JsonSerializable {
    //
    private String lang;
    private String label;

    private LangLabel(Locale locale, String label) {
        //
        this.lang = locale.getLanguage();
        this.label = label;
    }

    public static LangLabel newLabel(String langCode, String label) {
        //
        return new LangLabel(langCode, label);
    }

    public static LangLabel newLabel(Locale locale, String label) {
        //
        return new LangLabel(locale.getLanguage(), label);
    }

    public static LangLabel newLabel(String label) {
        //
        return new LangLabel(Locale.getDefault(), label);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static LangLabel fromJson(String json) {
        //
        return JsonUtil.fromJson(json, LangLabel.class);
    }

    public static LangLabel sample() {
        //
        return new LangLabel(Locale.KOREA.getLanguage(), "학습");
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
    }
}