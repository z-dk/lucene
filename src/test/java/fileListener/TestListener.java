package fileListener;

import xyz.zdk.listener.WatchServiceListen;

import java.io.IOException;

/**
 * Created by z_dk on 2019/4/16.
 */
public class TestListener {
    public static void main(String[] args) {
        try {
            WatchServiceListen.startListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
