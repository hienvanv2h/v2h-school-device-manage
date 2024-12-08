package com.vanhuuhien99.school_device_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TeacherAssignmentDto {

    private Long assignmentId;
    private String teacherName;
    private String className;
    private String subjectName;
    private String semester;
    private String description;
}
