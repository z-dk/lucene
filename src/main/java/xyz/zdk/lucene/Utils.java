package xyz.zdk.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import xyz.zdk.bean.FileModel;
import xyz.zdk.ikanalyzer.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by z_dk on 2019/2/22.
 */
public class Utils {
    static {
        //创建分词器
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig icw = new IndexWriterConfig(analyzer);
        icw.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        Directory dir = null;
        IndexWriter inWriter = null;
        Path indexPath = Paths.get("E:\\文档\\JAVA-api\\毕业-----------------------设计\\index");

        if (!Files.isReadable(indexPath)) {
            System.out.println("Document directory '" + indexPath.toAbsolutePath() + "' does not exist or is" +
                    " not readable,please check the path");
            System.exit(1);
        }

        FieldType fieldType = new FieldType();

        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        fieldType.setStored(true);
        fieldType.setTokenized(true);
        fieldType.setStoreTermVectors(true);
        fieldType.setStoreTermVectorPositions(true);
        fieldType.setStoreTermVectorOffsets(true);

        // 遍历fileList,建立索引
        /*for (FileModel f : fileList) {
            Document doc = new Document();
            doc.add(new Field("title", f.getTitle(), fieldType));
            doc.add(new Field("content", f.getContent(), fieldType));
            doc.add(new Field("path", f.getPath(), fieldType));
            inWriter.addDocument(doc);
        }
        inWriter.commit();
        inWriter.close();
        dir.close();*/

    }

    //对单个文档进行新增索引
    /*public static void createOneIndex(FileModel f){
        dir = FSDirectory.open(indexPath);
        inWriter = new IndexWriter(dir, icw);
        Document doc = new Document();
        doc.add(new Field("title", f.getTitle(), fieldType));
        doc.add(new Field("content", f.getContent(), fieldType));
        doc.add(new Field("path", f.getPath(), fieldType));
        inWriter.addDocument(doc);
    }*/
}
