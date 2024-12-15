package com.vanhuuhien99.school_device_management.service.impl;

import com.vanhuuhien99.school_device_management.entity.Device;
import com.vanhuuhien99.school_device_management.entity.DeviceCategory;
import com.vanhuuhien99.school_device_management.exception.ResourceNotFoundException;
import com.vanhuuhien99.school_device_management.formmodel.DeviceForm;
import com.vanhuuhien99.school_device_management.repository.DeviceCategoryRepository;
import com.vanhuuhien99.school_device_management.repository.DeviceRepository;
import com.vanhuuhien99.school_device_management.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    private final DeviceCategoryRepository deviceCategoryRepository;

    @Override
    public Page<Device> getFilteredDevices(String keyword, String filter, Pageable pageable) {
        if(keyword == null || keyword.isEmpty() || filter == null || filter.isEmpty()) {
            return getAllDevices(pageable);
        } else {
            // Các giá trị khớp xem trong lớp ColumnMapping
            if(filter.equalsIgnoreCase("deviceName")) {
                return searchByDeviceNameContaining(keyword, pageable);
            } else if(filter.equalsIgnoreCase("subjectName")) {
                return searchBySubjectName(keyword, pageable);
            } else {
                return getAllDevices(pageable);
            }
        }
    }

    @Override
    public Page<Device> getAllDevices(Pageable pageable) {
        return deviceRepository.findAll(pageable);
    }

    @Override
    public Page<Device> searchByDeviceNameContaining(String keyword, Pageable pageable) {
        return deviceRepository.findByDeviceNameContaining(keyword, pageable);
    }

    @Override
    public Page<Device> searchBySubjectName(String subjectName, Pageable pageable) {
//        return deviceRepository.findDevicesBySubjectName(subjectName, pageable);
        return getAllDevices(pageable);
    }


    @Override
    public Device getDeviceById(Long deviceId) {
        return deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device with id: " + deviceId));
    }

    @Override
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
    public void deleteDevice(Long deviceId) {
        var existingDevice = getDeviceById(deviceId);
        deviceRepository.delete(existingDevice);
    }

    private DeviceCategory getDeviceCategoryById(Long deviceCategoryId) {
        return deviceCategoryRepository.findById(deviceCategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Cannot find device category with id: " + deviceCategoryId));
    }
}
