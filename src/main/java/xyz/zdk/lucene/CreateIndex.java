package xyz.zdk.lucene;


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
    public static String indexpath = "E:\\文档\\JAVA-api\\毕业-----------------------设计\\index";
    //创建所有文档索引
    public static void create() {
        clearFolder(new File(indexpath));
        createIndex();
    }
    //对新增文档创建索引
    public static void addDocs(ArrayList<FileModel> fileModels) throws IOException {
        Lucene lucene = new Lucene(Paths.get(indexpath));
        lucene.addDocuments(fileModels);
        lucene.close();
    }
    //删除指定文档索引(按照文档路径删除)
    public static void delete(String path) throws IOException {
        Lucene lucene = new Lucene(Paths.get(indexpath));
        lucene.delete(path);
        lucene.close();
    }
    public static void createIndex(){

        Path indexPath = Paths.get(indexpath);
        try {
            Lucene lucene = new Lucene(indexPath);
            ArrayList<FileModel> fileList = (ArrayList<FileModel>) extractFile("E:\\文档\\JAVA-api\\文档检索系统");
            lucene.addDocuments(fileList);
            lucene.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
