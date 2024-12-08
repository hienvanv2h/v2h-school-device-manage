package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.dto.TeacherAssignmentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherAssignmentService {

    Page<TeacherAssignmentDto> getAllTeacherAssignments(Pageable pageable);

    Page<TeacherAssignmentDto> searchAssignmentByTeacherNameContaining(String keyword, Pageable pageable);

    Page<TeacherAssignmentDto> searchAssignmentByClassNameContaining(String keyword, Pageable pageable);

    Page<TeacherAssignmentDto> searchAssignmentBySemesterContaining(String keyword, Pageable pageable);

    TeacherAssignmentDto getTeacherAssignmentById(Long assignmentId);

//    void createNewTeacherAssignment(TeacherAssignmentDto teacherAssignmentDto);
//
//    void updateTeacherAssignment(TeacherAssignmentDto teacherAssignmentDto, Long assignmentId);
//
//    void deleteTeacherAssignment(Long assignmentId);
}
