package parsers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by z_dk on 2018/12/6.
 */
public class EXCELparser {
    public static void main(String[] args) {
        File file = new File("E:\\文档\\JAVA api\\文档检索系统\\创新学分班级汇总表.xlsx");
        try {
            getEXCELtext(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void getEXCELtext(File file) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0);
            int firstRowIndex = sheet.getFirstRowNum();
            int lastRowIndex = sheet.getLastRowNum();
            for (int i=firstRowIndex;i<lastRowIndex;i++){
                Row row = sheet.getRow(i);
                if (row!=null){
                    for (int j=row.getFirstCellNum();j<row.getLastCellNum();j++){
                        Cell cell = row.getCell(j);
                        if (cell!=null){
                            System.out.print(" "+cell.toString());
                        }
                    }
                    System.out.println("");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
