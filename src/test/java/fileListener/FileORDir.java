package fileListener;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchService;
import java.util.LinkedList;

/**
 * Created by z_dk on 2019/1/31.
 */
public class FileORDir {
    //递归遍历文件夹中所有文件与子文件夹中的文件
    public static void folderORFile(String path, WatchService watchService) throws IOException {

        Path p = Paths.get(path);
        p.register(watchService,
                StandardWatchEventKinds.ENTRY_MODIFY,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_CREATE);
        System.out.println("监听了："+path);

        File file = new File(path);
        LinkedList<File> fList = new LinkedList<File>();
        fList.addLast(file);
        while (fList.size() > 0 ) {
            File f = fList.removeFirst();
            if(f.listFiles() == null)
                continue;
            for(File file2 : f.listFiles()){
                if (file2.isDirectory()){//下一级目录
                    //fList.addLast(file2);
                    //依次注册子目录
                    folderORFile(file2.getAbsolutePath(),watchService);
                }
            }
        }
        //依次遍历所有子文件夹，并对其监听
        /*if (file.exists()){
            File[] files = file.listFiles();
            if (null == files || files.length == 0){
                System.out.println("文件夹为空");
            }
            else {
                for (File file1 : files){
                    if (file1.isDirectory()){
                        folderORFile(file1.getAbsolutePath(),watchService);
                    }
                }
            }
        }*/
    }
}
