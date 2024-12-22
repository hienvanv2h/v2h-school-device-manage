package com.vanhuuhien99.school_device_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSummaryReportDTO {

    private String deviceCategoryName;
    private String unit;
    private Long totalAtStartOfYear;
    private Long totalImportedDuringYear;
    private Long totalDamagedOrLostDuringYear;
    private Long totalAtEndOfYear;
    private String description;
}
