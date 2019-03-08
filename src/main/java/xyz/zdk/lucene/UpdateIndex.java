package xyz.zdk.lucene;

import org.apache.lucene.document.Document;
import xyz.zdk.bean.FileModel;
import xyz.zdk.filter.*;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by z_dk on 2019/2/22.
 */
public class UpdateIndex {

    public static void update(File file) throws Exception {
        Lucene lucene = new Lucene(Paths.get("E:\\文档\\JAVA-api\\毕业-----------------------设计\\index"));
        FileModel fileModel = parserFile(file);
        if (fileModel != null){
            Document document = lucene.setDocument(fileModel);
            lucene.update(fileModel.getPath(),document);
        } else{
            System.out.println("更新失败");
        }
        lucene.close();
    }

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
