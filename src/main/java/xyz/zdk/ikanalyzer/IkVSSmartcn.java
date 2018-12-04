package xyz.zdk.ikanalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by z_dk on 2018/11/23.
 */
public class IkVSSmartcn {
    private static String str1 = "公路局正在治理解放大道路面积水问题。";
    private static String str2 = "IKAnalyzer是一个开源的，基于Java语言开发的轻量级的中文分词工具包。";

    public static void main(String[] args) throws IOException {
        Analyzer analyzer = null;
        System.out.println("句子一："+str1);
        System.out.println("SamrtChineseAnalyzer分词结果：");
        analyzer = new SmartChineseAnalyzer();
        printAnalyzer(analyzer,str1);
        System.out.println("IKAnalyzer分词结果：");
        analyzer = new IKAnalyzer(true);
        printAnalyzer(analyzer,str1);
        System.out.println("-------------------------------------");
        System.out.println("句子二："+str2);
        System.out.println("SmartChineseAnalyzer分词结果：");
        analyzer = new SmartChineseAnalyzer();
        printAnalyzer(analyzer,str2);
        System.out.println("IKAnalyzer分词结果：");
        analyzer = new IKAnalyzer(true);
        printAnalyzer(analyzer,str2);
        analyzer.close();
    }
    public static void printAnalyzer(Analyzer analyzer,String str) throws IOException{
        StringReader reader = new StringReader(str);
        TokenStream toStream = analyzer.tokenStream(str,reader);
        toStream.reset();
        CharTermAttribute teAttribute = toStream.getAttribute(CharTermAttribute.class);
        while (toStream.incrementToken()){
            System.out.print(teAttribute.toString()+"|");
        }
        System.out.println();
    }
}
