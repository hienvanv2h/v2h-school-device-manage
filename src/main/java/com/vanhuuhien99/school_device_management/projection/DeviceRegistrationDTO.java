package com.vanhuuhien99.school_device_management.projection;

import com.vanhuuhien99.school_device_management.entity.DeviceRegistration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class DeviceRegistrationDTO {

    private Long registrationId;
    private Long teacherAssignmentId;
    private String teacherName;
    private Long deviceId;
    private String deviceName;
    private String registrationStatus;
    private String approvalStatus;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static DeviceRegistrationDTO fromDeviceRegistration(DeviceRegistration deviceRegistration) {
        return DeviceRegistrationDTO.builder()
                .registrationId(deviceRegistration.getRegistrationId())
                .teacherAssignmentId(deviceRegistration.getTeacherAssignment().getAssignmentId())
                .teacherName(deviceRegistration.getTeacherAssignment().getTeacher().getFullName())
                .deviceId(deviceRegistration.getDevice().getDeviceId())
                .deviceName(deviceRegistration.getDevice().getDeviceName())
                .registrationStatus(deviceRegistration.getRegistrationStatus())
                .approvalStatus(deviceRegistration.getApprovalStatus())
                .description(deviceRegistration.getDescription())
                .createdAt(deviceRegistration.getCreatedAt())
                .updatedAt(deviceRegistration.getUpdatedAt())
                .build();
    }
}
