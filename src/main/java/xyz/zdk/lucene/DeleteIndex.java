package xyz.zdk.lucene;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Created by z_dk on 2019/4/15.
 */
public class DeleteIndex {
    //删除指定文档索引(按照文档路径删除)
    public static void delete(String path) throws IOException {
        Lucene lucene = new Lucene(Paths.get(Lucene.HOMEPATH));
        lucene.delete(path);
        lucene.close();
    }
}
