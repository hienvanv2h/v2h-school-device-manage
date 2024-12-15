package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.projection.DeviceCategoryDropdownDto;
import com.vanhuuhien99.school_device_management.entity.DeviceCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceCategoryRepository extends JpaRepository<DeviceCategory, Long> {
    @Query("SELECT new com.vanhuuhien99.school_device_management.projection.DeviceCategoryDropdownDto(c.categoryId, c.categoryName) FROM DeviceCategory c")
    List<DeviceCategoryDropdownDto> getAllDeviceCategoriesForDropdown();

    boolean existsByCategoryName(String categoryName);

    Page<DeviceCategory> findByCategoryNameContaining(String keyword, Pageable pageable);
}
