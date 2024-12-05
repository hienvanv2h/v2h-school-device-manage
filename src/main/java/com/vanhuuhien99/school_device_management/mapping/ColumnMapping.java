package com.vanhuuhien99.school_device_management.mapping;

import com.vanhuuhien99.school_device_management.entity.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class ColumnMapping {

    public static final Map<Class<?>, Map<String, String>> COLUMN_TRANSLATIONS = Map.of(
            Subject.class, new LinkedHashMap<>() {{
                put("subjectId", "#");
                put("subjectName", "Tên môn học");
                put("description", "Mô tả");
                put("createdAt", "Ngày tạo");
                put("updatedAt", "Ngày cập nhật");
            }},
            SchoolClass.class, new LinkedHashMap<>() {{
                put("classId", "#");
                put("className", "Tên lớp");
                put("createdAt", "Ngày tạo");
                put("updatedAt", "Ngày cập nhật");
            }},
            Teacher.class, new LinkedHashMap<>() {{
                put("teacherId", "#");
                put("fullName", "Ho và tên");
                put("email", "Email");
                put("phoneNumber", "Số điện thoại");
                put("dateOfBirth", "Ngày sinh");
                put("gender", "Giới tính");
                put("createdAt", "Ngày tạo");
                put("updatedAt", "Ngày cập nhật");
            }},
            DeviceCategory.class, new LinkedHashMap<>() {{
                put("categoryId", "#");
                put("categoryName", "Tên loại thiết bị");
                put("description", "Mô tả");
                put("unit", "Đơn vị tính");
                put("unitPrice", "Đơn giá");
                put("createdAt", "Ngày tạo");
                put("updatedAt", "Ngày cập nhật");
            }},
            Device.class, new LinkedHashMap<>() {{
                put("deviceId", "#");
                put("deviceName", "Tên thiết bị");
                put("categoryId", "Loại thiết bị");
                put("description", "Mô tả");
                put("status", "Tình trạng TB");
                put("createdAt", "Ngày tạo");
                put("updatedAt", "Ngày cập nhật");
            }}
    );

    public static Map<String, String> getColumnTranslationMapping(Class<?> entityClass) {
        return COLUMN_TRANSLATIONS.getOrDefault(entityClass, Map.of());
    }
}
