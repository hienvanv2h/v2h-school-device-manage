package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubjectService {

    Page<Subject> getAllSubjects(Pageable pageable);
}
