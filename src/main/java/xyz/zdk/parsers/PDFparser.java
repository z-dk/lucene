package xyz.zdk.parsers;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import xyz.zdk.ikanalyzer.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by z_dk on 2018/11/25.
 */
public class PDFparser {
    public static void main(String[] args) {
        getPDFText();
    }
    public static String getPDFText(){
        PDDocument pdDocument = null;
        try {
            pdDocument = PDDocument.load(new File("E:\\文档\\JAVA api\\文档检索系统\\开题报告.pdf"));
            PDFTextStripper stripper = new PDFTextStripper();
            System.out.println(stripper.getText(pdDocument));
            Analyzer analyzer = new IKAnalyzer(true);
            StringReader reader = new StringReader(stripper.getText(pdDocument));
            TokenStream tokenStream = analyzer.tokenStream(stripper.getText(pdDocument),reader);
            tokenStream.reset();
            CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            while (tokenStream.incrementToken()){
                System.out.print(charTermAttribute.toString()+"|");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
