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
import java.util.List;

@Getter
@Setter
public class NameValueList implements JsonSerializable {
    //
    private List<NameValue> nameValues;

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
        this.nameValues.add(new NameValue(name, value));
    }

    public NameValueList(NameValueList nameValues) {
        //
        this();
        this.nameValues.addAll(nameValues.list());
    }

    public static NameValueList newInstance(String name, String value) {
        //
        return new NameValueList(name, value);
    }

    public static NameValueList newEmptyInstance() {
        //
        return new NameValueList();
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
        this.nameValues.add(new NameValue(name, value));
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
