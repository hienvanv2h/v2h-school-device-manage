package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.SchoolClass;
import com.vanhuuhien99.school_device_management.repository.SchoolClassRepository;
import com.vanhuuhien99.school_device_management.service.SchoolClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;


    @Override
    public Page<SchoolClass> getAllSchoolClasses(Pageable pageable) {
        return schoolClassRepository.findAll(pageable);
    }

    @Override
    public Page<SchoolClass> searchByClassName(String keyword, Pageable pageable) {
        return schoolClassRepository.searchByClassName(keyword, pageable);
    }
}
