package xyz.zdk.UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import xyz.zdk.listener.WatchServiceTest;

import java.io.IOException;

/**
 * Created by z_dk on 2018/12/17.
 */
public class TestJavaFX extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
        primaryStage.setTitle("文件检索系统");
        primaryStage.setScene(new Scene(root,826,475));
        primaryStage.show();
    }
}
