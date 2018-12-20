package searchTest;

/**
 * Created by z_dk on 2018/12/20.
 */
public class Split {
    public static void main(String[] args) {
        String text = "<span>朱</span><span>登</span><span>奎</span>你是<span>？</span>";
        splitText(text);
    }
    public static void splitText(String text){
        String[] strings = text.split("<span>|</span>");
        for (String s:strings){
            System.out.println(s);
        }
    }
}
