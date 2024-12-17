package com.vanhuuhien99.school_device_management.reports;

import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationReport;
import com.vanhuuhien99.school_device_management.reports.strategy.DeviceRegistrationReportStrategy;
import com.vanhuuhien99.school_device_management.repository.DeviceRegistrationRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelReportServiceImpl implements ExcelExportService {

    private final DeviceRegistrationRepository deviceRegistrationRepository;

    private final DeviceRegistrationReportStrategy deviceRegistrationReportStrategy;

    @Override
    public void exportDeviceRegistrationReport(HttpServletResponse response) throws IOException {
        // Mặc định lấy trong vòng 30 ngày gần nhất
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();
        exportDeviceRegistrationReport(response, startDate, endDate);
    }

    @Override
    public void exportDeviceRegistrationReport(HttpServletResponse response, LocalDateTime startDate, LocalDateTime endDate) throws IOException {
        List<DeviceRegistrationReport> deviceRegistrationReportList = deviceRegistrationRepository
                .findDeviceRegistrationReportBetween(startDate, endDate, Pageable.unpaged())
                .getContent();

        try (Workbook workbook = deviceRegistrationReportStrategy.generateReport(deviceRegistrationReportList)) {
            workbook.write(response.getOutputStream());
        }
    }
}
