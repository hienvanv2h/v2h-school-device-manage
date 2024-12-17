package com.vanhuuhien99.school_device_management.reports.strategy;

import com.vanhuuhien99.school_device_management.projection.DeviceRegistrationReport;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeviceRegistrationReportStrategy implements ExcelReportStrategy<DeviceRegistrationReport> {

    private static final int COLUMN_INDEX_REGISTER_DATE = 0;
    private static final int COLUMN_INDEX_DEVICE_NAME = 1;
    private static final int COLUMN_INDEX_TEACHER_NAME = 2;
    private static final int COLUMN_INDEX_CLASS_NAME = 3;
    private static final int COLUMN_INDEX_SCHEDULE_TIME = 4;
    private static final int COLUMN_INDEX_RETURN_DATE = 5;
    private static final int COLUMN_INDEX_DESCRIPTION = 6;

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String DATE_TIME_FORMAT = "HH:mm dd/MM/yyyy";

    @Override
    public Workbook generateReport(List<DeviceRegistrationReport> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("BÁO CÁO MƯỢN TRẢ");

        // Tạo cell style
        CellStyle dateCellStyle = createCellStyle(workbook, DATE_FORMAT);
        CellStyle dateTimeCellStyle = createCellStyle(workbook, DATE_TIME_FORMAT);

        int rowIndex = 0;
        writeHeaderRow(sheet, rowIndex);

        rowIndex++;
        for(DeviceRegistrationReport dcSummary: data) {
            Row row = sheet.createRow(rowIndex);
            writeDataRow(dcSummary, row, dateCellStyle, dateTimeCellStyle);
            rowIndex++;
        }

        int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
        autoResizeColumn(sheet, numberOfColumn);

        return workbook;
    }

    private void writeHeaderRow(Sheet sheet, int rowIndex) {
        CellStyle headerStyle = createStyleForHeader(sheet);
        Row header = sheet.createRow(rowIndex);

        Cell cell = header.createCell(COLUMN_INDEX_REGISTER_DATE);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Ngày đăng ký");

        cell = header.createCell(COLUMN_INDEX_DEVICE_NAME);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Tên TBGD hoặc hóa chất được sử dụng");

        cell = header.createCell(COLUMN_INDEX_TEACHER_NAME);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Giáo viên");

        cell = header.createCell(COLUMN_INDEX_CLASS_NAME);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Khối lớp");

        cell = header.createCell(COLUMN_INDEX_SCHEDULE_TIME);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Ca/tiết mượn");

        cell = header.createCell(COLUMN_INDEX_RETURN_DATE);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Ngày trả");

        cell = header.createCell(COLUMN_INDEX_DESCRIPTION);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Ghi chú");
    }

    private void writeDataRow(DeviceRegistrationReport summary, Row row, CellStyle dateCellStyle, CellStyle dateTimeCellStyle) {
        Cell cell = row.createCell(COLUMN_INDEX_REGISTER_DATE);
        if(summary.getRegistrationDate() != null) {
            cell.setCellValue(summary.getRegistrationDate());
            cell.setCellStyle(dateTimeCellStyle);
        }

        row.createCell(COLUMN_INDEX_DEVICE_NAME).setCellValue(summary.getDeviceName());
        row.createCell(COLUMN_INDEX_TEACHER_NAME).setCellValue(summary.getTeacherName());
        row.createCell(COLUMN_INDEX_CLASS_NAME).setCellValue(summary.getClassName());

        cell = row.createCell(COLUMN_INDEX_SCHEDULE_TIME);
        cell.setCellValue(summary.getScheduleTime());

        cell = row.createCell(COLUMN_INDEX_RETURN_DATE);
        if(summary.getReturnDate() != null) {
            cell.setCellValue(summary.getReturnDate());
            cell.setCellStyle(dateCellStyle);
        }

        cell = row.createCell(COLUMN_INDEX_DESCRIPTION);
        cell.setCellValue(summary.getDescription());
    }

    private CellStyle createStyleForHeader(Sheet sheet) {
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    private CellStyle createCellStyle(Workbook workbook, String format) {
        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(creationHelper.createDataFormat().getFormat(format));
        return cellStyle;
    }

    // Auto resize column width
    private static void autoResizeColumn(Sheet sheet, int lastColumn) {
        for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
}
