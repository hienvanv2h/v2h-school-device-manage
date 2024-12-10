package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.projection.DeviceCategoryDropdownDto;
import com.vanhuuhien99.school_device_management.entity.DeviceCategory;
import com.vanhuuhien99.school_device_management.formmodel.DeviceCategoryForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceCategoryService {

    List<DeviceCategoryDropdownDto> getAllDeviceCategoriesForDropdown();

    Page<DeviceCategory> getAllDeviceCategories(Pageable pageable);

    Page<DeviceCategory> searchByCategoryNameContaining(String keyword, Pageable pageable);

    DeviceCategory getDeviceCategoryById(Long categoryId);

    void createNewDeviceCategory(DeviceCategoryForm form);

    void updateDeviceCategory(DeviceCategoryForm form, Long categoryId);

    void deleteDeviceCategory(Long categoryId);
}
