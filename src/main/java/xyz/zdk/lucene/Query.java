package xyz.zdk.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import xyz.zdk.bean.FileModel;
import xyz.zdk.ikanalyzer.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by z_dk on 2018/12/18.
 */
public class Query {
    public static ArrayList<FileModel> search(String text,String fileType){
        ArrayList<FileModel> filelists = new ArrayList<>();

        String[] fields = {"title","content"};
        Path indexPath = Paths.get("E:\\文档\\JAVA-api\\毕业-----------------------设计\\index");
        Directory dir = null;
        try {
            dir = FSDirectory.open(indexPath);
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);
            Analyzer analyzer = new IKAnalyzer(true);

            //多域搜索
            MultiFieldQueryParser parser = new MultiFieldQueryParser(fields,analyzer);
//            parser.setDefaultOperator(QueryParser.Operator.AND);
            org.apache.lucene.search.Query query = parser.parse(text);
            TopDocs tds;
            //如果不是检索全部类型的文档，则将文档类型作为搜索条件
            if (!"all".equals(fileType)){

                Term term= new Term("title",fileType);
                WildcardQuery typeQuery = new WildcardQuery(term);

                BooleanClause booleanClause = new BooleanClause(query, BooleanClause.Occur.MUST);
                BooleanClause booleanClause1 = new BooleanClause(typeQuery, BooleanClause.Occur.MUST);
                BooleanQuery booleanQuery = new BooleanQuery.Builder()
                        .add(booleanClause).add(booleanClause1).build();

                tds = searcher.search(booleanQuery,10);
            }else{
                tds = searcher.search(query,10);
            }


            //定制高亮标签
            /*SimpleHTMLFormatter fors = new SimpleHTMLFormatter();
            QueryScorer scoreTitle = new QueryScorer(query,fields[0]);
            Highlighter hlTitle = new Highlighter(fors,scoreTitle);
            QueryScorer scoreContent = new QueryScorer(query,fields[1]);
            Highlighter hlContent = new Highlighter(fors,scoreContent);*/

            //遍历查询结果，将结果返回给客户端
            for(ScoreDoc sd:tds.scoreDocs){
                Document doc = searcher.doc(sd.doc);
                String title = doc.get("title");
                String content = doc.get("content").replaceAll("\\s*","");
                //高亮显示
                /*TokenStream tokenStream = TokenSources.getAnyTokenStream
                        (searcher.getIndexReader(),sd.doc,fields[0],new IKAnalyzer());
                Fragmenter fragmenter = new SimpleSpanFragmenter(scoreTitle);
                hlTitle.setTextFragmenter(fragmenter);
                String hl_title = hlTitle.getBestFragment(tokenStream,title);

                tokenStream = TokenSources.getAnyTokenStream
                        (searcher.getIndexReader(),sd.doc,fields[1],new IKAnalyzer(true));
                fragmenter = new SimpleSpanFragmenter(scoreContent);
                hlContent.setTextFragmenter(fragmenter);
                String hl_content = hlContent.getBestFragment(tokenStream,content);

                FileModel fm = new FileModel(hl_title!=null?hl_title:title,
                        hl_content!=null?hl_content:content,doc.get("path"));*/
                FileModel fm = new FileModel(title,content,doc.get("path"));
                filelists.add(fm);
            }
            dir.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return filelists;
    }
}
