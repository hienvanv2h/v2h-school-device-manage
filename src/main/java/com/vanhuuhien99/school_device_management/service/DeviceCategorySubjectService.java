package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.entity.DeviceCategorySubject;
import com.vanhuuhien99.school_device_management.entity.DeviceCategorySubjectId;
import com.vanhuuhien99.school_device_management.formmodel.DeviceCategorySubjectForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeviceCategorySubjectService {

    Page<DeviceCategorySubject> getFilteredDeviceCategorySubjects(String keyword, String filter, Pageable pageable);

    Page<DeviceCategorySubject> getAllDeviceCategorySubjects(Pageable pageable);

    Page<DeviceCategorySubject> searchByDeviceCategoryName(String categoryName, Pageable pageable);

    Page<DeviceCategorySubject> searchBySubjectName(String subjectName, Pageable pageable);

    void createNewDeviceCategorySubject(DeviceCategorySubjectForm form);

    void deleteDeviceCategorySubject(DeviceCategorySubjectId deviceCategorySubjectId);
}
