package com.example.shopmanagement.ExcelExport;

import com.example.shopmanagement.model.Shop;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExportHelper {
    public static String[] HEADERS = {
            "id",
            "ownername",
            "shoptype",
            "addres",
            "country"

    };

    public static String SHEET_NAME ="Shop_Data";

    public static ByteArrayInputStream DataToExcel(List<Shop> list){
        // crteate a workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            // create a sheet because a workbook will contain many sheets
            Sheet sheet = workbook.createSheet(SHEET_NAME);


            // Create the Header rows
            Row row = sheet.createRow(0);
            for (int i=0 ; i<HEADERS.length;i++){
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            // To create the Value rows
            int rowIndex = 1;
            for (Shop shop : list){
                Row dataRow = sheet.createRow(rowIndex);
                rowIndex++;
                dataRow.createCell(0).setCellValue(shop.getId());
                dataRow.createCell(1).setCellValue(shop.getOwnername());
                dataRow.createCell(2).setCellValue(shop.getShoptype());
                dataRow.createCell(3).setCellValue(shop.getAdress());
                dataRow.createCell(4).setCellValue(shop.getCountry());

            }

            workbook.write(out);







        }catch (IOException e){
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());

    }




}