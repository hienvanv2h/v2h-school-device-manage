package com.vanhuuhien99.school_device_management.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface ScheduleProjection {

    Long getScheduleId();

    String getDayOfWeek();

    LocalDate getScheduleDate();

    LocalTime getStartTime();

    LocalTime getEndTime();

    String getLocation();

    Long getAssignmentId();

    String getTeacherName();

    String getClassName();

    String getSubjectName();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
