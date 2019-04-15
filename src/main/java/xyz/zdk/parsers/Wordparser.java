package xyz.zdk.parsers;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

/**
 * Created by z_dk on 2018/12/7.
 */
public class Wordparser {
    public static void main(String[] args) {
        getWordtext(new File("E:\\文档\\JAVA api\\文档检索系统\\任务详细划分.docx"));
    }
    public static void getWordtext(File file){
        try {
            if (file.getName().endsWith(".doc")){
                InputStream inputStream = new FileInputStream(file);
                WordExtractor extractor = new WordExtractor(inputStream);
                System.out.println(extractor.getText());
                extractor.close();
            }else if (file.getName().endsWith(".docx")){
                InputStream inputStream = new FileInputStream(file);
                XWPFDocument document = new XWPFDocument(inputStream);
                XWPFWordExtractor extractor = new XWPFWordExtractor(document);
                System.out.println(extractor.getText());
                extractor.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
