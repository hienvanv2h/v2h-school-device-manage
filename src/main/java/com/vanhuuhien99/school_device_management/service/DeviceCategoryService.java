package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.projection.DeviceCategoryDTO;
import com.vanhuuhien99.school_device_management.entity.DeviceCategory;
import com.vanhuuhien99.school_device_management.formmodel.DeviceCategoryForm;
import com.vanhuuhien99.school_device_management.projection.DeviceCategorySummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceCategoryService {

    Page<DeviceCategory> getFilteredDeviceCategories(String keyword, String filter, Pageable pageable);

    List<DeviceCategoryDTO> getAllDeviceCategoriesForDropdown();

    Page<DeviceCategory> getAllDeviceCategories(Pageable pageable);

    Page<DeviceCategory> searchByCategoryNameContaining(String keyword, Pageable pageable);

    DeviceCategory getDeviceCategoryById(Long categoryId);

    Page<DeviceCategorySummaryDTO> getDeviceCategorySummary(Pageable pageable);

    Page<DeviceCategorySummaryDTO> getDamagedOrLostDeviceCategorySummary(Pageable pageable);

    Page<DeviceCategorySummaryDTO> getDeviceCategorySummaryByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<DeviceCategorySummaryDTO> getDamagedOrLostDeviceCategorySummaryByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    void createNewDeviceCategory(DeviceCategoryForm form);

    void updateDeviceCategory(DeviceCategoryForm form, Long categoryId);

    void deleteDeviceCategory(Long categoryId);
}
