package xyz.zdk.lucene;


import xyz.zdk.UI.ProgressLoading;
import xyz.zdk.bean.FileModel;
import xyz.zdk.filter.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by z_dk on 2018/12/4.
 */
public class CreateIndex {

    //创建所有文档索引
    public static void create() {
        clearFolder(new File(Lucene.HOMEPATH));
        createIndex();
    }


    public static void createIndex(){

        Path indexPath = Paths.get(Lucene.HOMEPATH);
        try {
            Lucene lucene = new Lucene(indexPath);
            ArrayList<FileModel> fileList = (ArrayList<FileModel>) Utils.extractFile("E:\\文档\\JAVA-api\\文档检索系统");
            lucene.addDocuments(fileList);
            lucene.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //清空要存放索引的文件夹
    public static void clearFolder(File file){
        if (file.isFile()){
            System.out.println("要清空的路径指定错误！");
        }else {
            File [] f = file.listFiles();
            for (int i=0;i<f.length;i++){
                f[i].delete();
            }
        }
    }
}
