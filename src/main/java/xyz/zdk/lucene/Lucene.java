package xyz.zdk.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import xyz.zdk.bean.FileModel;
import xyz.zdk.ikanalyzer.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by z_dk on 2019/2/22.
 */
public class Lucene {
    private final Analyzer analyzer;
    private final IndexWriter indexWriter;
    private final Directory fsDirectory;
    private final FieldType fieldType;

    public static final String HOMEPATH = "E:\\文档\\JAVA-api\\毕业-----------------------设计\\index";

    public Lucene(Path path) throws IOException {
        // 索引初始化
        analyzer = new IKAnalyzer();
        IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        fsDirectory = FSDirectory.open(path);
        indexWriter = new IndexWriter(fsDirectory, iwConfig);

        fieldType = new FieldType();

        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        fieldType.setStored(true);
        fieldType.setTokenized(true);
        fieldType.setStoreTermVectors(true);
        fieldType.setStoreTermVectorPositions(true);
        fieldType.setStoreTermVectorOffsets(true);
    }
    public void close() throws IOException {
        if (Objects.nonNull(indexWriter) && indexWriter.isOpen())
            indexWriter.close();
        if (Objects.nonNull(fsDirectory))
            fsDirectory.close();
        if (Objects.nonNull(analyzer))
            analyzer.close();
    }
    public Lucene addDocuments(List<FileModel> fileModels) throws IOException {
        for (FileModel f : fileModels) {
            Document doc = new Document();
            doc.add(new Field("title", f.getTitle(), fieldType));
            doc.add(new Field("content", f.getContent(), fieldType));
            doc.add(new StringField("path", f.getPath(), Field.Store.YES));
            doc.add(new StringField("id", UUID.randomUUID().toString().replace("-",""), Field.Store.YES));
            indexWriter.addDocument(doc);
        }
        indexWriter.commit();
        return this;
    }



    public Lucene delete(String path) throws IOException {
        // 做删除标志
        synchronized (this) {
            TermQuery query=new TermQuery(new Term("path",path));
            indexWriter.deleteDocuments(query);
            indexWriter.commit();
        }
        return this;
    }

    public Lucene update(String path,Document document) throws IOException {
        synchronized (this) {
            indexWriter.updateDocument(new Term("path",path),document);
            indexWriter.commit();
        }
        return this;
    }

    public Document setDocument(FileModel f){
        Document document = new Document();
        document.add(new Field("title",f.getTitle(),fieldType));
        document.add(new Field("content",f.getContent(),fieldType));
        document.add(new StringField("path",f.getPath(),Field.Store.YES));
        document.add(new StringField("id", UUID.randomUUID().toString().replace("-",""), Field.Store.YES));
        return document;
    }
}
