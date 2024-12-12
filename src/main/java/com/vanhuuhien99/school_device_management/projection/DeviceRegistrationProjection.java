package com.vanhuuhien99.school_device_management.projection;

import java.time.LocalDateTime;

public interface DeviceRegistrationProjection {

    Long getRegistrationId();

    Long getTeacherAssignmentId();

    Long getDeviceId();

    String getDeviceName();

    String getTeacherName();

    String getRegistrationStatus();

    String getApprovalStatus();

    String getDescription();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();

    default String getInfo() {
        return "DeviceRegistration [registrationId=" + getRegistrationId() + ", device=" + getDeviceId() + ", teacherAssignment=" + getTeacherAssignmentId() + "]"; // Add relevant fields
    }
}
