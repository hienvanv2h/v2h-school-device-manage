package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.dto.TeacherAssignmentDto;
import com.vanhuuhien99.school_device_management.repository.TeacherAssignmentRepository;
import com.vanhuuhien99.school_device_management.service.TeacherAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherAssignmentServiceImpl implements TeacherAssignmentService {

    private final TeacherAssignmentRepository teacherAssignmentRepository;

    @Override
    public Page<TeacherAssignmentDto> getAllTeacherAssignments(Pageable pageable) {
        return null;
    }

    @Override
    public Page<TeacherAssignmentDto> searchAssignmentByTeacherNameContaining(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public Page<TeacherAssignmentDto> searchAssignmentByClassNameContaining(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public Page<TeacherAssignmentDto> searchAssignmentBySemesterContaining(String keyword, Pageable pageable) {
        return null;
    }

    @Override
    public TeacherAssignmentDto getTeacherAssignmentById(Long assignmentId) {
        return null;
    }
}
