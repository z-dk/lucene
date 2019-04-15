package xyz.zdk.lucene;

import xyz.zdk.bean.FileModel;
import xyz.zdk.filter.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by z_dk on 2019/2/22.
 */
public class Utils {

    public static FileModel parserFile(File file) throws Exception {
        FileModel fileModel;
        if (file.getName().endsWith(".pdf")){
            fileModel = PDFFilter.extractFile(file);
            return fileModel;
        }else if (file.getName().endsWith(".xls")||file.getName().endsWith(".xlsx")){
            fileModel = ExcelFilter.extractFile(file);
            return fileModel;
        }else if (file.getName().endsWith(".doc")||file.getName().endsWith(".docx")){
            fileModel = WordFilter.extractFile(file);
            return fileModel;
        }else if (file.getName().endsWith(".ppt")||file.getName().endsWith(".pptx")){
            fileModel = PPTFilter.extractFile(file);
            return fileModel;
        }else if (file.getName().endsWith(".txt")){
            fileModel = TxtFilter.extractFile(file);
            return fileModel;
        }
        return null;
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
