package xyz.zdk.filter;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import xyz.zdk.bean.FileModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by z_dk on 2018/12/6.
 */
public class ExcelFilter {
    public static FileModel extractFile(File file){
        FileModel fileModel = new FileModel(file.getName(),parserFile(file),file.getAbsolutePath());
        return fileModel;
    }
    public static String parserFile(File file){
        String content = "";
        List<String> stringList = new ArrayList<>();
        if (!file.exists()){
            return content;
        }
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            if (file.getName().endsWith(".xls")){
                HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
                for (int i=0;i<workbook.getNumberOfSheets();i++){
                    HSSFSheet sheet = workbook.getSheetAt(i);
                    for (int j=0;j<sheet.getLastRowNum();j++){
                        HSSFRow row = sheet.getRow(j);
                        if (row==null){
                            continue;
                        }
                        for (int k=0;k<row.getLastCellNum();k++){
                            HSSFCell cell = row.getCell(k);
                            if (cell!=null){
                                cell.setCellType(CellType.STRING);
                                stringList.add(cell.getRichStringCellValue().getString());
                            }
                        }
                    }
                }
            }else if (file.getName().endsWith(".xlsx")){
                Workbook workbook = new XSSFWorkbook(fileInputStream);
                for (int i=0;i<workbook.getNumberOfSheets();i++){
                    Sheet sheet = workbook.getSheetAt(i);
                    if (null!=sheet){
                        for (int j=sheet.getFirstRowNum();j<sheet.getLastRowNum();j++){
                            Row row = sheet.getRow(j);
                            if (row!=null){
                                for (int k=row.getFirstCellNum();k<row.getLastCellNum();k++){
                                    Cell cell = row.getCell(k);
                                    if (cell!=null&&cell.toString()!=" "){
                                        stringList.add(cell.toString());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            content = String.join(",",stringList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
