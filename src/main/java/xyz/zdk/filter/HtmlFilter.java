package xyz.zdk.filter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import xyz.zdk.bean.FileModel;

import java.io.File;
import java.io.IOException;

/**
 * Created by z_dk on 2019/5/16.
 */
public class HtmlFilter {

    public static FileModel extractFile(File file){
        FileModel fileModel = null;
        try {
            fileModel = new FileModel(file.getName(),parserFile(file),file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileModel;
    }

    private static String parserFile(File file) throws IOException {
        Document document = Jsoup.parse(file,"UTF-8");
        return document.text();
    }
}
