package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.DeviceRegistration;
import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationReport;
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
        SELECT new com.vanhuuhien99.school_device_management.projection.DeviceRegistrationReport(
            dr.createdAt,
            d.deviceName,
            t.fullName,
            sc.className,
            CONCAT(FORMAT(s.startTime, 'HH:mm'), '-', FORMAT(s.endTime, 'HH:mm'), ' ', FORMAT(s.scheduleDate, 'dd/MM/yyyy')),
            dr.returnDate,
            dr.description
        )
        FROM DeviceRegistration dr
        JOIN dr.device d
        JOIN dr.teacherAssignment ta
        JOIN ta.teacher t
        JOIN ta.schoolClass sc
        JOIN ta.schedules s
        WHERE dr.createdAt between :startDate and :endDate
    """)
    Page<DeviceRegistrationReport> findDeviceRegistrationReportBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
