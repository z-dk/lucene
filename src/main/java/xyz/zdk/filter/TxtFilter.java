package xyz.zdk.filter;

import xyz.zdk.bean.FileModel;

import java.io.*;

/**
 * Created by z_dk on 2018/12/9.
 */
public class TxtFilter {
    public static FileModel extractFile(File file){
        FileModel model = new FileModel(file.getName(),parserFile(file),file.getAbsolutePath());
        return model;
    }
    public static String parserFile(File file){
        String encoding = "UTF-8";
        String str = null;
        StringBuffer buffer = new StringBuffer();
        try {
            if (!file.exists()){
                return "";
            }
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(inputStream,encoding);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((str=bufferedReader.readLine())!=null){
                buffer.append(str+"\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
