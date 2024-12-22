package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.formmodel.TeacherAssignmentForm;
import com.vanhuuhien99.school_device_management.projection.TeacherAssignmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherAssignmentService {

    Page<TeacherAssignmentDTO> searchByCriteria(String keyword, String filter, Pageable pageable);

    TeacherAssignmentDTO getTeacherAssignmentById(Long assignmentId);

    void createNewTeacherAssignment(TeacherAssignmentForm form);

    void updateTeacherAssignment(TeacherAssignmentForm form, Long assignmentId);

    void deleteTeacherAssignment(Long assignmentId);
}
