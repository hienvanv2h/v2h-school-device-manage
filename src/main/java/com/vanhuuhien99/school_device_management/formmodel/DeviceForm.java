package com.vanhuuhien99.school_device_management.formmodel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class DeviceForm {

    @NotBlank(message = "Device name is required")
    @Size(max = 100, message = "Device name must not exceed 100 characters")
    private String deviceName;

    @NotNull(message = "Device category is required")
    private Long deviceCategoryId;

    private String description;

    @NotBlank(message = "Status is required")
    @Size(max = 50, message = "Status must not exceed 50 characters")
    private String status;
}
