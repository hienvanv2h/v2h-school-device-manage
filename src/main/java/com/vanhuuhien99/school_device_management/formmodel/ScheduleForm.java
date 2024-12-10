package com.vanhuuhien99.school_device_management.formmodel;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@ToString
public class ScheduleForm {

    @NotNull(message = "Assignment ID is required")
    private Long teacherAssignmentId;

    @Size(max = 10, message = "Day of week must not exceed 10 characters")
    private String dayOfWeek;

    @NotNull(message = "Schedule date is required")
    private LocalDate scheduleDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @Size(max = 100, message = "Location must not exceed 100 characters")
    private String location;
}
