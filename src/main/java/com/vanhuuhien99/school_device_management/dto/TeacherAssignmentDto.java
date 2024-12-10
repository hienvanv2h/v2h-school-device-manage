package com.vanhuuhien99.school_device_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TeacherAssignmentDto {

    private Long assignmentId;
    private String teacherName;
    private String className;
    private String subjectName;
    private String semester;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
