package xyz.zdk.filter;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import xyz.zdk.bean.FileModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by z_dk on 2018/11/28.
 */
public class PDFFilter {

    //获取文件的FileModel的list集合
    public static FileModel extractFile(File f) throws Exception {

        FileModel sf = new FileModel(f.getName(), parserFile(f),f.getAbsolutePath());
        return sf;
    }

    public static String parserFile(File file){
        String fileContent = null;
        PDDocument pdDocument = null;
        try {
            pdDocument = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();
            fileContent = stripper.getText(pdDocument);
            pdDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
