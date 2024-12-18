package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.entity.ApprovalStatusDefinition;
import com.vanhuuhien99.school_device_management.entity.DeviceRegistration;
import com.vanhuuhien99.school_device_management.formmodel.DeviceRegistrationForm;
import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface DeviceRegistrationService {

    Page<DeviceRegistration> searchByCriteria(String keyword, String filter, String approvalStatus, Pageable pageable);

    Page<DeviceRegistration> searchByCriteria(String keyword, String filter, String approvalStatus, String phoneNumber, Pageable pageRequest);

    List<ApprovalStatusDefinition> getAllApprovalStatus();

    DeviceRegistration getDeviceRegistrationById(Long registrationId);

    void createNewDeviceRegistration(DeviceRegistrationForm form);

    void updateDeviceRegistration(DeviceRegistrationForm form, Long registrationId);

    void deleteDeviceRegistration(Long registrationId);

    Page<DeviceRegistrationReport> getDeviceRegistrationReportInRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
