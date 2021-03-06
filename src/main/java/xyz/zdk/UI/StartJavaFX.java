package xyz.zdk.UI;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import xyz.zdk.listener.WatchServiceListen;
import xyz.zdk.lucene.CreateIndex;

import java.io.IOException;

/**
 * Created by z_dk on 2018/12/17.
 */
public class StartJavaFX extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        primaryStage.setTitle("文件检索系统");
        primaryStage.getIcons().add(new Image("static/search.jpg"));
        primaryStage.setScene(new Scene(root,826,475));
        primaryStage.show();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                CreateIndex.create();
                return null;
            }
        };
        ProgressLoading progressLoading = new ProgressLoading(task,primaryStage);
        progressLoading.activateProgressBar();
        WatchServiceListen.startListener();
    }
}
