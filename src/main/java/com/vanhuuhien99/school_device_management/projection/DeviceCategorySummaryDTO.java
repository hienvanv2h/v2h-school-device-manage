package com.vanhuuhien99.school_device_management.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceCategorySummaryDTO {

    private Long categoryId;
    private String categoryName;
    private String description;
    private String unit;
    private Double unitPrice;
    private Long deviceCount;
}
