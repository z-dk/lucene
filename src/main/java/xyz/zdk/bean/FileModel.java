package xyz.zdk.bean;

/**
 * Created by z_dk on 2018/11/28.
 */
public class FileModel {
    private String title;
    private String content;
    private String path;

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileModel(String title, String content, String path) {
        this.title = title;
        this.content = content;
        this.path = path;
    }

    public FileModel() {
    }
}
