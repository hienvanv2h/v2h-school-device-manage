package com.vanhuuhien99.school_device_management.projection;

import com.vanhuuhien99.school_device_management.entity.Device;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class DeviceDTO {

    private Long deviceId;
    private String deviceName;
    private String categoryName;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static DeviceDTO fromDevice(Device device) {
        return DeviceDTO.builder()
                .deviceId(device.getDeviceId())
                .deviceName(device.getDeviceName())
                .categoryName(device.getDeviceCategory() != null ? device.getDeviceCategory().getCategoryName() : "")
                .description(device.getDescription())
                .status(device.getStatus())
                .createdAt(device.getCreatedAt())
                .updatedAt(device.getUpdatedAt())
                .build();
    }
}
