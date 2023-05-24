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
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Getter
@Setter
public class IdNameList implements JsonSerializable {
    //
    private List<IdName> idNames;

    public IdNameList(int capacity) {
        //
        this.idNames = new ArrayList<>(capacity);
    }

    public IdNameList() {
        //
        this.idNames = new ArrayList<>();
    }

    public IdNameList(IdName idName) {
        //
        this();
        this.idNames.add(idName);
    }

    public IdNameList(String id, String name) {
        //
        this();
        this.idNames.add(new IdName(id, name));
    }

    private IdNameList(List<IdName> idNames) {
        //
        this();
        this.idNames.addAll(idNames);
    }

    private IdNameList(IdNameList idNames) {
        //
        this();
        this.idNames.addAll(idNames.list());
    }

    public static IdNameList newInstance() {
        //
        return new IdNameList();
    }

    public static IdNameList of(String... idNames) {
        //
        if (idNames.length == 0 || idNames.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid id-name pairs");
        }

        IdNameList instance = new IdNameList();

        for (int i = 0; i < idNames.length / 2; i++) {
            instance.add(idNames[i * 2], idNames[i * 2 + 1]);
        }

        return instance;
    }

    public static IdNameList of(IdName... idNames) {
        //
        if (idNames.length == 0) {
            throw new IllegalArgumentException("Empty idNames");
        }

        IdNameList instance = new IdNameList();

        for (IdName idName : idNames) {
            instance.add(idName);
        }

        return instance;
    }

    public static IdNameList from(List<IdName> idNames) {
        //
        return new IdNameList(idNames);
    }

    public static IdNameList from(IdNameList idNames) {
        //
        return new IdNameList(idNames);
    }

    public static IdNameList filter(IdNameList idNames, String... ids) {
        //
        List<IdName> filteredIdNames = idNames.list().stream()
                .filter(idName -> Arrays.asList(ids).contains(idName.getId()))
                .collect(Collectors.toList());

        return from(filteredIdNames);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static IdNameList fromJson(String json) {
        //
        return JsonUtil.fromJson(json, IdNameList.class);
    }

    public IdNameList add(IdName idName) {
        //
        idNames.add(idName);

        return this;
    }

    public IdNameList add(String id, String name) {
        //
        idNames.add(new IdName(id, name));

        return this;
    }

    public void addAll(List<IdName> idNames) {
        //
        this.idNames.addAll(idNames);
    }

    public List<IdName> list() {
        //
        return idNames;
    }

    public void removeById(String id) {
        //
        for (IdName idName : getById(id)) {
            idNames.remove(idName);
        }
    }

    public List<IdName> getById(String id) {
        //
        List<IdName> foundIdNames = new ArrayList<>();
        for (IdName idName : idNames) {
            if (idName.getId().equals(id)) {
                foundIdNames.add(idName);
            }
        }

        return foundIdNames;
    }

    public IdName getByNameFirst(String name) {
        //
        for (IdName idName : idNames) {
            if (idName.getName().equals(name)) {
                return idName;
            }
        }

        throw new NoSuchElementException("Name: " + name);
    }

    public boolean containsId(String id) {
        //
        for (IdName idName : this.idNames) {
            if (id.equals(idName.getId())) {
                return true;
            }
        }

        return false;
    }

    public boolean containsName(String name) {
        //
        for (IdName idName : this.idNames) {
            if (name.equals(idName.getName())) {
                return true;
            }
        }

        return false;
    }

    public boolean isEmpty() {
        //
        return idNames.isEmpty();
    }

    public int size() {
        //
        return idNames.size();
    }

    public static IdNameList sample() {
        //
        return new IdNameList(IdName.sample());
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
    }
}