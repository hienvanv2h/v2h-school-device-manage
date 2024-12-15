package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.DeviceCategorySubject;
import com.vanhuuhien99.school_device_management.entity.DeviceCategorySubjectId;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.DeviceCategorySubjectForm;
import com.vanhuuhien99.school_device_management.repository.DeviceCategoryRepository;
import com.vanhuuhien99.school_device_management.repository.DeviceCategorySubjectRepository;
import com.vanhuuhien99.school_device_management.repository.SubjectRepository;
import com.vanhuuhien99.school_device_management.service.DeviceCategorySubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeviceCategorySubjectServiceImpl implements DeviceCategorySubjectService {

    private final DeviceCategorySubjectRepository deviceCategorySubjectRepository;

    private final DeviceCategoryRepository deviceCategoryRepository;

    private final SubjectRepository subjectRepository;

    @Override
    public Page<DeviceCategorySubject> getFilteredDeviceCategorySubjects(String keyword, String filter, Pageable pageable) {
        if(keyword == null || keyword.isEmpty() || filter == null || filter.isEmpty()) {
            return getAllDeviceCategorySubjects(pageable);
        } else {
            // Các giá trị khớp xem trong lớp ColumnMapping
            if(filter.contains("categoryName")) {
                return searchByDeviceCategoryName(keyword, pageable);
            } else if(filter.contains("subjectName")) {
                return searchBySubjectName(keyword, pageable);
            } else {
                return getAllDeviceCategorySubjects(pageable);
            }
        }
    }

    @Override
    public Page<DeviceCategorySubject> getAllDeviceCategorySubjects(Pageable pageable) {
        return deviceCategorySubjectRepository.findAll(pageable);
    }

    @Override
    public Page<DeviceCategorySubject> searchByDeviceCategoryName(String categoryName, Pageable pageable) {
        return deviceCategorySubjectRepository.findByCategoryName(categoryName, pageable);
    }

    @Override
    public Page<DeviceCategorySubject> searchBySubjectName(String subjectName, Pageable pageable) {
        return deviceCategorySubjectRepository.findBySubjectName(subjectName, pageable);
    }

    @Override
    public void createNewDeviceCategorySubject(DeviceCategorySubjectForm form) {
        // Kiểm tra nếu có bản ghi với cặp khóa tương ứng
        Optional<DeviceCategorySubject> deviceCategorySubject = deviceCategorySubjectRepository.findById(
                new DeviceCategorySubjectId(form.getCategoryId(), form.getSubjectId())
        );
        if(deviceCategorySubject.isPresent()) {
            throw new DataIntegrityViolationException("Device category subject already exists");
        }

        var existingDeviceCategory = deviceCategoryRepository.findById(form.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device category with id: " + form.getCategoryId()));
        var existingSubject = subjectRepository.findById(form.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find subject with id: " + form.getSubjectId()));

        DeviceCategorySubject newDeviceCategorySubject = new DeviceCategorySubject(existingDeviceCategory, existingSubject);
        deviceCategorySubjectRepository.save(newDeviceCategorySubject);
    }

    @Override
    public void deleteDeviceCategorySubject(DeviceCategorySubjectId deviceCategorySubjectId) {
        deviceCategorySubjectRepository.deleteById(deviceCategorySubjectId);
    }
}
