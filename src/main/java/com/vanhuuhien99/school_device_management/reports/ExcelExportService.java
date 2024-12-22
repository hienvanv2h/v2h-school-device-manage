package com.vanhuuhien99.school_device_management.reports;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;

public interface ExcelExportService {

    void exportDeviceRegistrationReport(HttpServletResponse response) throws IOException;

    void exportDeviceRegistrationReport(HttpServletResponse response, LocalDateTime startDate, LocalDateTime endDate) throws IOException;

    void exportDeviceSummaryReport(HttpServletResponse response, LocalDateTime startDate, LocalDateTime endDate) throws IOException;
}
