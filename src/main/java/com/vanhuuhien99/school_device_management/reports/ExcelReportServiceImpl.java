package com.vanhuuhien99.school_device_management.reports;

import com.vanhuuhien99.school_device_management.dto.DeviceRegistrationReportDTO;
import com.vanhuuhien99.school_device_management.dto.DeviceSummaryReportDTO;
import com.vanhuuhien99.school_device_management.entity.DeviceCategory;
import com.vanhuuhien99.school_device_management.reports.strategy.DeviceRegistrationReportStrategy;
import com.vanhuuhien99.school_device_management.reports.strategy.DeviceSummaryReportStrategy;
import com.vanhuuhien99.school_device_management.repository.DeviceCategoryRepository;
import com.vanhuuhien99.school_device_management.repository.DeviceRegistrationRepository;
import com.vanhuuhien99.school_device_management.repository.DeviceRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelReportServiceImpl implements ExcelExportService {

    private final DeviceRegistrationRepository deviceRegistrationRepository;
    private final DeviceCategoryRepository deviceCategoryRepository;
    private final DeviceRepository deviceRepository;

    private final DeviceRegistrationReportStrategy deviceRegistrationReportStrategy;
    private final DeviceSummaryReportStrategy deviceSummaryReportStrategy;

    @Override
    public void exportDeviceRegistrationReport(HttpServletResponse response) throws IOException {
        // Mặc định lấy trong vòng 30 ngày gần nhất
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();
        exportDeviceRegistrationReport(response, startDate, endDate);
    }

    @Override
    public void exportDeviceRegistrationReport(HttpServletResponse response, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        List<DeviceRegistrationReportDTO> deviceRegistrationReportDTOList = deviceRegistrationRepository
                .findDeviceRegistrationReportBetween(startDate, endDate, Pageable.unpaged())
                .getContent();

        String reportName = "BÁO CÁO MƯỢN TRẢ";
        try (Workbook workbook = deviceRegistrationReportStrategy.generateReport(deviceRegistrationReportDTOList, reportName)) {
            workbook.write(response.getOutputStream());
        }
    }

    @Override
    public void exportDeviceSummaryReport(HttpServletResponse response, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        List<DeviceSummaryReportDTO> deviceSummaryReportDTOList = new ArrayList<>();
        List<DeviceCategory> deviceCategories = deviceCategoryRepository.findAll();
        List<String> statuses = List.of("Hỏng", "Mất");

        for (var deviceCategory: deviceCategories) {
            Long categoryId = deviceCategory.getCategoryId();

            Long totalAtStartOfYear = deviceRepository.countTotalAtStartOfYear(categoryId, statuses, startDate);
            Long totalImportedDuringYear = deviceRepository.countTotalImportedDuringYear(categoryId, startDate, endDate);
            Long totalDamagedOrLostDuringYear = deviceRepository.countTotalDamagedOrLostDuringYear(categoryId, statuses, startDate, endDate);
            Long totalAtEndOfYear = deviceRepository.countTotalAtEndOfYear(categoryId, statuses, endDate);

            DeviceSummaryReportDTO report = DeviceSummaryReportDTO.builder()
                    .deviceCategoryName(deviceCategory.getCategoryName())
                    .unit(deviceCategory.getUnit())
                    .totalAtStartOfYear(totalAtStartOfYear == null ? 0 : totalAtStartOfYear)
                    .totalImportedDuringYear(totalImportedDuringYear == null ? 0 : totalImportedDuringYear)
                    .totalDamagedOrLostDuringYear(totalDamagedOrLostDuringYear == null ? 0 : totalDamagedOrLostDuringYear)
                    .totalAtEndOfYear(totalAtEndOfYear == null ? 0 : totalAtEndOfYear)
                    .description(deviceCategory.getDescription())
                    .build();
            deviceSummaryReportDTOList.add(report);
        }

        String reportName = String.format("BC TBGD %d-%d", startDate.getYear(), endDate.getYear());
        try (Workbook workbook = deviceSummaryReportStrategy.generateReport(deviceSummaryReportDTOList, reportName)) {
            workbook.write(response.getOutputStream());
        }
    }
}
