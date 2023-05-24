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

    private CodeNameList(List<CodeName> codeNames) {
        //
        this();
        this.codeNames.addAll(codeNames);
    }

    private CodeNameList(CodeNameList codeNames) {
        //
        this();
        this.codeNames.addAll(codeNames.list());
    }

    public static CodeNameList newInstance() {
        //
        return new CodeNameList();
    }

    public static CodeNameList of(String... codeNames) {
        //
        if (codeNames.length == 0 || codeNames.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid code-name pairs");
        }

        CodeNameList instance = new CodeNameList();

        for (int i = 0; i < codeNames.length / 2; i++) {
            instance.add(codeNames[i * 2], codeNames[i * 2 + 1]);
        }

        return instance;
    }

    public static CodeNameList of(CodeName... codeNames) {
        //
        if (codeNames.length == 0) {
            throw new IllegalArgumentException("Empty codeNames");
        }

        CodeNameList instance = new CodeNameList();

        for (CodeName codeName : codeNames) {
            instance.add(codeName);
        }

        return instance;
    }

    public static CodeNameList from(List<CodeName> codeNames) {
        //
        return new CodeNameList(codeNames);
    }

    public static CodeNameList from(CodeNameList codeNames) {
        //
        return new CodeNameList(codeNames);
    }

    public static CodeNameList filter(CodeNameList codeNames, String... codes) {
        //
        List<CodeName> filteredCodeNames = codeNames.list().stream()
                .filter(codeName -> Arrays.asList(codes).contains(codeName.getCode()))
                .collect(Collectors.toList());

        return from(filteredCodeNames);
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

    public boolean isEmpty() {
        //
        return codeNames.isEmpty();
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