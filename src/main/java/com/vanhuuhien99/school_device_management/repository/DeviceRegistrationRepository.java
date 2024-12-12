package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.DeviceRegistration;
import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DeviceRegistrationRepository extends JpaRepository<DeviceRegistration, Long> {

    @Query("SELECT dr.registrationId AS registrationId, " +
            "d.deviceId AS deviceId, d.deviceName AS deviceName, ta.assignmentId AS teacherAssignmentId, t.fullName AS teacherName, " +
            "dr.registrationStatus AS registrationStatus, dr.approvalStatus AS approvalStatus, dr.description AS description, " +
            "dr.createdAt AS createdAt, dr.updatedAt AS updatedAt " +
            "FROM DeviceRegistration dr " +
            "JOIN dr.device d " +
            "JOIN dr.teacherAssignment ta " +
            "JOIN ta.teacher t "
    )
    Page<DeviceRegistrationProjection> findAllDeviceRegistrations(Pageable pageable);

    @Query("SELECT dr.registrationId AS registrationId, " +
            "d.deviceId AS deviceId, d.deviceName AS deviceName, ta.assignmentId AS teacherAssignmentId, t.fullName AS teacherName, " +
            "dr.registrationStatus AS registrationStatus, dr.approvalStatus AS approvalStatus, dr.description AS description, " +
            "dr.createdAt AS createdAt, dr.updatedAt AS updatedAt " +
            "FROM DeviceRegistration dr " +
            "JOIN dr.device d " +
            "JOIN dr.teacherAssignment ta " +
            "JOIN ta.teacher t " +
            "WHERE dr.registrationId = :registrationId "
    )
    Optional<DeviceRegistrationProjection> findDeviceRegistrationById(Long registrationId);

    @Query("SELECT dr.registrationId AS registrationId, " +
            "d.deviceId AS deviceId, d.deviceName AS deviceName, ta.assignmentId AS teacherAssignmentId, t.fullName AS teacherName, " +
            "dr.registrationStatus AS registrationStatus, dr.approvalStatus AS approvalStatus, dr.description AS description, " +
            "dr.createdAt AS createdAt, dr.updatedAt AS updatedAt " +
            "FROM DeviceRegistration dr " +
            "JOIN dr.device d " +
            "JOIN dr.teacherAssignment ta " +
            "JOIN ta.teacher t " +
            "WHERE t.fullName LIKE CONCAT('%', :keyword, '%') "
    )
    Page<DeviceRegistrationProjection> findByTeacherFullNameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT dr.registrationId AS registrationId, " +
            "d.deviceId AS deviceId, d.deviceName AS deviceName, ta.assignmentId AS teacherAssignmentId, t.fullName AS teacherName, " +
            "dr.registrationStatus AS registrationStatus, dr.approvalStatus AS approvalStatus, dr.description AS description, " +
            "dr.createdAt AS createdAt, dr.updatedAt AS updatedAt " +
            "FROM DeviceRegistration dr " +
            "JOIN dr.device d " +
            "JOIN dr.teacherAssignment ta " +
            "JOIN ta.teacher t " +
            "WHERE d.deviceName LIKE CONCAT('%', :keyword, '%') "
    )
    Page<DeviceRegistrationProjection> findByDeviceNameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT dr.registrationId AS registrationId, " +
            "d.deviceId AS deviceId, d.deviceName AS deviceName, ta.assignmentId AS teacherAssignmentId, t.fullName AS teacherName, " +
            "dr.registrationStatus AS registrationStatus, dr.approvalStatus AS approvalStatus, dr.description AS description, " +
            "dr.createdAt AS createdAt, dr.updatedAt AS updatedAt " +
            "FROM DeviceRegistration dr " +
            "JOIN dr.device d " +
            "JOIN dr.teacherAssignment ta " +
            "JOIN ta.teacher t " +
            "WHERE dr.approvalStatus = :approvalStatus "
    )
    Page<DeviceRegistrationProjection> findByApprovalStatus(String approvalStatus, Pageable pageable);
}
