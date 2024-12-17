package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.mapping.ReportColumnMapping;
import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationReport;
import com.vanhuuhien99.school_device_management.reports.ExcelExportService;
import com.vanhuuhien99.school_device_management.service.DeviceRegistrationService;
import com.vanhuuhien99.school_device_management.utils.AppHelper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private static final String DEVICE_REGISTRATION_REPORT_TEMPLATE = "dashboard/report/device-registration-report";

    private final DeviceRegistrationService deviceRegistrationService;
    private final ExcelExportService excelExportService;

    @GetMapping("/device-registration-report")
    public String getDeviceRegistrationReportView(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "registrationId,asc") String[] sort,
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
            Model model
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        if(startDate == null) startDate = LocalDateTime.now().minusDays(30);
        if(endDate == null) endDate = LocalDateTime.now();
        Page<DeviceRegistrationReport> deviceRegistrationReportPage = deviceRegistrationService
                .getDeviceRegistrationReportInRange(startDate, endDate, pageRequest);

        model.addAttribute("columnMapping", ReportColumnMapping.getColumnTranslationMapping(DeviceRegistrationReport.class));
        model.addAttribute("deviceRegistrationReportPage", deviceRegistrationReportPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);

        return DEVICE_REGISTRATION_REPORT_TEMPLATE;
    }

    @GetMapping("/device-registration-report/export")
    public void exportDeviceRegistrationReport(
            @RequestParam(name = "startDate", required = false) LocalDateTime startDate,
            @RequestParam(name = "endDate", required = false) LocalDateTime endDate,
            HttpServletResponse response
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        String headerKey = HttpHeaders.CONTENT_DISPOSITION;
        String headerValue = "attachment;filename=device-registration-report.xlsx";
        response.setHeader(headerKey, headerValue);

        if(startDate != null && endDate != null) {
            excelExportService.exportDeviceRegistrationReport(response, startDate, endDate);
        } else {
            excelExportService.exportDeviceRegistrationReport(response);
        }
    }

}
