package xyz.zdk.UI;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import xyz.zdk.bean.FileModel;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

                    MenuItem menuItem = new MenuItem("打开文件所在位置");
                    menuItem.setOnAction(event -> {
                        int last = fileModel.getPath().lastIndexOf("\\");
                        String folder = fileModel.getPath().substring(0,last);
                        try {
                            Runtime.getRuntime().exec("explorer "+folder);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    ContextMenu contextMenu = new ContextMenu(menuItem);

                    HBox hBox = new HBox();
                    String[] strings1 = fileModel.getTitle().split("<span>|</span>");
                    for (int i=0;i<strings1.length;i++){
                        Label label = new Label(strings1[i]);
                        label.setFont(Font.font(20));
                        if (i%2==1){
                            label.setTextFill(Color.web("red"));
                        }else{
                            label.setTextFill(Color.web("blue"));
                        }
                        label.setContextMenu(contextMenu);
                        hBox.getChildren().add(label);
                    }

                    //为文档名称超链接绑定事件

                    hBox.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
                        @Override
                        public void handle(javafx.scene.input.MouseEvent event) {
                            if (event.getButton().equals(MouseButton.PRIMARY)){
                                try {

                                    String path = fileModel.getPath();
                                    Runtime.getRuntime().exec("cmd /c start " + path);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                    //对标签拆分进行高亮显示
                    TextFlow textFlow = new TextFlow();

                    String[] strings = fileModel.getContent().split("<span>|</span>");
                    for (int i = 0;i<strings.length;i++){
                        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                        Matcher matcher = p.matcher(strings[i]);
                        Text fxText = new Text(matcher.replaceAll(""));

                        if (i%2==1){
                            fxText.setFill(Color.RED);
                        }
                        textFlow.getChildren().add(fxText);
                    }
                    textFlow.setMaxWidth(750);
                    box.getChildren().addAll(hBox, textFlow);

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
