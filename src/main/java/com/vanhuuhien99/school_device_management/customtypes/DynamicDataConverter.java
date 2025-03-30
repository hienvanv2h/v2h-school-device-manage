package com.vanhuuhien99.school_device_management.customtypes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;

@Converter
public class DynamicDataConverter implements AttributeConverter<DynamicData, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(DynamicData attribute) {
        if(attribute == null || attribute.getJsonNode() == null || attribute.getJsonNode().isNull()) return null;
        try {
            return objectMapper.writeValueAsString(attribute.getJsonNode());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting DynamicData to JSON string", e);
        }
    }

    @Override
    public DynamicData convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new DynamicData();
        }

        try {
            JsonNode jsonNode = objectMapper.readTree(dbData);
            return new DynamicData(jsonNode);
        } catch (IOException e) {
            throw new RuntimeException("Error converting JSON string to DynamicData", e);
        }
    }
}
