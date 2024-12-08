package com.vanhuuhien99.school_device_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceCategoryDropdownDto {
    private Long categoryId;
    private String categoryName;
}
