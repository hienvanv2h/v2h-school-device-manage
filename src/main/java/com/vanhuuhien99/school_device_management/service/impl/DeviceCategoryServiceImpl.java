package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.projection.DeviceCategoryDTO;
import com.vanhuuhien99.school_device_management.entity.DeviceCategory;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.DeviceCategoryForm;
import com.vanhuuhien99.school_device_management.projection.DeviceCategorySummaryDTO;
import com.vanhuuhien99.school_device_management.repository.DeviceCategoryRepository;
import com.vanhuuhien99.school_device_management.service.DeviceCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceCategoryServiceImpl implements DeviceCategoryService {

    private final DeviceCategoryRepository deviceCategoryRepository;

    @Override
    public Page<DeviceCategory> getFilteredDeviceCategories(String keyword, String filter, Pageable pageable) {
        if(keyword == null || keyword.isEmpty() || filter == null || filter.isEmpty()) {
            return getAllDeviceCategories(pageable);
        } else {
            // Các giá trị khớp xem trong lớp ColumnMapping
            if(filter.equalsIgnoreCase("categoryName")) {
                return searchByCategoryNameContaining(keyword, pageable);
            } else {
                return getAllDeviceCategories(pageable);
            }
        }
    }

    @Override
    public List<DeviceCategoryDTO> getAllDeviceCategoriesForDropdown() {
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
    public Page<DeviceCategorySummaryDTO> getDeviceCategorySummary(Pageable pageable) {
        return deviceCategoryRepository.getDeviceCategorySummary(pageable);
    }

    @Override
    public Page<DeviceCategorySummaryDTO> getDamagedOrLostDeviceCategorySummary(Pageable pageable) {
        var statuses = List.of("Hỏng", "Mất");
        return deviceCategoryRepository.getDamagedOrLostDeviceCategorySummary(statuses, pageable);
    }

    @Override
    public Page<DeviceCategorySummaryDTO> getDeviceCategorySummaryByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return deviceCategoryRepository.getDeviceCategorySummaryByDateRange(startDate, endDate, pageable);
    }

    @Override
    public Page<DeviceCategorySummaryDTO> getDamagedOrLostDeviceCategorySummaryByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        var statuses = List.of("Hỏng", "Mất");
        return deviceCategoryRepository.getDamagedOrLostDeviceCategorySummaryByDateRange(startDate, endDate, statuses, pageable);
    }

    @Override
    @Transactional
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
    @Transactional
    public void updateDeviceCategory(DeviceCategoryForm form, Long categoryId) {
        var existingDeviceCategory = getDeviceCategoryById(categoryId);
        existingDeviceCategory.setCategoryName(form.getCategoryName());
        existingDeviceCategory.setDescription(form.getDescription());
        existingDeviceCategory.setUnit(form.getUnit());
        existingDeviceCategory.setUnitPrice(form.getUnitPrice());
        deviceCategoryRepository.save(existingDeviceCategory);
    }

    @Override
    @Transactional
    public void deleteDeviceCategory(Long categoryId) {
        var existingDeviceCategory = getDeviceCategoryById(categoryId);
        deviceCategoryRepository.delete(existingDeviceCategory);
    }
}
