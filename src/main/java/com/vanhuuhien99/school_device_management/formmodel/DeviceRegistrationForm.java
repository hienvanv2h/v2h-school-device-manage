package com.vanhuuhien99.school_device_management.formmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
public class DeviceRegistrationForm {

    @NotNull(message = "Device ID is required")
    private Long deviceId;

    @NotNull(message = "Teacher assignment ID is required")
    private Long teacherAssignmentId;

    @NotNull(message = "Schedule date is required")
    private LocalDate scheduleDate;

    private LocalDate returnDate;

    @NotBlank(message = "Registration status is required")
    @Size(max = 50, message = "Registration status must not exceed 50 characters")
    private String registrationStatus;

    @Size(max = 50, message = "Approval status must not exceed 50 characters")
    private String approvalStatus;

    private String description;
}
