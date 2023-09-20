package org.example.shared;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JsonUtil {
    private JsonUtil() {
    }

    public static String toJson(Object target) {
        String result = "";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            ObjectWriter writer = mapper.writer().withoutAttribute("logger").withoutAttribute("typeName");
            return writer.writeValueAsString(target);
        } catch (JsonProcessingException var4) {
            var4.printStackTrace();
            return result;
        }
    }

    public static ObjectNode toJsonNode(String json) {
        ObjectNode result = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            result = (ObjectNode)mapper.readTree(json);
        } catch (JsonProcessingException var4) {
            var4.printStackTrace();
        }

        return result;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        T result = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        try {
            result = mapper.readValue(json, clazz);
        } catch (JsonProcessingException var5) {
            var5.printStackTrace();
        }

        return result;
    }

    public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
        List<T> results = new ArrayList();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);

        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            results = (List)mapper.readValue(json, type);
        } catch (JsonProcessingException var5) {
            var5.printStackTrace();
        }

        return (List)results;
    }

    public static <T> Set<T> fromJsonSet(String json, Class<T> clazz) {
        Set<T> results = new HashSet();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);

        try {
            JavaType type = mapper.getTypeFactory().constructCollectionType(Set.class, clazz);
            results = (Set)mapper.readValue(json, type);
        } catch (JsonProcessingException var5) {
            var5.printStackTrace();
        }

        return (Set)results;
    }

    public abstract class IgnoreMixIn {
        public IgnoreMixIn() {
        }

        @JsonIgnore
        abstract String getTypeName();
    }
}
