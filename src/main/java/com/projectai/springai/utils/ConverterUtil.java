package com.projectai.springai.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterUtil {

    public static <T> List<T> convertToList(String json, Class<T> clazz) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.stream(json.split(","))
                    .map(s -> {
                        try {
                            return mapper.readValue(s, clazz);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}