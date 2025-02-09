package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.entity.Teacher;
import com.vanhuuhien99.school_device_management.formmodel.TeacherForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {

    Page<Teacher> getFilteredTeachers(String keyword, String filter, Pageable pageable);

    Page<Teacher> getAllTeachers(Pageable pageable);

    <T> List<T> getAll(Class<T> classType);

    Page<Teacher> searchByFullNameContaining(String keyword, Pageable pageable);

    Page<Teacher> searchByEmailContaining(String keyword, Pageable pageable);

    Page<Teacher> searchByPhoneNumberContaining(String keyword, Pageable pageable);

    Teacher getTeacherById(Long teacherId);

    void createNewTeacher(TeacherForm form);

    void updateTeacher(TeacherForm form, Long teacherId);

    void deleteTeacher(Long teacherId);
}
