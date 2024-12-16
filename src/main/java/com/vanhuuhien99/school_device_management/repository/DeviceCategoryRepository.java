package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.DeviceCategory;
import com.vanhuuhien99.school_device_management.projection.DeviceCategoryDto;
import com.vanhuuhien99.school_device_management.projection.DeviceCategorySummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceCategoryRepository extends JpaRepository<DeviceCategory, Long> {
    @Query("SELECT new com.vanhuuhien99.school_device_management.projection.DeviceCategoryDto(c.categoryId, c.categoryName) FROM DeviceCategory c")
    List<DeviceCategoryDto> getAllDeviceCategoriesForDropdown();

    boolean existsByCategoryName(String categoryName);

    Page<DeviceCategory> findByCategoryNameContaining(String keyword, Pageable pageable);


    @Query("""
            SELECT new com.vanhuuhien99.school_device_management.projection.DeviceCategorySummaryDTO(
                dc.categoryId,
                dc.categoryName,
                dc.description,
                dc.unit,
                dc.unitPrice,
                COUNT(d.deviceId)
            )
            FROM DeviceCategory AS dc
            JOIN dc.devices d
            GROUP BY dc.categoryId, dc.categoryName, dc.description, dc.unit, dc.unitPrice
    """)
    Page<DeviceCategorySummaryDTO> getDeviceCategorySummary(Pageable pageable);

    @Query("""
            SELECT new com.vanhuuhien99.school_device_management.projection.DeviceCategorySummaryDTO(
                dc.categoryId,
                dc.categoryName,
                dc.description,
                dc.unit,
                dc.unitPrice,
                COUNT(d.deviceId)
            )
            FROM DeviceCategory AS dc
            JOIN dc.devices d
            WHERE d.status IN :statuses
            GROUP BY dc.categoryId, dc.categoryName, dc.description, dc.unit, dc.unitPrice
    """)
    Page<DeviceCategorySummaryDTO> getDamagedOrLostDeviceCategorySummary(@Param("statuses") List<String> statuses, Pageable pageable);

    @Query("""
            SELECT new com.vanhuuhien99.school_device_management.projection.DeviceCategorySummaryDTO(
                dc.categoryId,
                dc.categoryName,
                dc.description,
                dc.unit,
                dc.unitPrice,
                COUNT(d.deviceId)
            )
            FROM DeviceCategory AS dc
            JOIN dc.devices d
            WHERE d.createdAt BETWEEN :startDate AND :endDate
            GROUP BY dc.categoryId, dc.categoryName, dc.description, dc.unit, dc.unitPrice
    """)
    Page<DeviceCategorySummaryDTO> getDeviceCategorySummaryByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    @Query("""
            SELECT new com.vanhuuhien99.school_device_management.projection.DeviceCategorySummaryDTO(
                dc.categoryId,
                dc.categoryName,
                dc.description,
                dc.unit,
                dc.unitPrice,
                COUNT(d.deviceId)
            )
            FROM DeviceCategory AS dc
            JOIN dc.devices d
            WHERE d.status IN :statuses AND d.createdAt BETWEEN :startDate AND :endDate
            GROUP BY dc.categoryId, dc.categoryName, dc.description, dc.unit, dc.unitPrice
    """)
    Page<DeviceCategorySummaryDTO> getDamagedOrLostDeviceCategorySummaryByDateRange(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("statuses") List<String> statuses,
            Pageable pageable);
}
