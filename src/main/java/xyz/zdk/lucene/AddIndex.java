package xyz.zdk.lucene;

import xyz.zdk.bean.FileModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by z_dk on 2019/4/15.
 */
public class AddIndex {

    //对新增文档创建索引
    public static void addIndex(File file) throws IOException {
        Lucene lucene = new Lucene(Paths.get(Lucene.HOMEPATH));
        try {
            FileModel fileModel = Utils.parserFile(file);
            List<FileModel> fileModels = new ArrayList<>();
            fileModels.add(fileModel);
            lucene.addDocuments(fileModels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        lucene.close();
    }
}
