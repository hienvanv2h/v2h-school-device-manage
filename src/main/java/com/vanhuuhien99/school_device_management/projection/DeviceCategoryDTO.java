package com.vanhuuhien99.school_device_management.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceCategoryDTO {
    private Long categoryId;
    private String categoryName;
}
