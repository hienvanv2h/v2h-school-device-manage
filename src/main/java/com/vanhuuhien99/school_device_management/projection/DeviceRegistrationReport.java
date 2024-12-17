package com.vanhuuhien99.school_device_management.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceRegistrationReport {

    private LocalDateTime registrationDate;
    private String deviceName;
    private String teacherName;
    private String className;
    private String scheduleTime;
    private LocalDate returnDate;
    private String description;
}
