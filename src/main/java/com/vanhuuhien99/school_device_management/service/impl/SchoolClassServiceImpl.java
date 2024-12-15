package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.SchoolClass;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.SchoolClassForm;
import com.vanhuuhien99.school_device_management.repository.SchoolClassRepository;
import com.vanhuuhien99.school_device_management.service.SchoolClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;


    @Override
    public Page<SchoolClass> getFilteredSchoolClasses(String keyword, String filter, Pageable pageable) {
        if(keyword == null || keyword.isEmpty() || filter == null || filter.isEmpty()) {
            return getAllSchoolClasses(pageable);
        } else {
            // Các giá trị khớp xem trong lớp ColumnMapping
            if(filter.equalsIgnoreCase("className")) {
                return searchByClassNameContaining(keyword, pageable);
            } else {
                return getAllSchoolClasses(pageable);
            }
        }
    }

    @Override
    public Page<SchoolClass> getAllSchoolClasses(Pageable pageable) {
        return schoolClassRepository.findAll(pageable);
    }

    @Override
    public <T> List<T> getAll(Class<T> classType) {
        return schoolClassRepository.findBy(classType);
    }

    @Override
    public SchoolClass getSchoolClassById(Long classId) {
        return schoolClassRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find school class with id: " + classId));
    }

    @Override
    public Page<SchoolClass> searchByClassNameContaining(String keyword, Pageable pageable) {
        return schoolClassRepository.findByClassNameContaining(keyword, pageable);
    }

    @Override
    public void createNewSchoolClass(SchoolClassForm form) {
        var newSchoolClass = SchoolClass.builder()
                .className(form.getClassName())
                .build();
        schoolClassRepository.save(newSchoolClass);
    }

    @Override
    public void updateSchoolClass(SchoolClassForm form, Long classId) {
        SchoolClass existingSchoolClass = getSchoolClassById(classId);
        existingSchoolClass.setClassName(form.getClassName());
        schoolClassRepository.save(existingSchoolClass);
    }

    @Override
    public void deleteSchoolClass(Long classId) {
        SchoolClass existingSchoolClass = getSchoolClassById(classId);
        schoolClassRepository.deleteById(existingSchoolClass.getClassId());
    }
}
