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
public class CodeNameList implements JsonSerializable {
    //
    private List<CodeName> codeNames;

    public CodeNameList(int capacity) {
        //
        this.codeNames = new ArrayList<>(capacity);
    }

    public CodeNameList() {
        //
        this.codeNames = new ArrayList<>();
    }

    public CodeNameList(CodeName codeName) {
        //
        this();
        this.codeNames.add(codeName);
    }

    public CodeNameList(String code, String name) {
        //
        this();
        this.codeNames.add(new CodeName(code, name));
    }

    public CodeNameList(List<CodeName> codeNames) {
        //
        this.codeNames = codeNames;
    }

    public static CodeNameList newInstance(String code, String name) {
        //
        return new CodeNameList(code, name);
    }

    @Override
    public String toString() {
        //
        return toJson();
    }

    public static CodeNameList fromJson(String json) {
        //
        return JsonUtil.fromJson(json, CodeNameList.class);
    }

    public CodeNameList add(CodeName codeName) {
        //
        codeNames.add(codeName);

        return this;
    }

    public CodeNameList add(String code, String name) {
        //
        codeNames.add(new CodeName(code, name));

        return this;
    }

    public void addAll(List<CodeName> codeNames) {
        //
        this.codeNames.addAll(codeNames);
    }

    public List<CodeName> list() {
        //
        return codeNames;
    }

    public void removeByCode(String code) {
        //
        for (CodeName codeName : getByCode(code)) {
            codeNames.remove(codeName);
        }
    }

    public List<CodeName> getByCode(String id) {
        //
        List<CodeName> foundIdNames = new ArrayList<>();
        for (CodeName codeName : codeNames) {
            if (codeName.getCode().equals(id)) {
                foundIdNames.add(codeName);
            }
        }

        return foundIdNames;
    }

    public int size() {
        //
        return codeNames.size();
    }

    public static CodeNameList sample() {
        //
        return new CodeNameList(CodeName.sample());
    }

    public static void main(String[] args) {
        //
        System.out.println(sample());
    }
}
