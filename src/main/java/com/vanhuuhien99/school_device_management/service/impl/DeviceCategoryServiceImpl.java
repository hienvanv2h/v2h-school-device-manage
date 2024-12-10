package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.projection.DeviceCategoryDropdownDto;
import com.vanhuuhien99.school_device_management.entity.DeviceCategory;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.DeviceCategoryForm;
import com.vanhuuhien99.school_device_management.repository.DeviceCategoryRepository;
import com.vanhuuhien99.school_device_management.service.DeviceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceCategoryServiceImpl implements DeviceCategoryService {

    private final DeviceCategoryRepository deviceCategoryRepository;


    @Override
    public List<DeviceCategoryDropdownDto> getAllDeviceCategoriesForDropdown() {
        return deviceCategoryRepository.getAllDeviceCategoriesForDropdown();
    }

    @Override
    public Page<DeviceCategory> getAllDeviceCategories(Pageable pageable) {
        return deviceCategoryRepository.findAll(pageable);
    }

    @Override
    public Page<DeviceCategory> searchByCategoryNameContaining(String keyword, Pageable pageable) {
        return deviceCategoryRepository.findByCategoryNameContaining(keyword, pageable);
    }

    @Override
    public DeviceCategory getDeviceCategoryById(Long categoryId) {
        return deviceCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device category with id: " + categoryId));
    }

    @Override
    public void createNewDeviceCategory(DeviceCategoryForm form) {
        if(deviceCategoryRepository.existsByCategoryName(form.getCategoryName())) {
            throw new DataIntegrityViolationException("Device category name already exists");
        }
        var newDeviceCategory = DeviceCategory.builder()
                .categoryName(form.getCategoryName())
                .description(form.getDescription())
                .unit(form.getUnit())
                .unitPrice(form.getUnitPrice())
                .build();

        deviceCategoryRepository.save(newDeviceCategory);
    }

    @Override
    public void updateDeviceCategory(DeviceCategoryForm form, Long categoryId) {
        var existingDeviceCategory = getDeviceCategoryById(categoryId);
        existingDeviceCategory.setCategoryName(form.getCategoryName());
        existingDeviceCategory.setDescription(form.getDescription());
        existingDeviceCategory.setUnit(form.getUnit());
        existingDeviceCategory.setUnitPrice(form.getUnitPrice());
        deviceCategoryRepository.save(existingDeviceCategory);
    }

    @Override
    public void deleteDeviceCategory(Long categoryId) {
        var existingDeviceCategory = getDeviceCategoryById(categoryId);
        deviceCategoryRepository.delete(existingDeviceCategory);
    }
}
