package xyz.zdk.filter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
        FileModel fileModel = new FileModel(file.getName(),parserFile(file));
        return fileModel;
    }
    public static String parserFile(File file){
        String content = "";
        List<String> stringList = new ArrayList<>();
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            for (int i=sheet.getFirstRowNum();i<sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                if (row!=null){
                    for (int j=row.getFirstCellNum();j<row.getLastCellNum();j++){
                        Cell cell = row.getCell(j);
                        if (cell!=null&&cell.toString()!=" "){
                            stringList.add(cell.toString());
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
