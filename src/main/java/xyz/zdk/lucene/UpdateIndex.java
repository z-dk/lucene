package xyz.zdk.lucene;

import xyz.zdk.bean.FileModel;
import xyz.zdk.filter.*;

import java.io.File;

/**
 * Created by z_dk on 2019/2/22.
 */
public class UpdateIndex {



    public static FileModel parserFile(File file) throws Exception {
        FileModel fileModel;
        if (file.getName().endsWith(".pdf")){
            fileModel = PDFFilter.extractFile(file);
            return fileModel;
        }else if (file.getName().endsWith(".xls")||file.getName().endsWith(".xlsx")){
            fileModel = ExcelFilter.extractFile(file);
            return fileModel;
        }else if (file.getName().endsWith(".doc")||file.getName().endsWith(".docx")){
            fileModel = WordFilter.extractFile(file);
            return fileModel;
        }else if (file.getName().endsWith(".ppt")||file.getName().endsWith(".pptx")){
            fileModel = PPTFilter.extractFile(file);
            return fileModel;
        }else if (file.getName().endsWith(".txt")){
            fileModel = TxtFilter.extractFile(file);
            return fileModel;
        }
        return null;
    }
}
