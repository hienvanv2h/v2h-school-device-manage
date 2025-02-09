package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.Teacher;
import com.vanhuuhien99.school_device_management.enums.Gender;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.TeacherForm;
import com.vanhuuhien99.school_device_management.repository.TeacherRepository;
import com.vanhuuhien99.school_device_management.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public Page<Teacher> getFilteredTeachers(String keyword, String filter, Pageable pageable) {
        if(keyword == null || keyword.isEmpty() || filter == null || filter.isEmpty()) {
            return getAllTeachers(pageable);
        } else {
            // Các giá trị khớp xem trong lớp ColumnMapping
            if(filter.equalsIgnoreCase("fullName")) {
                return searchByFullNameContaining(keyword, pageable);
            } else if(filter.equalsIgnoreCase("email")) {
                return searchByEmailContaining(keyword, pageable);
            } else if(filter.equalsIgnoreCase("phoneNumber")) {
                return searchByPhoneNumberContaining(keyword, pageable);
            } else {
                return getAllTeachers(pageable);
            }
        }
    }

    @Override
    public Page<Teacher> getAllTeachers(Pageable pageable) {
        return teacherRepository.findAll(pageable);
    }

    @Override
    public <T> List<T> getAll(Class<T> classType) {
        return teacherRepository.findBy(classType);
    }

    @Override
    public Page<Teacher> searchByFullNameContaining(String keyword, Pageable pageable) {
        return teacherRepository.findByFullNameContaining(keyword, pageable);
    }

    @Override
    public Page<Teacher> searchByEmailContaining(String keyword, Pageable pageable) {
        return teacherRepository.findByEmailContaining(keyword, pageable);
    }

    @Override
    public Page<Teacher> searchByPhoneNumberContaining(String keyword, Pageable pageable) {
        return teacherRepository.findByPhoneNumberContaining(keyword, pageable);
    }

    @Override
    public Teacher getTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find teacher with id: " + teacherId));
    }

    @Override
    @Transactional
    public void createNewTeacher(TeacherForm form) {
        if(teacherRepository.existsByPhoneNumber(form.getPhoneNumber())) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        var newTeacher = Teacher.builder()
                .fullName(form.getFullName())
                .email(form.getEmail())
                .phoneNumber(form.getPhoneNumber())
                .dateOfBirth(form.getDateOfBirth())
                .gender(Gender.fromValue(form.getGender()))
                .build();

        teacherRepository.save(newTeacher);
    }

    @Override
    @Transactional
    public void updateTeacher(TeacherForm form, Long teacherId) {
        var existingTeacher = getTeacherById(teacherId);
        existingTeacher.setFullName(form.getFullName());
        existingTeacher.setEmail(form.getEmail());
        existingTeacher.setPhoneNumber(form.getPhoneNumber());
        existingTeacher.setDateOfBirth(form.getDateOfBirth());
        existingTeacher.setGender(Gender.fromValue(form.getGender()));

        teacherRepository.save(existingTeacher);
    }

    @Override
    @Transactional
    public void deleteTeacher(Long teacherId) {
        var existingTeacher = getTeacherById(teacherId);
        teacherRepository.delete(existingTeacher);
    }
}
