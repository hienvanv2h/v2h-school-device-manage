package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.entity.SchoolClass;
import com.vanhuuhien99.school_device_management.formmodel.SchoolClassForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchoolClassService {

    Page<SchoolClass> getAllSchoolClasses(Pageable pageable);

    SchoolClass getSchoolClassById(Long classId);

    Page<SchoolClass> searchByClassNameContaining(String keyword, Pageable pageable);

    void createNewSchoolClass(SchoolClassForm form);

    void updateSchoolClass(SchoolClassForm form, Long classId);

    void deleteSchoolClass(Long classId);
}
