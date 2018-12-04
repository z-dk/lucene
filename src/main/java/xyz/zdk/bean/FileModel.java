package xyz.zdk.bean;

/**
 * Created by z_dk on 2018/11/28.
 */
public class FileModel {
    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public FileModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public FileModel() {
    }
}
