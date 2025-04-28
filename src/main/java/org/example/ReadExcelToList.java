package org.example;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class ReadExcelToList {
    public static List<List<String>> readExcelToList(String filePath) {
        List<List<String>> data = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new HSSFWorkbook(fis)) { // XSSF для .xlsx, HSSF для .xls

            Sheet sheet = workbook.getSheetAt(0); // Первый лист

            for (Row row : sheet) {
                List<String> rowData = getStrings(row);
                data.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private static List<String> getStrings(Row row) {
        List<String> rowData = new ArrayList<>();

        for (Cell cell : row) {
            switch (cell.getCellType()) {
                case STRING:
                    rowData.add(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    rowData.add(String.valueOf(cell.getNumericCellValue()));
                    break;
                case BOOLEAN:
                    rowData.add(String.valueOf(cell.getBooleanCellValue()));
                    break;
                default:
                    rowData.add("");
            }
        }
        return rowData;
    }
}
