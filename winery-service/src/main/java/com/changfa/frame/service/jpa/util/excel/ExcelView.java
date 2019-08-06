package com.changfa.frame.service.jpa.util.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ExcelView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExcelSheetSettingEnum setting = (ExcelSheetSettingEnum) map.get("ExcelSheetSetting");

        // 设置文件名称
        String filename = setting.getFilename();
        filename = new String(filename.getBytes("UTF-8"),"ISO8859-1");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=" + filename + ".xlsx");

        List<List<List<String>>> sheets = (List<List<List<String>>>) map.get("data");
        for (int i = 0; i < sheets.size(); i++) {
            // 创建sheet
            String[] sheetNames = setting.getSheetnames();
            String sheetName = "Sheet" + (i + 1);
            if (sheetNames != null && sheetNames.length > 0) {
                sheetName = sheetNames[i];
            }
            Sheet sheet = workbook.createSheet(sheetName);

            // 如果标题不为空的话，将表格的第一行作为标题行，并合并第一行的N个单元格
            int index = 0;
            String[] titles = setting.getTitles();
            String[][] headerss = setting.getHeaders();
            List<List<String>> rowsForTable = sheets.get(i);
            if (titles != null && titles.length > 0) {
                // 合并标题单元格 下标从0开始 起始行号，终止行号， 起始列号，终止列号
                CellRangeAddress region = new CellRangeAddress(0, 0, 0, rowsForTable.get(0).size() - 1);
                sheet.addMergedRegion(region);

                Row titleRow = sheet.createRow(index++);
                Cell titleCell = titleRow.createCell(0);
                CellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                Font font = workbook.createFont();
                font.setFontName("黑体");
                font.setBold(true);
                font.setFontHeightInPoints((short) 15);
                cellStyle.setFont(font);
                titleCell.setCellStyle(cellStyle);
                titleCell.setCellValue(titles[i]);
            }

            // 创建表头行
            if (headerss != null && headerss.length > 0) {
                Row headerRow = sheet.createRow(index++);
                String[] headers = headerss[i];
                for(int j = 0; j < headers.length; j++) {
                    headerRow.createCell(j).setCellValue(headers[j]);
                }
            }

            // 创建数据行
            AtomicInteger rowIndex = new AtomicInteger(index);
            rowsForTable.forEach(rowList -> {
                Row row = sheet.createRow(rowIndex.getAndIncrement());
                AtomicInteger x = new AtomicInteger();
                rowList.forEach(cell ->
                        row.createCell(x.getAndIncrement()).setCellValue(cell)
                );
            });
        }
    }
}
