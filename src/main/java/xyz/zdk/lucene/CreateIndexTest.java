package xyz.zdk.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import xyz.zdk.bean.News;
import xyz.zdk.ikanalyzer.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by z_dk on 2018/11/23.
 */
public class CreateIndexTest {

    public static void main(String[] args) {
        News news1 = new News(1,"习近平会见美国总统奥巴马，学习国外经验","国家主席习近平9月3日在杭州西湖国" +
                "宾馆会见前来出席二十国集团领导人杭州峰会的美国总统奥巴马。。。",672);
        News news2 = new News(2,"北大迎4380名新生 农村学生700多人近年最多","昨天北京大学迎来4380名来自全国" +
                "各地及数十个国家的本科新生。其中，农村学生共700余名，为近年最多。。。",995);
        News news3 = new News(3,"特朗普宣誓(Donald Trump)就任美国第45任总统","当地时间1月20日，唐纳德·特" +
                "朗普在美国国会宣誓就职，正式成为美国第45任总统。",1872);
        //创建分词器
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
        icw.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        Directory dir = null;
        IndexWriter inWrite = null;
        Path indexPath = Paths.get("E:\\文档\\JAVA api\\毕业-----------------------设计\\index");
        Date start = new Date();
        try {
            if (!Files.isReadable(indexPath)){
                System.out.println("Document directory '"+indexPath.toAbsolutePath()+"' does not exist or is" +
                        " not readable,please check the path");
                System.exit(1);
            }
            dir = FSDirectory.open(indexPath);
            inWrite = new IndexWriter(dir,icw);
            FieldType idType = new FieldType();
            idType.setIndexOptions(IndexOptions.DOCS);
            idType.setStored(true);
            //设置新闻标题索引文档、词项频率、位移信息和偏移量，存储并词条化
            FieldType titleType = new FieldType();
            titleType.setIndexOptions(IndexOptions.DOCS);
            titleType.setStored(true);
            FieldType contentType = new FieldType();
            contentType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
            contentType.setStored(true);
            contentType.setTokenized(true);
            contentType.setStoreTermVectors(true);
            contentType.setStoreTermVectorPositions(true);
            contentType.setStoreTermVectorOffsets(true);
            contentType.setStoreTermVectorPayloads(true);
            Document doc1 = new Document();
            doc1.add(new Field("id",String.valueOf(news1.getId()),idType));
            doc1.add(new Field("title",news1.getTitle(),titleType));
            doc1.add(new Field("content",news1.getContent(),contentType));
            doc1.add(new IntPoint("reply",news1.getReply()));
            doc1.add(new StoredField("reply_display",news1.getReply()));
            Document doc2 = new Document();
            doc2.add(new Field("id",String.valueOf(news2.getId()),idType));
            doc2.add(new Field("title",news2.getTitle(),titleType));
            doc2.add(new Field("content",news2.getContent(),contentType));
            doc2.add(new IntPoint("reply",news2.getReply()));
            doc2.add(new StoredField("reply_display",news2.getReply()));
            Document doc3 = new Document();
            doc3.add(new Field("id",String.valueOf(news3.getId()),idType));
            doc3.add(new Field("title",news3.getTitle(),titleType));
            doc3.add(new Field("content",news3.getContent(),contentType));
            doc3.add(new IntPoint("reply",news3.getReply()));
            doc3.add(new StoredField("reply_display",news3.getReply()));
            inWrite.addDocument(doc1);
            inWrite.addDocument(doc2);
            inWrite.addDocument(doc3);
            inWrite.commit();
            inWrite.close();
            dir.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        Date end = new Date();
        System.out.println("索引文档用时："+(end.getTime()-start.getTime())+"毫秒");
    }
}
