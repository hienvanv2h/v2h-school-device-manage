package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.SchoolClass;
import com.vanhuuhien99.school_device_management.entity.Subject;
import com.vanhuuhien99.school_device_management.entity.Teacher;
import com.vanhuuhien99.school_device_management.entity.TeacherAssignment;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.TeacherAssignmentForm;
import com.vanhuuhien99.school_device_management.projection.TeacherAssignmentDTO;
import com.vanhuuhien99.school_device_management.repository.SchoolClassRepository;
import com.vanhuuhien99.school_device_management.repository.SubjectRepository;
import com.vanhuuhien99.school_device_management.repository.TeacherAssignmentRepository;
import com.vanhuuhien99.school_device_management.repository.TeacherRepository;
import com.vanhuuhien99.school_device_management.service.TeacherAssignmentService;
import com.vanhuuhien99.school_device_management.specification.TeacherAssignmentSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TeacherAssignmentServiceImpl implements TeacherAssignmentService {

    private final TeacherAssignmentRepository teacherAssignmentRepository;

    private final TeacherRepository teacherRepository;

    private final SchoolClassRepository schoolClassRepository;

    private final SubjectRepository subjectRepository;

    @Override
    public Page<TeacherAssignmentDTO> searchByCriteria(String keyword, String filter, Pageable pageable) {
        Specification<TeacherAssignment> spec = Specification.where(null);
        if(StringUtils.hasText(filter) && StringUtils.hasText(keyword)) {
            if(filter.equalsIgnoreCase("teacher.fullName") || filter.equals("teacherName")) {
                spec = spec.and(TeacherAssignmentSpec.containsTeacherFullName(keyword));
            } else if(filter.equalsIgnoreCase("schoolClass.className")) {
                spec = spec.and(TeacherAssignmentSpec.containsSchoolClassName(keyword));
            } else if(filter.equalsIgnoreCase("semester")) {
                spec = spec.and(TeacherAssignmentSpec.containsSemesterName(keyword));
            }
        }

        var page = teacherAssignmentRepository.findAll(spec, pageable);
        return page.map(TeacherAssignmentDTO::fromTeacherAssignment);
    }

    @Override
    public TeacherAssignmentDTO getTeacherAssignmentById(Long assignmentId) {
        var teacherAssignment = teacherAssignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find teacher assignment with id: " + assignmentId));
        return TeacherAssignmentDTO.fromTeacherAssignment(teacherAssignment);
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
