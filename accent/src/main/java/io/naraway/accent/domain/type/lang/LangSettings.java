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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
public class LangSettings implements ValueObject {
    //
    private Locale locale;
    private String baseLanguage;
    private List<String> supportLanguages;

    public LangSettings(Locale locale,
                        String baseLanguage,
                        List<String> supportLanguages) {
        //
        this.locale = locale;
        this.baseLanguage = baseLanguage;
        this.supportLanguages = supportLanguages;
    }

    public LangSettings(String baseLanguage) {
        //
        this(Locale.getDefault(), baseLanguage, Collections.singletonList(baseLanguage));
    }

    public LangSettings(String baseLanguage,
                        List<String> supportLanguages) {
        //
        this(Locale.getDefault(), baseLanguage, supportLanguages);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static LangSettings fromJson(String json) {
        //
        return JsonUtil.fromJson(json, LangSettings.class);
    }

    public static LangSettings sample() {
        //
        return new LangSettings(
                Locale.KOREA.getLanguage(),
                Arrays.asList(Locale.KOREA.getLanguage(), Locale.US.getLanguage())
        );
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
    }
}