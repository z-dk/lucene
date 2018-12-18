package xyz.zdk.UI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import xyz.zdk.bean.FileModel;
import xyz.zdk.ikanalyzer.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * Created by z_dk on 2018/12/16.
 */
public class Main extends HBox{
    @FXML private TextField textField;
    @FXML private Button searchBtn;
    @FXML private Button openBtn;
    @FXML private VBox vBox;

    @FXML
    public void openFolder(){
        try {
            Runtime.getRuntime().exec("explorer E:\\文档\\JAVA-api\\文档检索系统");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void search(){
        String text = getTextField();
        if (text==null||"".equals(text)){
            //弹窗警告内容不能为空
            Alert warning = new Alert(Alert.AlertType.WARNING,"关键词不能为空！");
            warning.showAndWait();
        }
        else {
            ArrayList<FileModel> fileModels = xyz.zdk.lucene.Query.search(text);
            for(FileModel fileModel:fileModels){

                VBox box = new VBox();
                box.setSpacing(5);

                Hyperlink hyperlink = new Hyperlink(fileModel.getTitle());
                //为文档名称超链接绑定事件
                hyperlink.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            String path = fileModel.getPath();
                            Runtime.getRuntime().exec("cmd /c start "+path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Label label = new Label(fileModel.getContent());
                box.getChildren().addAll(hyperlink,label);
                vBox.getChildren().add(box);
            }
        }
    }

    @FXML
    public void openFile(){

    }

    public String getTextField() {
        System.out.println(textField.getText());
        return textField.getText();
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }
}
