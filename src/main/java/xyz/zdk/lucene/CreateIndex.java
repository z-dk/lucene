package xyz.zdk.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import xyz.zdk.bean.FileModel;
import xyz.zdk.ikanalyzer.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by z_dk on 2018/12/4.
 */
public class CreateIndex {
    public static void main(String[] args) {

    }
    public static void createIndex(){
        //创建分词器
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
        icw.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        Directory dir = null;
        IndexWriter inWriter = null;
        Path indexPath = Paths.get("E:\\文档\\JAVA api\\毕业-----------------------设计\\index");
        Date start = new Date();
        try {
            if (!Files.isReadable(indexPath)){
                System.out.println("Document directory '"+indexPath.toAbsolutePath()+"' does not exist or is" +
                        " not readable,please check the path");
                System.exit(1);
            }
            dir = FSDirectory.open(indexPath);
            inWriter = new IndexWriter(dir,icw);
            FieldType fieldType = new FieldType();

            ArrayList<FileModel> fileList = (ArrayList<FileModel>) extractFile();
            // 遍历fileList,建立索引
            for (FileModel f : fileList) {
                Document doc = new Document();
                doc.add(new Field("title", f.getTitle(), fieldType));
                doc.add(new Field("content", f.getContent(), fieldType));
                inWriter.addDocument(doc);
            }
            inWriter.commit();
            inWriter.close();
            dir.close();
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date end = new Date();
        System.out.println("索引文档用时："+(end.getTime()-start.getTime())+"毫秒");
    }
    public static List<FileModel> extractFile(){
        List<FileModel> fileModels = new ArrayList<>();
        //首先遍历文件，判断文件类型
        //根据文件类型调用相应的filter
        //接收filter返回的filemodel对象
        //封装filemodel集合
        return fileModels;
    }
}
