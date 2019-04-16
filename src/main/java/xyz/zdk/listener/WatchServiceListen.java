package xyz.zdk.listener;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * Created by z_dk on 2019/1/31.
 */
public class WatchServiceListen {
    public static void startListener() throws IOException {
        // 需要监听的文件目录（只能监听目录）
        String path = "E:\\文档\\JAVA-api\\文档检索系统";

        WatchService watchService = FileSystems.getDefault().newWatchService();
        //遍历目录，为所有文件夹添加监听
        FileORDir.folderORFile(path,watchService);

        Thread thread = new Thread(() -> {
            try {
                out:while(true){
                    WatchKey watchKey = watchService.take();
                    List<WatchEvent<?>> watchEvents = watchKey.pollEvents();
                    in:for(WatchEvent<?> event : watchEvents){
                        Path paths = (Path) watchKey.watchable();
                        File file = new File(paths.toAbsolutePath().toString()+"\\"+event.context());
                        if (file.getName().startsWith("~$")||file.getName().startsWith("~WR")
                                ||file.getName().endsWith(".tmp")){
                            continue in;
                        }
                        System.out.println("["+file.getAbsolutePath().toString()+"]文件发生了["
                                +event.kind()+"]事件");
                        //根据文件的变化情况，被动执行相应的方法更新索引
                        FileUpdateIndex.excuteUpdateIndex(event,watchService,file);
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
