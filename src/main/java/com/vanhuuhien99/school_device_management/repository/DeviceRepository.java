package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.Device;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long>, JpaSpecificationExecutor<Device> {

    @EntityGraph(attributePaths = {"deviceCategory"})
    @NonNull
    Page<Device> findAll(Specification<Device> spec, @NonNull Pageable pageable);

    @Query("SELECT COUNT(d) FROM Device d JOIN d.deviceCategory dc " +
            "WHERE dc.categoryId = :categoryId " +
            "AND d.status NOT IN :statuses " +
            "AND d.createdAt < :startDate")
    Long countTotalAtStartOfYear(Long categoryId, List<String> statuses, LocalDateTime startDate);

    @Query("SELECT COUNT(d) FROM Device d JOIN d.deviceCategory dc " +
            "WHERE dc.categoryId = :categoryId " +
            "AND d.createdAt BETWEEN :startDate AND :endDate")
    Long countTotalImportedDuringYear(Long categoryId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT COUNT(d) FROM Device d JOIN d.deviceCategory dc " +
            "WHERE dc.categoryId = :categoryId " +
            "AND d.status IN :statuses " +
            "AND d.createdAt BETWEEN :startDate AND :endDate")
    Long countTotalDamagedOrLostDuringYear(Long categoryId, List<String> statuses, LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT COUNT(d) FROM Device d JOIN d.deviceCategory dc " +
            "WHERE dc.categoryId = :categoryId " +
            "AND d.status NOT IN :statuses " +
            "AND d.createdAt < :endDate")
    Long countTotalAtEndOfYear(Long categoryId, List<String> statuses, LocalDateTime endDate);
}
