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
public class StringList implements JsonSerializable {
    //
    private List<String> elements;

    public StringList() {
        //
        this.elements = new ArrayList<>();
    }

    public StringList(int capacity) {
        //
        this.elements = new ArrayList<>(capacity);
    }

    public static StringList newInstance(String element) {
        //
        StringList stringList = new StringList();
        stringList.getElements().add(element);

        return stringList;
    }

    public static StringList newInstance(String... elements) {
        //
        StringList stringList = new StringList(elements.length);
        for (String element : elements) {
            stringList.getElements().add(element);
        }

        return stringList;
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static StringList fromJson(String json) {
        //
        return JsonUtil.fromJson(json, StringList.class);
    }

    public StringList add(String element) {
        //
        this.elements.add(element);

        return this;
    }

    public boolean contains(String element) {
        //
        return elements.contains(element);
    }

    public boolean isEmpty() {
        //
        return elements.isEmpty();
    }

    public int size() {
        //
        return elements.size();
    }

    public String get(int index) {
        //
        return elements.get(index);
    }

    public static StringList sample() {
        //
        return newInstance("Morning", "Afternoon", "Evening");
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toPrettyJson());
        System.out.println(sample().isEmpty());
    }
}