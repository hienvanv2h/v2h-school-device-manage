package com.vanhuuhien99.school_device_management.controller;

import com.vanhuuhien99.school_device_management.dto.DeviceRegistrationReportDTO;
import com.vanhuuhien99.school_device_management.mapping.ReportColumnMapping;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private static final String DEVICE_REGISTRATION_REPORT_TEMPLATE = "dashboard/report/device-registration-report";
    private static final String DEVICE_SUMMARY_REPORT_TEMPLATE = "dashboard/report/device-summary-report";

    private final DeviceRegistrationService deviceRegistrationService;
    private final ExcelExportService excelExportService;

    @GetMapping("/device-registration-report")
    public String getDeviceRegistrationReportView(
            @RequestParam(defaultValue = "1" ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "registrationId,asc") String[] sort,
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) LocalDate endDate,
            Model model
    ) {
        PageRequest pageRequest = AppHelper.createPageRequest(page, size, sort);
        if(startDate == null) startDate = LocalDate.now().minusDays(30);
        if(endDate == null) endDate = LocalDate.now();

        // Chuyển đổi LocalDate sang LocalDateTime
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        Page<DeviceRegistrationReportDTO> deviceRegistrationReportPage = deviceRegistrationService
                .getDeviceRegistrationReportInRange(startDateTime, endDateTime, pageRequest);

        model.addAttribute("columnMapping", ReportColumnMapping.getColumnTranslationMapping(DeviceRegistrationReportDTO.class));
        model.addAttribute("deviceRegistrationReportPage", deviceRegistrationReportPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sort[0]);
        model.addAttribute("sortDirection", sort[1]);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return DEVICE_REGISTRATION_REPORT_TEMPLATE;
    }

    @GetMapping("/device-registration-report/export")
    public void exportDeviceRegistrationReport(
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) LocalDate endDate,
            HttpServletResponse response
    ) throws IOException {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        String headerKey = HttpHeaders.CONTENT_DISPOSITION;
        String headerValue = "attachment;filename=device-registration-report.xlsx";
        response.setHeader(headerKey, headerValue);

        if(startDate != null && endDate != null) {
            // Chuyển đổi LocalDate sang LocalDateTime
            LocalDateTime startDateTime = startDate.atStartOfDay();
            LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
            excelExportService.exportDeviceRegistrationReport(response, startDateTime, endDateTime);
        } else {
            excelExportService.exportDeviceRegistrationReport(response);
        }
    }

    @GetMapping("/device-summary-report")
    public String getDeviceSummaryReportView(
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) LocalDate endDate,
            Model model
    ) {
        if(startDate == null) startDate = LocalDate.ofYearDay(LocalDate.now().getYear(), 1);
        if(endDate == null) endDate = LocalDate.ofYearDay(LocalDate.now().getYear(), 365);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return DEVICE_SUMMARY_REPORT_TEMPLATE;
    }

    @GetMapping("/device-summary-report/export")
    public void exportDeviceSummaryReport(
            @RequestParam(name = "startDate", required = false) LocalDate startDate,
            @RequestParam(name = "endDate", required = false) LocalDate endDate,
            HttpServletResponse response
    ) throws IOException {
        if(startDate == null) startDate = LocalDate.ofYearDay(LocalDate.now().getYear(), 1);
        if(endDate == null) endDate = LocalDate.ofYearDay(LocalDate.now().getYear(), 365);
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        String headerKey = HttpHeaders.CONTENT_DISPOSITION;
        String headerValue = "attachment;filename=device-summary-report.xlsx";
        response.setHeader(headerKey, headerValue);

        // Chuyển đổi LocalDate sang LocalDateTime
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        excelExportService.exportDeviceSummaryReport(response, startDateTime, endDateTime);
    }

}
