package parsers;

import java.io.*;

/**
 * Created by z_dk on 2018/12/9.
 */
public class Txtparser {
    public static void main(String[] args) {
        txtparser();
    }
    public static void txtparser(){
        String encoding = "UTF-8";
        String str = null;
        StringBuffer buffer = new StringBuffer();
        File file = new File("E:\\下载\\第七届蓝桥杯个人赛真题\\第七届蓝桥杯大赛个人赛省赛（软件类）真题\\Java语言B组\\1\\题目.txt");
        try {
            InputStream inputStream = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(inputStream,encoding);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((str=bufferedReader.readLine())!=null){
                buffer.append(str);
                buffer.append("\n");
            }
            System.out.println(buffer);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
