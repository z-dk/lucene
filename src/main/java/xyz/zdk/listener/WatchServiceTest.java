package xyz.zdk.listener;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * Created by z_dk on 2019/1/31.
 */
public class WatchServiceTest {
    public static void startListener() throws IOException {
        // 需要监听的文件目录（只能监听目录）
        String path = "E:\\文档\\JAVA-api\\文档检索系统";

        WatchService watchService = FileSystems.getDefault().newWatchService();
        //遍历目录，为所有文件夹添加监听
        FileORDir.folderORFile(path,watchService);

        Thread thread = new Thread(() -> {
            try {
                while(true){
                    WatchKey watchKey = watchService.take();
                    List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                    for(WatchEvent<?> event : watchEvents){
                        Path paths = (Path) watchKey.watchable();
                        File file = new File(paths.toAbsolutePath().toString()+"\\"+event.context());
                        System.out.println("["+file.getAbsolutePath().toString()+"]文件发生了["+event.kind()+"]事件");
                        //
                        if (event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)){
                            //对创建的新目录添加监听
                            if (file.isDirectory())
                                FileORDir.folderORFile(file.getAbsolutePath().toString(),watchService);
                            else if (file.isFile()){
                                //对新建文件进行解析，更新索引
                            }
                        }
                    }
                    watchKey.reset();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(false);
        thread.start();

        // 增加jvm关闭的钩子来关闭监听
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                watchService.close();
            } catch (Exception e) {
            }
        }));
    }
}
