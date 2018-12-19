package xyz.zdk.UI;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import xyz.zdk.bean.FileModel;

import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by z_dk on 2018/12/16.
 */
public class Main extends HBox{
    @FXML private TextField textField;
    @FXML private Button searchBtn;
    @FXML private Button openBtn;
    @FXML private RadioButton allRadio;
    @FXML private RadioButton pdfRadio;
    @FXML private RadioButton docRadio;
    @FXML private RadioButton pptRadio;
    @FXML private RadioButton xlsRadio;
    @FXML private RadioButton txtRadio;
    @FXML private ScrollPane scrollPane;
    @FXML private Hyperlink hyperlink;
    @FXML private ContextMenu contextMenu;

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
        String fileType = null;
        if (allRadio.isSelected()){
            fileType = "all";
        }else if (pdfRadio.isSelected()){
            fileType = "pdf*";
        }else if (docRadio.isSelected()){
            fileType = "doc*";
        }else if (pptRadio.isSelected()){
            fileType = "ppt*";
        }else if (xlsRadio.isSelected()){
            fileType = "xls*";
        }else if (txtRadio.isSelected()){
            fileType = "txt";
        }
        if (text==null||"".equals(text)){
            //弹窗警告内容不能为空
            Alert warning = new Alert(Alert.AlertType.WARNING,"关键词不能为空！");
            warning.showAndWait();
        }
        else {
            scrollPane.setContent(null);
            ArrayList<FileModel> fileModels = xyz.zdk.lucene.Query.search(text,fileType);
            VBox box = new VBox();
            if (fileModels.size()==0){
                Label label = new Label("没有符合条件的结果！");
                label.setFont(Font.font(30));
                label.setTextFill(Paint.valueOf("red"));
                box.getChildren().add(label);
            }else {

                for (FileModel fileModel : fileModels) {

                    box.setSpacing(5);

                    Hyperlink hyperlink = new Hyperlink(fileModel.getTitle());
                    MenuItem menuItem = new MenuItem("打开所在文件夹");
                    menuItem.setOnAction(event -> {
                        String folder = fileModel.getPath().replaceAll(fileModel.getTitle(),"");
                        try {
                            Runtime.getRuntime().exec("explorer "+folder);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    ContextMenu contextMenu = new ContextMenu(menuItem);
                    hyperlink.setContextMenu(contextMenu);
                    //为文档名称超链接绑定事件

                    hyperlink.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {

                                String path = fileModel.getPath();
                                Runtime.getRuntime().exec("cmd /c start " + path);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    Label label = new Label(fileModel.getContent());
                    box.getChildren().addAll(hyperlink, label);

                }
            }
            scrollPane.setContent(box);
        }
    }

    @FXML
    public void showrt(ActionEvent event){

        contextMenu.show(hyperlink,90,90);

    }

    public String getTextField() {
        System.out.println(textField.getText());
        return textField.getText();
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }
}
