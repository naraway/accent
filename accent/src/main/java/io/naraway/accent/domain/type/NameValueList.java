/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.domain.type;

import io.naraway.accent.util.json.JsonSerializable;
import io.naraway.accent.util.json.JsonUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class NameValueList implements JsonSerializable {
    //
    private List<NameValue> nameValues;

    public NameValueList(int capacity) {
        //
        this.nameValues = new ArrayList<>(capacity);
    }

    public NameValueList() {
        //
        this.nameValues = new ArrayList<>();
    }

    public NameValueList(NameValue nameValue) {
        //
        this();
        this.nameValues.add(nameValue);
    }

    public NameValueList(String name, String value) {
        //
        this();
        this.nameValues.add(NameValue.of(name, value));
    }

    public NameValueList(String name, Object value) {
        //
        this();
        this.nameValues.add(NameValue.of(name, value));
    }

    private NameValueList(List<NameValue> nameValues) {
        //
        this();
        this.nameValues.addAll(nameValues);
    }

    private NameValueList(NameValueList nameValues) {
        //
        this();
        this.nameValues.addAll(nameValues.list());
    }

    public static NameValueList newInstance() {
        //
        return new NameValueList();
    }

    public static NameValueList of(String... nameValues) {
        //
        if (nameValues.length == 0 || nameValues.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid name-value pairs");
        }

        NameValueList instance = new NameValueList();

        for (int i = 0; i < nameValues.length / 2; i++) {
            instance.add(nameValues[i * 2], nameValues[i * 2 + 1]);
        }

        return instance;
    }

    public static NameValueList of(Object... nameValues) {
        //
        if (nameValues.length == 0 || nameValues.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid name-value pairs");
        }

        NameValueList instance = new NameValueList();

        for (int i = 0; i < nameValues.length / 2; i++) {
            instance.add(nameValues[i * 2].toString(), nameValues[i * 2 + 1]);
        }

        return instance;
    }

    public static NameValueList of(NameValue... nameValues) {
        //
        if (nameValues.length == 0) {
            throw new IllegalArgumentException("Empty nameValues");
        }

        NameValueList instance = new NameValueList();

        for (NameValue nameValue : nameValues) {
            instance.add(nameValue);
        }

        return instance;
    }

    public static NameValueList from(List<NameValue> nameValues) {
        //
        return new NameValueList(nameValues);
    }

    public static NameValueList from(NameValueList nameValues) {
        //
        return new NameValueList(nameValues);
    }

    public static NameValueList filter(NameValueList nameValues, String... names) {
        //
        List<NameValue> filteredNameValues = nameValues.list().stream()
                .filter(nameValue -> Arrays.asList(names).contains(nameValue.getName()))
                .collect(Collectors.toList());

        return from(filteredNameValues);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static NameValueList fromJson(String json) {
        //
        return JsonUtil.fromJson(json, NameValueList.class);
    }

    public NameValueList add(NameValue nameValue) {
        //
        this.nameValues.add(nameValue);
        return this;
    }

    public NameValueList addAll(NameValueList nameValues) {
        //
        this.nameValues.addAll(nameValues.list());
        return this;
    }

    public NameValueList add(String name, String value) {
        //
        this.nameValues.add(NameValue.of(name, value));
        return this;
    }

    public NameValueList add(String name, Object value) {
        //
        this.nameValues.add(NameValue.of(name, value));
        return this;
    }

    public NameValueList remove(String name) {
        //
        NameValue targetNameValue = null;
        for (NameValue nameValue : nameValues) {
            if (nameValue.getName().equals(name)) {
                targetNameValue = nameValue;
                break;
            }
        }


        if (targetNameValue != null) {
            nameValues.remove(targetNameValue);
        }

        return this;
    }

    public String getValueOf(String name) {
        //
        return getNameValue(name).getValue();
    }

    public NameValue getNameValue(String name) {
        //
        return this.nameValues
                .stream()
                .filter(nameValue -> name.equals(nameValue.getName()))
                .findFirst()
                .orElse(null);
    }

    public void addAll(List<NameValue> nameValues) {
        //
        this.nameValues.addAll(nameValues);
    }

    public List<NameValue> list() {
        //
        return nameValues;
    }

    public boolean containsName(String name) {
        //
        return nameValues
                .stream()
                .anyMatch(nv -> nv.getName().equals(name));
    }

    public boolean isEmpty() {
        //
        return nameValues.isEmpty();
    }

    public int size() {
        //
        return nameValues.size();
    }

    public static NameValueList sample() {
        //
        return new NameValueList("name", "Cheolsoo Kim");
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
    }
}