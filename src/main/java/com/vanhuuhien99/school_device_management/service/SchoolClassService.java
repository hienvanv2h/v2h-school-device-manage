package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.entity.SchoolClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchoolClassService {

    Page<SchoolClass> getAllSchoolClasses(Pageable pageable);

    Page<SchoolClass> searchByClassName(String keyword, Pageable pageable);
}
