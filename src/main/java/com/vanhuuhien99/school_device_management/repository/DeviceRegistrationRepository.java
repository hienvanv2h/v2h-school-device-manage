package com.vanhuuhien99.school_device_management.repository;

import com.vanhuuhien99.school_device_management.entity.DeviceRegistration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;

public interface DeviceRegistrationRepository extends JpaRepository<DeviceRegistration, Long>, JpaSpecificationExecutor<DeviceRegistration> {

    @EntityGraph(attributePaths = {"device", "teacherAssignment", "teacherAssignment.teacher"})
    @NonNull
    Page<DeviceRegistration> findAll(Specification<DeviceRegistration> spec, @NonNull Pageable pageable);

    //    @Query("SELECT dr FROM DeviceRegistration dr " +
//            "JOIN dr.device d " +
//            "JOIN dr.teacherAssignment ta " +
//            "JOIN ta.teacher t "
//    )
//    Page<DeviceRegistration> findAllDeviceRegistrations(Pageable pageable);
//
//    @Query("SELECT dr FROM DeviceRegistration dr " +
//            "JOIN dr.device d " +
//            "JOIN dr.teacherAssignment ta " +
//            "JOIN ta.teacher t " +
//            "WHERE dr.registrationId = :registrationId "
//    )
//    Optional<DeviceRegistration> findDeviceRegistrationById(Long registrationId);
//
//    @Query("SELECT dr FROM DeviceRegistration dr " +
//            "JOIN dr.device d " +
//            "JOIN dr.teacherAssignment ta " +
//            "JOIN ta.teacher t " +
//            "WHERE t.fullName LIKE CONCAT('%', :keyword, '%') "
//    )
//    Page<DeviceRegistration> findByTeacherFullNameContainingIgnoreCase(String keyword, Pageable pageable);
//
//    @Query("SELECT dr FROM DeviceRegistration dr " +
//            "JOIN dr.device d " +
//            "JOIN dr.teacherAssignment ta " +
//            "JOIN ta.teacher t " +
//            "WHERE d.deviceName LIKE CONCAT('%', :keyword, '%') "
//    )
//    Page<DeviceRegistration> findByDeviceNameContainingIgnoreCase(String keyword, Pageable pageable);
//
//    @Query("SELECT dr FROM DeviceRegistration dr " +
//            "JOIN dr.device d " +
//            "JOIN dr.teacherAssignment ta " +
//            "JOIN ta.teacher t " +
//            "WHERE dr.approvalStatus = :approvalStatus "
//    )
//    Page<DeviceRegistration> findByApprovalStatus(String approvalStatus, Pageable pageable);
//
//    @Query("SELECT dr FROM DeviceRegistration dr " +
//            "JOIN dr.device d " +
//            "JOIN dr.teacherAssignment ta " +
//            "JOIN ta.teacher t " +
//            "WHERE t.phoneNumber = :phoneNumber "
//    )
//    Page<DeviceRegistration> findByTeacherPhoneNumber(String phoneNumber, Pageable pageable);
}
