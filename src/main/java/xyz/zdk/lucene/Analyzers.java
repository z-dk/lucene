package xyz.zdk.lucene;

/**
 * Created by z_dk on 2018/11/14.
 */

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by z_dk on 2018/11/14.
 */
public class Analyzers {
    private static String str = "中华人民共和国简称中国，是一个有13亿人口的国家";
    private static String strEn = "Dogs can not achieve a place, eyes can reach;";

    public static void main(String[] args) throws IOException {
        System.out.println("StandarAnalyzer对中文分词：");
        stdAnalyzer(str);
        System.out.println("StandarAnalyzer对英文分词：");
        stdAnalyzer(strEn);
    }

    public static void stdAnalyzer(String str) throws IOException {
        Analyzer analyzer = null;
        analyzer = new StandardAnalyzer();
        printAnalyzer(analyzer);
        analyzer = new SmartChineseAnalyzer();
        printAnalyzer(analyzer);
    }
    public static void printAnalyzer(Analyzer analyzer) throws IOException {
        StringReader reader = new StringReader(str);
        TokenStream toStream = analyzer.tokenStream(str,reader);
        toStream.reset();
        CharTermAttribute teAttribute = toStream.getAttribute(CharTermAttribute.class);
        System.out.println("标准分词：");
        while (toStream.incrementToken()){
            System.out.print(teAttribute.toString()+"|");
        }
        System.out.println("\n");
        analyzer.close();
    }
}

