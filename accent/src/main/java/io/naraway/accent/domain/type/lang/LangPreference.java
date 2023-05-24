/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type.lang;

import io.naraway.accent.domain.entity.ValueObject;
import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
public class LangPreference implements ValueObject {
    //
    private String defaultLang;
    private List<String> supportLangs;

    public LangPreference(String defaultLang) {
        //
        this.defaultLang = defaultLang;
    }

    public LangPreference(String defaultLang, List<String> supportLangs) {
        //
        this.defaultLang = defaultLang;
        this.supportLangs = supportLangs;
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static LangPreference fromJson(String json) {
        //
        return JsonUtil.fromJson(json, LangPreference.class);
    }

    public static LangPreference systemDefault() {
        //
        return new LangPreference(Locale.getDefault().getLanguage());
    }

    public static LangPreference sample() {
        //
        LangPreference sample = new LangPreference(
                Locale.US.getLanguage()
        );

        sample.setSupportLangs(Collections.singletonList(
                Locale.KOREA.getLanguage()
        ));

        return sample;
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}