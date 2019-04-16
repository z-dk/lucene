package lucene;

import xyz.zdk.lucene.DeleteIndex;

import java.io.IOException;

/**
 * Created by z_dk on 2019/4/16.
 */
public class TestDeleAll {
    public static void main(String[] args) {
        try {
            DeleteIndex.delete("E:\\文档\\JAVA-api\\文档检索系统\\子文件夹2");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
