package com.vanhuuhien99.school_device_management.reports.strategy;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

public interface ExcelReportStrategy<T> {

    Workbook generateReport(List<T> data, String reportName);
}
