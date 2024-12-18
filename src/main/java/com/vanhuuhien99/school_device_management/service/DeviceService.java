package com.vanhuuhien99.school_device_management.service;

import com.vanhuuhien99.school_device_management.entity.Device;
import com.vanhuuhien99.school_device_management.formmodel.DeviceForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeviceService {

    Page<Device> searchByCriteria(String providedDeviceName, String subjectName, Pageable pageable);

    Page<Device> getAllDevices(Pageable pageable);

    Device getDeviceById(Long deviceId);

    void createNewDevice(DeviceForm form);

    void updateDevice(DeviceForm form, Long deviceId);

    void deleteDevice(Long deviceId);
}
