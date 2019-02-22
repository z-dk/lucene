package lucene;

import xyz.zdk.bean.FileModel;
import xyz.zdk.filter.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by z_dk on 2019/2/22.
 */
public class TestLucene {
    public static void main(String[] args) throws IOException {
        Path indexPath = Paths.get("E:\\文档\\JAVA-api\\毕业-----------------------设计\\index");
        Lucene lucene = new Lucene(indexPath);

        try {
            //lucene.addDocuments(fileList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
