package com.vanhuuhien99.school_device_management.projection;

import java.time.LocalDateTime;

public interface TeacherAssignmentProjection {

    Long getAssignmentId();

    Long getTeacherId();

    String getTeacherName();

    Long getClassId();

    String getClassName();

    Long getSubjectId();

    String getSubjectName();

    String getSemester();

    String getDescription();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
