package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.ApprovalStatusDefinition;
import com.vanhuuhien99.school_device_management.entity.Device;
import com.vanhuuhien99.school_device_management.entity.DeviceRegistration;
import com.vanhuuhien99.school_device_management.entity.TeacherAssignment;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.DeviceRegistrationForm;
import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationReport;
import com.vanhuuhien99.school_device_management.repository.ApprovalStatusRepository;
import com.vanhuuhien99.school_device_management.repository.DeviceRegistrationRepository;
import com.vanhuuhien99.school_device_management.repository.DeviceRepository;
import com.vanhuuhien99.school_device_management.repository.TeacherAssignmentRepository;
import com.vanhuuhien99.school_device_management.service.DeviceRegistrationService;
import com.vanhuuhien99.school_device_management.specification.DeviceRegistrationSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceRegistrationServiceImpl implements DeviceRegistrationService {

    private final DeviceRegistrationRepository deviceRegistrationRepository;

    private final DeviceRepository deviceRepository;

    private final TeacherAssignmentRepository teacherAssignmentRepository;

    private final ApprovalStatusRepository approvalStatusRepository;

    @Override
    public Page<DeviceRegistration> searchByCriteria(String keyword, String filter, String approvalStatus, Pageable pageable) {
        return searchByCriteria(keyword, filter, approvalStatus, null, pageable);
    }

    @Override
    public Page<DeviceRegistration> searchByCriteria(
            String keyword,
            String filter,
            String approvalStatus,
            String phoneNumber,
            Pageable pageable
    ) {
        Specification<DeviceRegistration> spec = Specification.where(null);

        if(StringUtils.hasText(filter) && StringUtils.hasText(keyword)) {
            if(filter.contains("teacher.fullName")) {
                spec = spec.and(DeviceRegistrationSpec.containsTeacherFullName(keyword));
            }
            if(filter.contains("device.deviceName")) {
                spec = spec.and(DeviceRegistrationSpec.containsDeviceName(keyword));
            }
        }

        if(StringUtils.hasText(phoneNumber)) {
            spec = spec.and(DeviceRegistrationSpec.hasTeacherPhoneNumber(phoneNumber));
        }

        if(StringUtils.hasText(approvalStatus)) {
            spec = spec.and(DeviceRegistrationSpec.hasApprovalStatus(approvalStatus));
        }

        return deviceRegistrationRepository.findAll(spec, pageable);
    }

    @Override
    public List<ApprovalStatusDefinition> getAllApprovalStatus() {
        return approvalStatusRepository.findAll();
    }

    @Override
    public DeviceRegistration getDeviceRegistrationById(Long registrationId) {
        return deviceRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device registration with id: " + registrationId));
    }

    @Override
    @Transactional
    public void createNewDeviceRegistration(DeviceRegistrationForm form) {
        var existingDevice = getDeviceById(form.getDeviceId());
        var existingTeacherAssignment = getTeacherAssignmentById(form.getTeacherAssignmentId());
        if(form.getRegistrationStatus() == null || form.getRegistrationStatus().trim().isEmpty()) {
            form.setRegistrationStatus("Tạo mới");
        }
        if(form.getApprovalStatus() == null || form.getApprovalStatus().trim().isEmpty()) {
            form.setApprovalStatus("Chờ phê duyệt");    // Mặc định khi tạo
        }

        DeviceRegistration newDeviceRegistration = DeviceRegistration.builder()
                .device(existingDevice)
                .teacherAssignment(existingTeacherAssignment)
                .registrationStatus(form.getRegistrationStatus())
                .registrationStatus(form.getRegistrationStatus())
                .approvalStatus(form.getApprovalStatus())
                .scheduleDate(form.getScheduleDate())
                .returnDate(form.getReturnDate())
                .description(form.getDescription())
                .build();
        deviceRegistrationRepository.save(newDeviceRegistration);
    }

    @Override
    @Transactional
    public void updateDeviceRegistration(DeviceRegistrationForm form, Long registrationId) {
        var existingDevice = getDeviceById(form.getDeviceId());
        var existingTeacherAssignment = getTeacherAssignmentById(form.getTeacherAssignmentId());

        DeviceRegistration existingDeviceRegistration = deviceRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device registration with id: " + registrationId));
        existingDeviceRegistration.setDevice(existingDevice);
        existingDeviceRegistration.setTeacherAssignment(existingTeacherAssignment);
        existingDeviceRegistration.setRegistrationStatus(form.getRegistrationStatus());
        existingDeviceRegistration.setApprovalStatus(form.getApprovalStatus());
        existingDeviceRegistration.setScheduleDate(form.getScheduleDate());
        existingDeviceRegistration.setReturnDate(form.getReturnDate());
        existingDeviceRegistration.setDescription(form.getDescription());
        deviceRegistrationRepository.save(existingDeviceRegistration);
    }

    @Override
    @Transactional
    public void deleteDeviceRegistration(Long registrationId) {
        var existingDeviceRegistration = deviceRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device registration with id: " + registrationId));
        deviceRegistrationRepository.delete(existingDeviceRegistration);
    }

    @Override
    public Page<DeviceRegistrationReport> getDeviceRegistrationReportInRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return deviceRegistrationRepository.findDeviceRegistrationReportBetween(startDate, endDate, pageable);
    }

    private Device getDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device with id: " + deviceId));
    }

    private TeacherAssignment getTeacherAssignmentById(Long teacherAssignmentId) {
        return teacherAssignmentRepository.findById(teacherAssignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find teacher assignment with id: " + teacherAssignmentId));
    }
}
