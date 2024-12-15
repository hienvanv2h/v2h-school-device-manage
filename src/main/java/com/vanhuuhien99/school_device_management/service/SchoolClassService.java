package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.entity.SchoolClass;
import com.vanhuuhien99.school_device_management.formmodel.SchoolClassForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SchoolClassService {

    Page<SchoolClass> getFilteredSchoolClasses(String keyword, String filter, Pageable pageable);

    Page<SchoolClass> getAllSchoolClasses(Pageable pageable);

    <T> List<T> getAll(Class<T> classType);

    SchoolClass getSchoolClassById(Long classId);

    Page<SchoolClass> searchByClassNameContaining(String keyword, Pageable pageable);

    void createNewSchoolClass(SchoolClassForm form);

    void updateSchoolClass(SchoolClassForm form, Long classId);

    void deleteSchoolClass(Long classId);
}
