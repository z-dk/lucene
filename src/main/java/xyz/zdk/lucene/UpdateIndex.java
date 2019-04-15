package xyz.zdk.lucene;

import org.apache.lucene.document.Document;
import xyz.zdk.bean.FileModel;
import xyz.zdk.filter.*;

import java.io.File;
import java.nio.file.Paths;

/**
 * Created by z_dk on 2019/2/22.
 */
public class UpdateIndex {

    public static void update(File file) throws Exception {
        Lucene lucene = new Lucene(Paths.get(Lucene.HOMEPATH));
        FileModel fileModel = Utils.parserFile(file);
        if (fileModel != null){
            Document document = lucene.setDocument(fileModel);
            lucene.update(fileModel.getPath(),document);
        } else{
            System.out.println("更新失败");
        }
        lucene.close();
    }
}
