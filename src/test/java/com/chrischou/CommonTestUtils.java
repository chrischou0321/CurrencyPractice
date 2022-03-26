package com.chrischou;

import com.chrischou.common.model.GlobalResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonTestUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> GlobalResponse<T> generateResponse(String jsonStr, Class<T> clazz) {
        try {
            JavaType type = objectMapper.getTypeFactory().constructParametricType(GlobalResponse.class, clazz);
//            return objectMapper.readValue(jsonStr, new TypeReference<GlobalResponse<T>>(){});
            return objectMapper.readValue(jsonStr, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> String toJson(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
