/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type.lang;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@SuppressWarnings("ALL")
@Getter
@Setter
@NoArgsConstructor
public class LangLabels implements JsonSerializable {
    //
    private String defaultLanguage;                     // English
    private Map<String, String> langLabelMap;           // langCode : label text

    private LangLabels(String defaultLanguage, String defaultLabel) {
        //
        this.defaultLanguage = defaultLanguage;
        this.langLabelMap = new HashMap<>();
        this.langLabelMap.put(defaultLanguage, defaultLabel);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static LangLabels fromJson(String json) {
        //
        return JsonUtil.fromJson(json, LangLabels.class);
    }

    public static LangLabels newLabel(String defaultLanguage, String defaultLabel) {
        //
        return new LangLabels(defaultLanguage, defaultLabel);
    }

    public LangLabels addLabel(String langCode, String label) {
        //
        this.langLabelMap.put(langCode, label);
        return this;
    }

    public int countLabel() {
        //
        return (this.langLabelMap.size());
    }

    public String getLabel(String langCode) {
        //
        return langLabelMap.get(langCode);
    }

    public void removeLabel(String langCode) {
        //
        langLabelMap.remove(langCode);
    }

    public static LangLabels sample() {
        //
        String defaultLabel = "Student";
        LangLabels langLabels = LangLabels.newLabel(Locale.US.getLanguage(), defaultLabel);
        langLabels.addLabel(Locale.KOREA.getLanguage(), "학생");

        return langLabels;
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
    }
}