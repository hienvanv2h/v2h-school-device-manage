package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.dto.DeviceRegistrationReportDTO;
import com.vanhuuhien99.school_device_management.entity.DeviceRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public interface DeviceRegistrationRepository extends JpaRepository<DeviceRegistration, Long>, JpaSpecificationExecutor<DeviceRegistration> {

    @EntityGraph(attributePaths = {"device", "teacherAssignment", "teacherAssignment.teacher"})
    @NonNull
    Page<DeviceRegistration> findAll(Specification<DeviceRegistration> spec, @NonNull Pageable pageable);

    @Query("""
        SELECT new com.vanhuuhien99.school_device_management.dto.DeviceRegistrationReportDTO(
            dr.createdAt,
            d.deviceName,
            t.fullName,
            sc.className,
            CONCAT(FORMAT(s.startTime, 'HH:mm'), '-', FORMAT(s.endTime, 'HH:mm'), ' ', FORMAT(s.scheduleDate, 'dd/MM/yyyy')),
            dr.returnDate,
            dr.description
        )
        FROM DeviceRegistration dr
        LEFT JOIN dr.device d
        LEFT JOIN dr.teacherAssignment ta
        LEFT JOIN ta.teacher t
        LEFT JOIN ta.schoolClass sc
        LEFT JOIN ta.schedules s
        WHERE dr.createdAt between :startDate and :endDate
        AND dr.scheduleDate = s.scheduleDate
    """)
    Page<DeviceRegistrationReportDTO> findDeviceRegistrationReportBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
