package org.example;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.util.*;

public class ExcelExporter {

    public static void exportToExcel(List<Map<String, Object>> data,
                                     List<String> headers,
                                     String filePath) throws Exception {
        // Проверка на пустые данные
        if (data == null || headers == null) {
            throw new IllegalArgumentException("Data and headers cannot be null");
        }

        // Создаем книгу Excel
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Данные");

        // Создаем стили
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle dataStyle = createDataStyle(workbook);

        // Создаем строку заголовков
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(headerStyle);
        }

        // Заполняем данные
        for (int rowNum = 0; rowNum < data.size(); rowNum++) {
            Row row = sheet.createRow(rowNum * 2 + 1); // +1 чтобы пропустить заголовки
            Map<String, Object> rowData = data.get(rowNum);

            for (int colNum = 0; colNum < headers.size(); colNum++) {
                String header = headers.get(colNum);
                Object value = rowData.get(header);

                Cell cell = row.createCell(colNum);
                setCellValue(cell, value, dataStyle);
            }
        }

        // Автонастройка ширины столбцов
        for (int i = 0; i < headers.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Сохраняем файл
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

    private static CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setColor(IndexedColors.BLACK.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static CellStyle createDataStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    private static void setCellValue(Cell cell, Object value, CellStyle style) {
        cell.setCellStyle(style);

        switch (value) {
            case null -> cell.setCellValue("");
            case String string -> cell.setCellValue(string);
            case Number number -> cell.setCellValue(number.doubleValue());
            case Boolean b -> cell.setCellValue(b);
            case Date date -> cell.setCellValue(date);
            default -> cell.setCellValue(value.toString());
        }
    }
}