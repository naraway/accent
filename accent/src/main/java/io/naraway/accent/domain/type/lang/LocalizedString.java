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

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;

@SuppressWarnings("UnusedReturnValue")
@Getter
@Setter
public class LocalizedString implements JsonSerializable {
    //
    private String defaultLocale;
    @SuppressWarnings("java:S1700")
    private Map<String, String> localeStringMap;

    public LocalizedString() {
        //
        this.defaultLocale = Locale.getDefault().getLanguage();
        this.localeStringMap = new HashMap<>();
    }

    private LocalizedString(String locale, String string) {
        //
        this.defaultLocale = locale;
        this.localeStringMap = new HashMap<>();
        this.localeStringMap.put(locale, string);
    }

    public static LocalizedString emptyInstance() {
        //
        return new LocalizedString();
    }

    public static LocalizedString newInstance(LocaleString localeString) {
        //
        return new LocalizedString(localeString.getLocale(), localeString.getString());
    }

    public static LocalizedString newInstance(String locale, String string) {
        //
        return new LocalizedString(locale, string);
    }

    public static LocalizedString newInstance(Locale locale, String string) {
        //
        return newInstance(locale.getLanguage(), string);
    }

    public static LocalizedString newInstance(String string) {
        //
        return newInstance(Locale.getDefault(), string);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static LocalizedString fromJson(String json) {
        //
        return JsonUtil.fromJson(json, LocalizedString.class);
    }

    public int size() {
        //
        return localeStringMap.size();
    }

    public String get() {
        //
        return get(defaultLocale);
    }

    public String get(String locale) {
        //
        if (!localeStringMap.containsKey(locale)) {
            throw new NoSuchElementException("locale: " + locale);
        }

        return localeStringMap.get(locale);
    }

    public LocalizedString add(LocaleString localeString) {
        //
        this.localeStringMap.put(localeString.getLocale(), localeString.getString());
        return this;
    }

    public LocalizedString add(String locale, String string) {
        //
        return this.add(LocaleString.newInstance(locale, string));
    }

    public LocalizedString add(Locale locale, String string) {
        //
        return add(locale.getLanguage(), string);
    }

    public static LocalizedString sample() {
        //
        String locale = Locale.KOREA.getLanguage();
        LocalizedString sample = LocalizedString.newInstance(locale, "넥스트리");
        sample.add(Locale.US, "NEXTREE");

        return sample;
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
        System.out.println(sample().getDefaultLocale());
    }
}
