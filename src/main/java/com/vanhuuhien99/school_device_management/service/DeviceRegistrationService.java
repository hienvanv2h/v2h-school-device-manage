package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.entity.ApprovalStatusDefinition;
import com.vanhuuhien99.school_device_management.formmodel.DeviceRegistrationForm;
import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DeviceRegistrationService {

    Page<DeviceRegistrationProjection> getAllDeviceRegistrations(Pageable pageable);

    List<ApprovalStatusDefinition> getAllApprovalStatus();

    DeviceRegistrationProjection getDeviceRegistrationById(Long registrationId);

    Page<DeviceRegistrationProjection> searchByTeacherNameContaining(String keyword, Pageable pageable);

    Page<DeviceRegistrationProjection> searchByApprovalStatus(String approvalStatus, Pageable pageable);

    Page<DeviceRegistrationProjection> searchByDeviceNameContaining(String keyword, Pageable pageable);

    void createNewDeviceRegistration(DeviceRegistrationForm form);

    void updateDeviceRegistration(DeviceRegistrationForm form, Long registrationId);

    void deleteDeviceRegistration(Long registrationId);

    Page<DeviceRegistrationProjection> getFilteredDeviceRegistrations(String keyword, String filter, String approvalStatus, Pageable pageRequest);
}
