package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.Device;
import com.vanhuuhien99.school_device_management.entity.DeviceCategory;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.DeviceForm;
import com.vanhuuhien99.school_device_management.repository.DeviceCategoryRepository;
import com.vanhuuhien99.school_device_management.repository.DeviceRepository;
import com.vanhuuhien99.school_device_management.service.DeviceService;
import com.vanhuuhien99.school_device_management.specification.DeviceSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    private final DeviceCategoryRepository deviceCategoryRepository;

    @Override
    public Page<Device> searchByCriteria(String providedDeviceName, String subjectName, Pageable pageable) {
        Specification<Device> spec = Specification.where(null);

        if(StringUtils.hasText(providedDeviceName)) {
            spec = spec.and(DeviceSpec.containsDeviceName(providedDeviceName));
        }

        if(StringUtils.hasText(subjectName)) {
            spec = spec.and(DeviceSpec.hasSubjectName(subjectName));
        }

        return deviceRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Device> getAllDevices(Pageable pageable) {
        return deviceRepository.findAll(pageable);
    }

    @Override
    public Device getDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device with id: " + deviceId));
    }

    @Override
    @Transactional
    public void createNewDevice(DeviceForm form) {
        DeviceCategory existingDeviceCategory = getDeviceCategoryById(form.getDeviceCategoryId());

        Device newDevice = Device.builder()
                .deviceName(form.getDeviceName())
                .deviceCategory(existingDeviceCategory)
                .description(form.getDescription())
                .status(form.getStatus())
                .build();
        deviceRepository.save(newDevice);
    }

    @Override
    @Transactional
    public void updateDevice(DeviceForm form, Long deviceId) {
        DeviceCategory existingDeviceCategory = getDeviceCategoryById(form.getDeviceCategoryId());

        var existingDevice = getDeviceById(deviceId);
        existingDevice.setDeviceName(form.getDeviceName());
        existingDevice.setDeviceCategory(existingDeviceCategory);
        existingDevice.setDescription(form.getDescription());
        existingDevice.setStatus(form.getStatus());
        deviceRepository.save(existingDevice);
    }

    @Override
    @Transactional
    public void deleteDevice(Long deviceId) {
        var existingDevice = getDeviceById(deviceId);
        deviceRepository.delete(existingDevice);
    }

    private DeviceCategory getDeviceCategoryById(Long deviceCategoryId) {
        return deviceCategoryRepository.findById(deviceCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device category with id: " + deviceCategoryId));
    }
}
