package parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by z_dk on 2019/5/18.
 */
public class Htmlparser {
    public static void main(String[] args) {
        htmlTest();
    }

    public static void htmlTest() {
        try {
            Document document = Jsoup.parse(new File("E:\\文档\\JAVA-api\\文档检索系统\\子文件夹\\春.html"),"UTF-8");

            System.out.println(document.text());
            /*Elements allElements = document.getAllElements();
            for (Element e:allElements) {
                System.out.println(e.text());
            }*/

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
