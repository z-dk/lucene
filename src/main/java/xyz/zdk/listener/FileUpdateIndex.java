package xyz.zdk.listener;

import xyz.zdk.lucene.AddIndex;
import xyz.zdk.lucene.DeleteIndex;
import xyz.zdk.lucene.UpdateIndex;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;

/**
 * Created by z_dk on 2019/4/16.
 */
public class FileUpdateIndex {
    public static void excuteUpdateIndex(WatchEvent<?> event, WatchService watchService,File file) throws IOException {
        // 如果文件发生了新增事件，则执行新增方法
        if (event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)){
            //对创建的新目录添加监听
            if (file.isDirectory())
                FileORDir.folderORFile(file.getAbsolutePath().toString(),watchService);
            else if (file.isFile()){
                //对新建文件进行解析，新增索引
                try {
                    AddIndex.addIndex(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 如果文件发生了删除事件，则执行删除方法
        // 问题：文件或文件夹已被删除，无法通过file判断是目录还是文件
        // 思路：如果是目录的话，获取所有索引的文件路径，删除包含该目录的文件索引
        // 方案：删除采用通配符*，无论是目录还是文件，都可以删除
        else if (event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)){

            try {
                DeleteIndex.delete(file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        // 如果文件发生了修改事件，则执行修改方法
        else if (event.kind().equals(StandardWatchEventKinds.ENTRY_MODIFY)){
            //目录发生修改
            if (file.isDirectory()){
                //目录的修改事件
            }
            //文件发生修改
            else if (file.isFile()){
                //对文件进行更新索引
                try {
                    UpdateIndex.update(file);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
