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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Getter
@Setter
public class IdNameList implements JsonSerializable, Serializable {
    //
    @SuppressWarnings("java:S1948")
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

    public IdNameList(List<IdName> idNames) {
        //
        this.idNames = idNames;
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
