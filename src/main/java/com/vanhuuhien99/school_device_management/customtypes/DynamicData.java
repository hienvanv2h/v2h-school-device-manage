package com.vanhuuhien99.school_device_management.customtypes;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class DynamicData implements Serializable {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private JsonNode data;

    // Constructor nhận JsonNode
    public DynamicData(JsonNode data) {
        this.data = data;
    }

    // Constructor nhận List (Array)
    public DynamicData(List<?> list) {
        this.data = objectMapper.valueToTree(list);
    }

    // Constructor nhận Map<String, Object>
    public DynamicData(Map<String, Object> map) {
        this.data = objectMapper.valueToTree(map);
    }

    public boolean isArray() {
        return data != null && data.isArray();
    }

    public boolean isObject() {
        return data != null && data.isObject();
    }

    // Getter khi data là Object
    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        if (isObject()) {
            Map<String, Object> properties = new HashMap<>();
            data.fields().forEachRemaining(entry ->
                    properties.put(entry.getKey(), this.toJavaObject(entry.getValue())));
            return properties;
        }
        return new HashMap<>();
    }

    // Lấy data dưới dạng List khi là Array
    public List<Object> getAsList() {
        if (isArray()) {
            List<Object> list = new ArrayList<>();
            data.elements().forEachRemaining(item ->
                    list.add(toJavaObject(item)));
            return list;
        }
        return new ArrayList<>();
    }

    public JsonNode getJsonNode() {
        return data;
    }

    // Setter cho Object
    @JsonAnySetter
    public void add(String key, Object value) {
        if (data == null || !data.isObject()) {
            data = objectMapper.createObjectNode();
        }
        ((ObjectNode) data).set(key, objectMapper.valueToTree(value));
    }

    // Setter for ArrayList
    public void add(Object value) {
        if (data == null || !data.isArray()) {
            data = objectMapper.createArrayNode();
        }
        ((ArrayNode) data).add(objectMapper.valueToTree(value));
    }

    // Lấy giá trị từ Object theo key
    public Object get(String key) {
        if (isObject() && data.has(key)) {
            return toJavaObject(data.get(key));
        }
        return null;
    }

    // Lấy phần tử từ Array theo index
    public Object get(int index) {
        if (isArray() && index >= 0 && index < data.size()) {
            return toJavaObject(data.get(index));
        }
        return null;
    }

    // Kiểm tra tô tại Object key
    public boolean has(String key) {
        return isObject() && data.has(key);
    }

    // Xóa thuộc tính trong Object theo key
    public void remove(String key) {
        if(isObject() && data.has(key)) {
            ((ObjectNode) data).remove(key);
        }
    }

    // Xóa phần tử trong Array theo index
    public void remove(int index) {
        if(isArray() && index >= 0 && index < data.size()) {
            ((ArrayNode) data).remove(index);
        }
    }

    // Chuyển JsonNode thành Java Object phù hợp
    private Object toJavaObject(JsonNode node) {
        try {
            if (node.isNull()) {
                return null;
            } else if (node.isTextual()) {
                return node.asText();
            } else if (node.isInt()) {
                return node.asInt();
            } else if (node.isLong()) {
                return node.asLong();
            } else if (node.isDouble()) {
                return node.asDouble();
            } else if (node.isBoolean()) {
                return node.asBoolean();
            } else if (node.isArray() || node.isObject()) {
                return objectMapper.treeToValue(node, Object.class);
            } else {
                return node.toString();
            }
        } catch (Exception e) {
            return node.toString();
        }
    }
}
