package com.vanhuuhien99.school_device_management.mapping;

import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationReport;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReportColumnMapping {

    private static final Map<Class<?>, Map<String, String>> REPORT_COLUMN_TRANSLATIONS = Map.of(
            DeviceRegistrationReport.class, new LinkedHashMap<>() {{
                put("registrationDate", "Ngày đăng ký");
                put("deviceName", "Tên TBGD hoặc hóa chất được sử dụng");
                put("teacherName", "Giáo viên");
                put("className", "Khối lớp");
                put("scheduleTime", "Ca/tiết mượn");
                put("returnDate", "Ngày trả");
                put("description", "Ghi chú");
            }}
    );

    public static Map<String, String> getColumnTranslationMapping(Class<?> classType) {
        return REPORT_COLUMN_TRANSLATIONS.getOrDefault(classType, Map.of());
    }
}
