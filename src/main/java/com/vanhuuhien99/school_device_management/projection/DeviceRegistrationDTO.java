package com.vanhuuhien99.school_device_management.projection;

import com.vanhuuhien99.school_device_management.entity.DeviceRegistration;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class DeviceRegistrationDTO {

    private Long registrationId;
    private Long scheduleId;
    private String teacherName;
    private Long deviceId;
    private String deviceName;
    private String registrationStatus;
    private String approvalStatus;
    private LocalDate scheduleDate;
    private LocalDate returnDate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static DeviceRegistrationDTO fromDeviceRegistration(DeviceRegistration deviceRegistration) {
        var scheduleId = deviceRegistration.getSchedule() != null ? deviceRegistration.getSchedule().getScheduleId() : null;
        var teacherName = deviceRegistration.getSchedule() != null ? deviceRegistration.getSchedule().getTeacherAssignment().getTeacher().getFullName() : null;
        var deviceId = deviceRegistration.getDevice() != null ? deviceRegistration.getDevice().getDeviceId() : null;
        var deviceName = deviceRegistration.getDevice() != null ? deviceRegistration.getDevice().getDeviceName() : null;
        var scheduleDate = deviceRegistration.getSchedule() != null ? deviceRegistration.getSchedule().getScheduleDate() : null;
        return DeviceRegistrationDTO.builder()
                .registrationId(deviceRegistration.getRegistrationId())
                .scheduleId(scheduleId)
                .teacherName(teacherName)
                .deviceId(deviceId)
                .deviceName(deviceName)
                .registrationStatus(deviceRegistration.getRegistrationStatus())
                .approvalStatus(deviceRegistration.getApprovalStatus())
                .scheduleDate(scheduleDate)
                .returnDate(deviceRegistration.getReturnDate())
                .description(deviceRegistration.getDescription())
                .createdAt(deviceRegistration.getCreatedAt())
                .updatedAt(deviceRegistration.getUpdatedAt())
                .build();
    }
}
