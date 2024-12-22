package com.vanhuuhien99.school_device_management.projection;

import com.vanhuuhien99.school_device_management.entity.TeacherAssignment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class TeacherAssignmentDTO {

    private Long assignmentId;
    private Long teacherId;
    private String teacherName;
    private Long classId;
    private String className;
    private Long subjectId;
    private String subjectName;
    private String semester;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static TeacherAssignmentDTO fromTeacherAssignment(TeacherAssignment teacherAssignment) {
        return TeacherAssignmentDTO.builder()
                .assignmentId(teacherAssignment.getAssignmentId())
                .teacherId(teacherAssignment.getTeacher().getTeacherId())
                .teacherName(teacherAssignment.getTeacher().getFullName())
                .classId(teacherAssignment.getSchoolClass().getClassId())
                .className(teacherAssignment.getSchoolClass().getClassName())
                .subjectId(teacherAssignment.getSubject().getSubjectId())
                .subjectName(teacherAssignment.getSubject().getSubjectName())
                .semester(teacherAssignment.getSemester())
                .description(teacherAssignment.getDescription())
                .createdAt(teacherAssignment.getCreatedAt())
                .updatedAt(teacherAssignment.getUpdatedAt())
                .build();
    }
}
