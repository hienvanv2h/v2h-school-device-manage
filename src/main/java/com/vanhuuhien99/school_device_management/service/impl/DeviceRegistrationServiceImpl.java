package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.ApprovalStatusDefinition;
import com.vanhuuhien99.school_device_management.entity.Device;
import com.vanhuuhien99.school_device_management.entity.DeviceRegistration;
import com.vanhuuhien99.school_device_management.entity.TeacherAssignment;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.DeviceRegistrationForm;
import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationProjection;
import com.vanhuuhien99.school_device_management.repository.ApprovalStatusRepository;
import com.vanhuuhien99.school_device_management.repository.DeviceRegistrationRepository;
import com.vanhuuhien99.school_device_management.repository.DeviceRepository;
import com.vanhuuhien99.school_device_management.repository.TeacherAssignmentRepository;
import com.vanhuuhien99.school_device_management.service.DeviceRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceRegistrationServiceImpl implements DeviceRegistrationService {

    private final DeviceRegistrationRepository deviceRegistrationRepository;

    private final DeviceRepository deviceRepository;

    private final TeacherAssignmentRepository teacherAssignmentRepository;

    private final ApprovalStatusRepository approvalStatusRepository;

    @Override
    public Page<DeviceRegistrationProjection> getAllDeviceRegistrations(Pageable pageable) {
        return deviceRegistrationRepository.findAllDeviceRegistrations(pageable);
    }

    @Override
    public List<ApprovalStatusDefinition> getAllApprovalStatus() {
        return approvalStatusRepository.findAll();
    }

    @Override
    public DeviceRegistrationProjection getDeviceRegistrationById(Long registrationId) {
        return deviceRegistrationRepository.findDeviceRegistrationById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device registration with id: " + registrationId));
    }

    @Override
    public Page<DeviceRegistrationProjection> searchByTeacherNameContaining(String keyword, Pageable pageable) {
        return deviceRegistrationRepository.findByTeacherFullNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Page<DeviceRegistrationProjection> searchByApprovalStatus(String approvalStatus, Pageable pageable) {
        return deviceRegistrationRepository.findByApprovalStatus(approvalStatus, pageable);
    }

    @Override
    public Page<DeviceRegistrationProjection> searchByDeviceNameContaining(String keyword, Pageable pageable) {
        return deviceRegistrationRepository.findByDeviceNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
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
                .description(form.getDescription())
                .build();
        deviceRegistrationRepository.save(newDeviceRegistration);
    }

    @Override
    public void updateDeviceRegistration(DeviceRegistrationForm form, Long registrationId) {
        var existingDevice = getDeviceById(form.getDeviceId());
        var existingTeacherAssignment = getTeacherAssignmentById(form.getTeacherAssignmentId());

        DeviceRegistration existingDeviceRegistration = deviceRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device registration with id: " + registrationId));
        existingDeviceRegistration.setDevice(existingDevice);
        existingDeviceRegistration.setTeacherAssignment(existingTeacherAssignment);
        existingDeviceRegistration.setRegistrationStatus(form.getRegistrationStatus());
        existingDeviceRegistration.setApprovalStatus(form.getApprovalStatus());
        existingDeviceRegistration.setDescription(form.getDescription());
        deviceRegistrationRepository.save(existingDeviceRegistration);
    }

    @Override
    public void deleteDeviceRegistration(Long registrationId) {
        var existingDeviceRegistration = deviceRegistrationRepository.findById(registrationId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device registration with id: " + registrationId));
        deviceRegistrationRepository.delete(existingDeviceRegistration);
    }

    // Tách logic truy vấn khỏi controller
    @Override
    public Page<DeviceRegistrationProjection> getFilteredDeviceRegistrations(String keyword, String filter, String approvalStatus, Pageable pageable) {
        if ((keyword == null || keyword.isEmpty()) &&
                (filter == null || filter.isEmpty()) &&
                (approvalStatus == null || approvalStatus.isEmpty())) {
            return getAllDeviceRegistrations(pageable);
        }

        if (filter != null && filter.contains("teacher.fullName")) {
            return searchByTeacherNameContaining(keyword, pageable);
        } else if (filter != null && filter.contains("device.deviceName")) {
            return searchByDeviceNameContaining(keyword, pageable);
        } else if (approvalStatus != null && !approvalStatus.isEmpty()) {
            return searchByApprovalStatus(approvalStatus, pageable);
        }

        return getAllDeviceRegistrations(pageable);
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
