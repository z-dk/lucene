package xyz.zdk.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import xyz.zdk.bean.FileModel;
import xyz.zdk.ikanalyzer.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Created by z_dk on 2019/2/22.
 */
public class Lucene {
    private final Analyzer analyzer;
    private final IndexWriter indexWriter;
    private final Directory fsDirectory;
    private final FieldType fieldType;

    Lucene(Path path) throws IOException {
        // 索引初始化
        analyzer = new IKAnalyzer();
        IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
        iwConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
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
    public Lucene addDocument(FileModel fileModel) throws IOException {

        Document doc = new Document();
        doc.add(new Field("title",fileModel.getContent(),fieldType));
        doc.add(new Field("content",fileModel.getTitle(),fieldType));
        doc.add(new Field("path",fileModel.getPath(),fieldType));
        synchronized (this) {
            indexWriter.addDocument(doc);
            indexWriter.commit();
        }
        return this;
    }

    public Lucene delete(String ID) throws IOException {
        // 做删除标志
        synchronized (this) {
            indexWriter.deleteDocuments(new Term("id", ID));
            indexWriter.forceMergeDeletes();
            indexWriter.commit();
        }
        return this;
    }
}
