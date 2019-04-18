package xyz.zdk.filter;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import xyz.zdk.bean.FileModel;

import java.io.*;

/**
 * Created by z_dk on 2018/12/7.
 */
public class WordFilter {
    public static FileModel extractFile(File file){
        FileModel fileModel = new FileModel(file.getName(),parserFile(file),file.getAbsolutePath());
        return fileModel;
    }

    public static String parserFile(File file){
        String content = "";
        if (!file.exists()){
            return content;
        }
        try {
            if (file.getName().endsWith(".doc")){
                InputStream inputStream = new FileInputStream(file);
                WordExtractor extractor = new WordExtractor(inputStream);
                content = extractor.getText();
                extractor.close();
            }else if (file.getName().endsWith(".docx")){
                InputStream inputStream = new FileInputStream(file);
                XWPFDocument document = new XWPFDocument(inputStream);
                XWPFWordExtractor extractor = new XWPFWordExtractor(document);
                content = extractor.getText();
                extractor.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
