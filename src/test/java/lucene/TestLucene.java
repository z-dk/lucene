package lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import xyz.zdk.ikanalyzer.IKAnalyzer;
import xyz.zdk.lucene.*;
import xyz.zdk.lucene.Lucene;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by z_dk on 2019/2/22.
 */
public class TestLucene {
    public static void main(String[] args) throws Exception {
        //File file = new File("E:\\文档\\JAVA-api\\文档检索系统\\毕业设计任务书-----朱登奎.doc");
        //UpdateIndex.update(file);
        //CreateIndex.create();
        //DeleteIndex.delete("E:\\文档\\JAVA-api\\文档检索系统\\41-1507084237-朱登奎-第二组.pptx");// √
        //AddIndex.addIndex(new File("E:\\文档\\JAVA-api\\文档检索系统\\41-1507084237-朱登奎-第二组.pptx"));
        //UpdateIndex.update(new File("E:\\文档\\JAVA-api\\文档检索系统\\任务详细划分.docx"));
        //testSearch("E:\\文档\\JAVA-api\\文档检索系统\\子文件夹2"+"*");
        testSearch("E:\\文档\\JAVA-api\\文档检索系统\\子文件夹2".replace("\\","?"));
    }

    public static void testSearch(String path){
        try {
            Directory dir = FSDirectory.open(Paths.get(Lucene.HOMEPATH));
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);

            System.out.println(path);

            WildcardQuery query=new WildcardQuery(new Term("path",path+"*"));
            //TermQuery query = new TermQuery(new Term("path",path));
            TopDocs tops=searcher.search(query, 10);
            for (ScoreDoc sc: tops.scoreDocs) {
                Document document = searcher.doc(sc.doc);
                System.out.println(document.get("title"));
            }
            System.out.println(tops.totalHits);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void testTerm(){
        try {
            String  keyword="124b947a298c466ebc10ae77b7f58965";
            Directory dir = FSDirectory.open(Paths.get("E:\\文档\\JAVA-api\\毕业-----------------------设计\\index"));
            IndexReader reader = DirectoryReader.open(dir);
            IndexSearcher searcher = new IndexSearcher(reader);
            //------------TermQuery  单个关键字查询
            TermQuery query=new TermQuery(new Term("id",keyword));
            TopDocs tops=searcher.search(query, 10);
            for (ScoreDoc sc: tops.scoreDocs) {
                Document document = searcher.doc(sc.doc);
                System.out.println(document.get("id"));
                System.out.println(document.get("title"));
            }
            System.out.println(tops.totalHits);
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void testDel(String p){
        try {



            Analyzer analyzer = new IKAnalyzer();
            IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
            iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            Path path = Paths.get("E:\\文档\\JAVA-api\\毕业-----------------------设计\\index");
            Directory fsDirectory = FSDirectory.open(path);

            IndexWriter indexWriter = new IndexWriter(fsDirectory, iwConfig);
            IndexReader reader = DirectoryReader.open(fsDirectory);

            TermQuery query=new TermQuery(new Term("path",p));

            System.out.println(indexWriter.numDocs());
            indexWriter.deleteDocuments(query);
            System.out.println(indexWriter.numDocs());
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
