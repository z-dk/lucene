package lucene;

import xyz.zdk.lucene.CreateIndex;
import xyz.zdk.lucene.UpdateIndex;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by z_dk on 2019/2/22.
 */
public class TestLucene {
    public static void main(String[] args) throws Exception {
        File file = new File("E:\\文档\\JAVA-api\\文档检索系统\\毕业设计任务书-----朱登奎.doc");
        //UpdateIndex.update(file);
        //CreateIndex.createIndex();
        CreateIndex.delete("毕业设计任务书-----朱登奎.doc");
    }
}
