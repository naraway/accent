/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonUtil {
    //
    public static String toJson(Object target) {
        //
        if (target == null) {
            return "";
        }

        String result = "";

        ObjectMapper mapper = JsonUtil.getObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            ObjectWriter writer = mapper.writer().withoutAttribute("logger");
            return writer.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String toPrettyJson(Object target) {
        //
        if (target == null) {
            return "";
        }

        String result = "";

        ObjectMapper mapper = JsonUtil.getObjectMapper();

        try {
            ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter().withoutAttribute("logger");
            return writer.writeValueAsString(target);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        //
        if (json == null || json.trim().length() == 0) {
            return null;
        }

        T result = null;

        ObjectMapper mapper = JsonUtil.getObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            result = mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        //
        if (json == null || json.trim().length() == 0) {
            return null;
        }

        T result = null;

        ObjectMapper mapper = JsonUtil.getObjectMapper();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            result = mapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        //
        if (json == null || json.trim().length() == 0) {
            return Collections.emptyList();
        }

        List<T> results = new ArrayList<>();

        ObjectMapper mapper = JsonUtil.getObjectMapper();

        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            results = mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return results;
    }

    public static <T> Set<T> fromJsonSet(String json, Class<T> clazz) {
        //
        if (json == null || json.trim().length() == 0) {
            return Collections.emptySet();
        }

        Set<T> results = new HashSet<>();

        ObjectMapper mapper = JsonUtil.getObjectMapper();

        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(Set.class, clazz);
            results = mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        return results;
    }

    @SuppressWarnings("java:S1874")
    private static ObjectMapper getObjectMapper() {
        //
        ObjectMapper mapper = JsonMapper.builder()
                .enable(MapperFeature.PROPAGATE_TRANSIENT_MARKER)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .build();
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }
}