package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.formmodel.TeacherAssignmentForm;
import com.vanhuuhien99.school_device_management.projection.TeacherAssignmentProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherAssignmentService {

    Page<TeacherAssignmentProjection> getFilteredTeacherAssignments(String keyword, String filter, Pageable pageable);

    Page<TeacherAssignmentProjection> getAllTeacherAssignments(Pageable pageable);

    Page<TeacherAssignmentProjection> searchAssignmentByTeacherNameContaining(String keyword, Pageable pageable);

    Page<TeacherAssignmentProjection> searchAssignmentByClassNameContaining(String keyword, Pageable pageable);

    Page<TeacherAssignmentProjection> searchAssignmentBySemesterContaining(String keyword, Pageable pageable);

    TeacherAssignmentProjection getTeacherAssignmentById(Long assignmentId);

    void createNewTeacherAssignment(TeacherAssignmentForm form);

    void updateTeacherAssignment(TeacherAssignmentForm form, Long assignmentId);

    void deleteTeacherAssignment(Long assignmentId);
}
