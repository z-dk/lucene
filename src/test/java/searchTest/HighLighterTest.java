package searchTest;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import xyz.zdk.ikanalyzer.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by z_dk on 2018/11/25.
 */
public class HighLighterTest {
    public static void main(String[] args) throws IOException, ParseException, InvalidTokenOffsetsException {
        String field = "title";
        Path indexPath = Paths.get("E:\\文档\\JAVA-api\\毕业-----------------------设计\\index");
        Directory dir = FSDirectory.open(indexPath);
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = new IKAnalyzer();
        QueryParser parser = new QueryParser(field,analyzer);
        Query query = parser.parse("北大");
        System.out.println("Query:"+query);
        QueryScorer scorer = new QueryScorer(query,field);
        SimpleHTMLFormatter fors = new SimpleHTMLFormatter("<span style=\"color:red;\">","</span>");
        Highlighter highlighter = new Highlighter(fors,scorer);
        //高亮分词器
        TopDocs tds = searcher.search(query,10);
        for (ScoreDoc sd:tds.scoreDocs) {
            Document doc = searcher.doc(sd.doc);
            System.out.println("id:"+doc.get("id"));
            System.out.println("title:"+doc.get("title"));
            TokenStream tokenStream = TokenSources.getAnyTokenStream(searcher.getIndexReader(),sd.doc,field,analyzer);
            Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
            highlighter.setTextFragmenter(fragmenter);
            String str = highlighter.getBestFragment(tokenStream,doc.get(field));
            System.out.println("高亮的片段："+str);
        }
        dir.close();
        reader.close();
    }
}
