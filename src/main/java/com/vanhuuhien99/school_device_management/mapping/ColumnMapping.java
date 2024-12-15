package com.vanhuuhien99.school_device_management.mapping;

import com.vanhuuhien99.school_device_management.entity.*;
import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationProjection;
import com.vanhuuhien99.school_device_management.projection.ScheduleProjection;
import com.vanhuuhien99.school_device_management.projection.TeacherAssignmentProjection;

import java.util.LinkedHashMap;
import java.util.Map;

public class ColumnMapping {

    public static final Map<Class<?>, Map<String, String>> COLUMN_TRANSLATIONS = Map.of(
            Subject.class, new LinkedHashMap<>() {{
                put("subjectId", "#");
                put("subjectName", "Tên môn học");
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
                put("unitPrice", "Đơn giá nhập (VND)");
                put("createdAt", "Ngày tạo");
                put("updatedAt", "Ngày cập nhật");
            }},
            DeviceCategorySubject.class, new LinkedHashMap<>() {{
                put("id", "#");
                put("deviceCategory.categoryName", "Loại thiết bị");      // DeviceCategorySubject.deviceCategory.categoryName
                put("subject.subjectName", "Môn học");                   // DeviceCategorySubject.subject.subjectName
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
            }},
            TeacherAssignmentProjection.class, new LinkedHashMap<>() {{
                put("assignmentId", "#");
                put("teacher.fullName", "Tên giáo viên");       // TeacherAssignment.teacher.fullName
                put("schoolClass.className", "Tên lớp");         // TeacherAssignment.schoolClass.className
                put("subject.subjectName", "Tên môn học");       // TeacherAssignment.subject.subjectName
                put("semester", "Học kỳ");
                put("description", "Mô tả");
                put("createdAt", "Ngày tạo");
                put("updatedAt", "Ngày cập nhật");
            }},
            ScheduleProjection.class, new LinkedHashMap<>() {{
                put("scheduleId", "#");
                put("dayOfWeek", "Ngày trong tuần");
                put("scheduleDate", "Ngày học");
                put("startTime", "Bắt đầu vào");
                put("endTime", "Kết thúc vào");
                put("location", "Địa điểm");
                put("teacherAssignment.teacher.fullName", "Giáo viên");     // Schedule.teacherAssignment.teacher.fullName
                put("teacherAssignment.schoolClass.className", "Lớp");      // Schedule.teacherAssignment.schoolClass.className
                put("teacherAssignment.subject.subjectName", "Môn học");     // Schedule.teacherAssignment.subject.subjectName
                put("createdAt", "Ngày tạo");
                put("updatedAt", "Ngày cập nhật");
            }},
            DeviceRegistrationProjection.class, new LinkedHashMap<>() {{
                put("registrationId", "#");
                put("device.deviceName", "Tên thiết bị");       // DeviceRegistration.device.deviceName
                put("teacherAssignment.teacher.fullName", "Giáo viên");     // DeviceRegistration.teacherAssignment.teacher.fullName
                put("description", "Ghi chú");
                put("registrationStatus", "Trạng thái");
                put("approvalStatus", "Trạng thái phê duyệt");
                put("createdAt", "Ngày tạo");
                put("updatedAt", "Ngày cập nhật");
            }}
    );

    public static Map<String, String> getColumnTranslationMapping(Class<?> classType) {
        return COLUMN_TRANSLATIONS.getOrDefault(classType, Map.of());
    }
}
