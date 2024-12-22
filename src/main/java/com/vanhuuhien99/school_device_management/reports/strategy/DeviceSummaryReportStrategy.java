package com.vanhuuhien99.school_device_management.reports.strategy;

import com.vanhuuhien99.school_device_management.dto.DeviceSummaryReportDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeviceSummaryReportStrategy implements ExcelReportStrategy<DeviceSummaryReportDTO> {

    private static final int COLUMN_INDEX_CATEGORY_NAME = 0;
    private static final int COLUMN_INDEX_UNIT = 1;
    private static final int COLUMN_INDEX_TOTAL_AT_START_OF_YEAR = 2;
    private static final int COLUMN_INDEX_TOTAL_IMPORTED_DURING_YEAR = 3;
    private static final int COLUMN_INDEX_TOTAL_DAMAGED_OR_LOST_DURING_YEAR = 4;
    private static final int COLUMN_INDEX_TOTAL_AT_END_OF_YEAR = 5;
    private static final int COLUMN_INDEX_DESCRIPTION = 6;

    @Override
    public Workbook generateReport(List<DeviceSummaryReportDTO> data, String reportName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(reportName);

        int rowIndex = 0;
        writeHeaderRow(sheet, rowIndex);

        rowIndex++;
        for(DeviceSummaryReportDTO reportDTO: data) {
            Row row = sheet.createRow(rowIndex);
            writeDataRow(reportDTO, row);
            rowIndex++;
        }

        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autoResizeColumn(sheet, numberOfColumn);

        return workbook;
    }

    private void writeHeaderRow(Sheet sheet, int rowIndex) {
        CellStyle headerStyle = createStyleForHeader(sheet);
        Row header = sheet.createRow(rowIndex);

        Cell cell = header.createCell(COLUMN_INDEX_CATEGORY_NAME);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Tên loại thiết bị");

        cell = header.createCell(COLUMN_INDEX_UNIT);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Đơn vị tính");

        cell = header.createCell(COLUMN_INDEX_TOTAL_AT_START_OF_YEAR);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Tổng SL đầu năm");

        cell = header.createCell(COLUMN_INDEX_TOTAL_IMPORTED_DURING_YEAR);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("SL nhập trong năm");

        cell = header.createCell(COLUMN_INDEX_TOTAL_DAMAGED_OR_LOST_DURING_YEAR);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("SL hư hỏng/mất");

        cell = header.createCell(COLUMN_INDEX_TOTAL_AT_END_OF_YEAR);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Tổng SL cuối năm");

        cell = header.createCell(COLUMN_INDEX_DESCRIPTION);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Ghi chú");
    }

    private void writeDataRow(DeviceSummaryReportDTO reportDTO, Row row) {
        row.createCell(COLUMN_INDEX_CATEGORY_NAME).setCellValue(reportDTO.getDeviceCategoryName());
        row.createCell(COLUMN_INDEX_UNIT).setCellValue(reportDTO.getUnit());
        row.createCell(COLUMN_INDEX_TOTAL_AT_START_OF_YEAR).setCellValue(reportDTO.getTotalAtStartOfYear());
        row.createCell(COLUMN_INDEX_TOTAL_IMPORTED_DURING_YEAR).setCellValue(reportDTO.getTotalImportedDuringYear());
        row.createCell(COLUMN_INDEX_TOTAL_DAMAGED_OR_LOST_DURING_YEAR).setCellValue(reportDTO.getTotalDamagedOrLostDuringYear());
        row.createCell(COLUMN_INDEX_TOTAL_AT_END_OF_YEAR).setCellValue(reportDTO.getTotalAtEndOfYear());
        row.createCell(COLUMN_INDEX_DESCRIPTION).setCellValue(reportDTO.getDescription());
    }

    private CellStyle createStyleForHeader(Sheet sheet) {
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    // Auto resize column width
    private static void autoResizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
}
