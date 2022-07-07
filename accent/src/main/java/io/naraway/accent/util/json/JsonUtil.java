/*
 COPYRIGHT (c) NEXTREE Inc. 2014
 This software is the proprietary of NEXTREE Inc.
 @since 2014. 6. 10.
 */

package io.naraway.accent.util.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonUtil {
    //
    private JsonUtil() {
        //
    }

    public static String toJson(Object target) {
        //
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

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        //
        List<T> results = new ArrayList<>();

        if (json == null || json.isEmpty()) {
            return results;
        }

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
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        return mapper;
    }
}
