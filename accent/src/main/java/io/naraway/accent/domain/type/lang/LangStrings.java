/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type.lang;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

@SuppressWarnings("UnusedReturnValue")
@Getter
@Setter
public class LangStrings implements JsonSerializable {
    //
    private String defaultLanguage;
    @SuppressWarnings("java:S1700")
    private List<LangString> langStrings;

    public LangStrings() {
        //
        this.langStrings = new ArrayList<>();
    }

    protected LangStrings(String langCode, String string) {
        //
        this();
        this.defaultLanguage = langCode;
        this.langStrings.add(LangString.newString(langCode, string));
    }

    public static LangStrings emptyString() {
        //
        return new LangStrings();
    }

    public static LangStrings newString(String landCode, String string) {
        //
        return new LangStrings(landCode, string);
    }

    public static LangStrings newString(String string) {
        //
        return new LangStrings(Locale.getDefault().getLanguage(), string);
    }

    public static LangStrings newString(LangString langString) {
        //
        return new LangStrings(langString.getLanguage(), langString.getString());
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static LangStrings fromJson(String json) {
        //
        return JsonUtil.fromJson(json, LangStrings.class);
    }

    public int size() {
        //
        return langStrings.size();
    }

    public List<LangString> list() {
        //
        return this.langStrings;
    }

    public String genString() {
        //
        return genString(defaultLanguage);
    }

    public String genString(String language) {
        //
        String string = null;
        for (LangString langString : langStrings) {
            if (langString.getLanguage().equals(language)) {
                string = langString.getString();
                break;
            }
        }
        if (string == null) {
            for (LangString langString : langStrings) {
                if (langString.getLanguage().equals(defaultLanguage)) {
                    string = langString.getString();
                    break;
                }
            }
        }

        if (string == null) {
            throw new NoSuchElementException("language: " + language);
        }

        return string;
    }

    public LangStrings addString(String langCode, String string) {
        //
        this.langStrings.add(LangString.newString(langCode, string));
        return this;
    }

    public LangStrings addString(Locale locale, String string) {
        //
        return addString(locale.getLanguage(), string);
    }

    public static LangStrings sample() {
        //
        String langCode = Locale.KOREA.getLanguage();
        LangStrings sample = new LangStrings(langCode, "넥스트리");
        sample.addString(Locale.US, "NEXTREE");

        return sample;
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
        System.out.println(sample().list());
        System.out.println(sample().getDefaultLanguage());
    }
}
