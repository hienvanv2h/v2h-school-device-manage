package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.SchoolClass;
import com.vanhuuhien99.school_device_management.entity.Subject;
import com.vanhuuhien99.school_device_management.entity.Teacher;
import com.vanhuuhien99.school_device_management.entity.TeacherAssignment;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.TeacherAssignmentForm;
import com.vanhuuhien99.school_device_management.projection.TeacherAssignmentProjection;
import com.vanhuuhien99.school_device_management.repository.SchoolClassRepository;
import com.vanhuuhien99.school_device_management.repository.SubjectRepository;
import com.vanhuuhien99.school_device_management.repository.TeacherAssignmentRepository;
import com.vanhuuhien99.school_device_management.repository.TeacherRepository;
import com.vanhuuhien99.school_device_management.service.TeacherAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeacherAssignmentServiceImpl implements TeacherAssignmentService {

    private final TeacherAssignmentRepository teacherAssignmentRepository;

    private final TeacherRepository teacherRepository;

    private final SchoolClassRepository schoolClassRepository;

    private final SubjectRepository subjectRepository;

    @Override
    public Page<TeacherAssignmentProjection> getFilteredTeacherAssignments(String keyword, String filter, Pageable pageable) {
        if(keyword == null || keyword.isEmpty() || filter == null || filter.isEmpty()) {
            return getAllTeacherAssignments(pageable);
        } else {
            // Các giá trị khớp xem trong lớp ColumnMapping
            if(filter.equalsIgnoreCase("teacher.fullName")) {
                return searchAssignmentByTeacherNameContaining(keyword, pageable);
            } else if(filter.equalsIgnoreCase("schoolClass.className")) {
                return searchAssignmentByClassNameContaining(keyword, pageable);
            } else if(filter.equalsIgnoreCase("semester")) {
                return searchAssignmentBySemesterContaining(keyword, pageable);
            } else {
                return getAllTeacherAssignments(pageable);
            }
        }
    }

    @Override
    public Page<TeacherAssignmentProjection> getAllTeacherAssignments(Pageable pageable) {
        return teacherAssignmentRepository.findAllAssignments(pageable);
    }

    @Override
    public Page<TeacherAssignmentProjection> searchAssignmentByTeacherNameContaining(String keyword, Pageable pageable) {
        return teacherAssignmentRepository.findByTeacherFullNameContaining(keyword, pageable);
    }

    @Override
    public Page<TeacherAssignmentProjection> searchAssignmentByClassNameContaining(String keyword, Pageable pageable) {
        return teacherAssignmentRepository.findByClassNameContaining(keyword, pageable);
    }

    @Override
    public Page<TeacherAssignmentProjection> searchAssignmentBySemesterContaining(String keyword, Pageable pageable) {
        return teacherAssignmentRepository.findBySemesterContaining(keyword, pageable);
    }

    @Override
    public TeacherAssignmentProjection getTeacherAssignmentById(Long assignmentId) {
        return teacherAssignmentRepository.findTeacherAssignmentDetailsById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find teacher assignment with id: " + assignmentId));
    }

    @Override
    @Transactional
    public void createNewTeacherAssignment(TeacherAssignmentForm form) {
        var existingTeacher = getTeacherById(form.getTeacherId());
        var existingSchoolClass = getSchoolClassById(form.getClassId());
        var existingSubject = getSubjectById(form.getSubjectId());

        TeacherAssignment newTeacherAssignment = TeacherAssignment.builder()
                .teacher(existingTeacher)
                .schoolClass(existingSchoolClass)
                .subject(existingSubject)
                .semester(form.getSemester())
                .description(form.getDescription())
                .build();
        teacherAssignmentRepository.save(newTeacherAssignment);
    }

    @Override
    @Transactional
    public void updateTeacherAssignment(TeacherAssignmentForm form, Long assignmentId) {
        var existingTeacher = getTeacherById(form.getTeacherId());
        var existingSchoolClass = getSchoolClassById(form.getClassId());
        var existingSubject = getSubjectById(form.getSubjectId());
        TeacherAssignment existingTeacherAssignment = teacherAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find teacher assignment with id: " + assignmentId));

        existingTeacherAssignment.setTeacher(existingTeacher);
        existingTeacherAssignment.setSchoolClass(existingSchoolClass);
        existingTeacherAssignment.setSubject(existingSubject);
        existingTeacherAssignment.setSemester(form.getSemester());
        existingTeacherAssignment.setDescription(form.getDescription());
        teacherAssignmentRepository.save(existingTeacherAssignment);
    }

    @Override
    @Transactional
    public void deleteTeacherAssignment(Long assignmentId) {
        var existingTeacherAssignment = teacherAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find teacher assignment with id: " + assignmentId));
        teacherAssignmentRepository.delete(existingTeacherAssignment);
    }

    private Teacher getTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find teacher with id: " + teacherId));
    }

    private SchoolClass getSchoolClassById(Long classId) {
        return schoolClassRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find school class with id: " + classId));
    }

    private Subject getSubjectById(Long subjectId) {
        return subjectRepository.findById(subjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find subject with id: " + subjectId));
    }
}
