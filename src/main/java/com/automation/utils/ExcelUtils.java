package com.automation.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Utility class for reading Excel files (XLSX) for test cases and data
 */
public class ExcelUtils {
    private String filePath;
    private Workbook workbook;

    public ExcelUtils(String filePath) throws IOException {
        this.filePath = filePath;
        FileInputStream fis = new FileInputStream(filePath);
        this.workbook = new XSSFWorkbook(fis);
    }

    public List<List<String>> getSheetData(String sheetName) {
        List<List<String>> data = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return data;
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                cell.setCellType(CellType.STRING);
                rowData.add(cell.getStringCellValue());
            }
            data.add(rowData);
        }
        return data;
    }

    public List<String> getSheetHeaders(String sheetName) {
        List<String> headers = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) return headers;
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) return headers;
        for (Cell cell : headerRow) {
            cell.setCellType(CellType.STRING);
            headers.add(cell.getStringCellValue());
        }
        return headers;
    }

    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }
} 