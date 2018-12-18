package xyz.zdk.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import xyz.zdk.bean.FileModel;
import xyz.zdk.filter.*;
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
        createIndex();
    }
    public static void createIndex(){
        //创建分词器
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
        icw.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        Directory dir = null;
        IndexWriter inWriter = null;
        Path indexPath = Paths.get("E:\\文档\\JAVA-api\\毕业-----------------------设计\\index");
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

            fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            fieldType.setStored(true);
            fieldType.setTokenized(true);
            fieldType.setStoreTermVectors(true);
            fieldType.setStoreTermVectorPositions(true);
            fieldType.setStoreTermVectorOffsets(true);

            ArrayList<FileModel> fileList = (ArrayList<FileModel>) extractFile("E:\\文档\\JAVA-api\\文档检索系统");
            // 遍历fileList,建立索引
            for (FileModel f : fileList) {
                Document doc = new Document();
                doc.add(new Field("title", f.getTitle(), fieldType));
                doc.add(new Field("content", f.getContent(), fieldType));
                doc.add(new Field("path", f.getPath(), fieldType));
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
    public static List<FileModel> extractFile(String path) throws Exception {
        List<FileModel> fileModels = new ArrayList<>();
        //首先遍历文件，判断文件类型

        List<File> fileList = traverseFolder(path);
        for (int i=0;i<fileList.size();i++){
            FileModel fileModel;
            System.out.println(fileList.get(i).getName());
            //根据文件类型调用相应的filter
            //处理pdf文件
            //接收filter返回的filemodel对象
            //封装filemodel集合
            if (fileList.get(i).getName().endsWith(".pdf")){
                fileModel = PDFFilter.extractFile(fileList.get(i));
                fileModels.add(fileModel);
            }else if (fileList.get(i).getName().endsWith(".xls")||fileList.get(i).getName().endsWith(".xlsx")){
                fileModel = ExcelFilter.extractFile(fileList.get(i));
                fileModels.add(fileModel);
            }else if (fileList.get(i).getName().endsWith(".doc")||fileList.get(i).getName().endsWith(".docx")){
                fileModel = WordFilter.extractFile(fileList.get(i));
                fileModels.add(fileModel);
            }else if (fileList.get(i).getName().endsWith(".ppt")||fileList.get(i).getName().endsWith(".pptx")){
                fileModel = PPTFilter.extractFile(fileList.get(i));
                fileModels.add(fileModel);
            }else if (fileList.get(i).getName().endsWith(".txt")){
                fileModel = TxtFilter.extractFile(fileList.get(i));
                fileModels.add(fileModel);
            }
        }
        return fileModels;
    }
    //递归遍历文件夹中所有文件与子文件夹中的文件
    public static List<File> traverseFolder(String path){
        File file = new File(path);
        List<File> fileList = new ArrayList<>();
        if (file.exists()){
            File[] files = file.listFiles();
            if (null == files || files.length == 0){
                System.out.println("文件夹为空");
            }
            else {
                for (File file1 : files){
                    if (file1.isDirectory()){
                        List<File> fileList1 = traverseFolder(file1.getAbsolutePath());
                        fileList.addAll(fileList1);
                    }
                    else {
                        fileList.add(file1);
                    }
                }
            }
        }
        return fileList;
    }
}
