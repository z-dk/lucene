package xyz.zdk.ikanalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

/**
 * Created by z_dk on 2018/11/23.
 */
public class IKAnalyzer extends Analyzer {
    private boolean useSmart;
    public boolean useSmart(){
        return useSmart;
    }
    public void setUseSmart(boolean useSmart){
        this.useSmart = useSmart;
    }
    public IKAnalyzer(){
        this(false);
    }

    @Override
    protected TokenStreamComponents createComponents(String s) {
        Tokenizer _IKTokenizer = new IKTokenizer6x(this.useSmart());
        return new TokenStreamComponents(_IKTokenizer);
    }

    public IKAnalyzer(boolean useSmart){
        super();
        this.useSmart = useSmart;
    }

}
