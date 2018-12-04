package xyz.zdk.ikanalyzer;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;

/**
 * Created by z_dk on 2018/11/23.
 */
public class IKTokenizer6x extends Tokenizer {
    private IKSegmenter _IKImplement;
    private final CharTermAttribute termAtt;
    private final OffsetAttribute offsetAtt;
    private final TypeAttribute typeAtt;
    private int endPosition;
    public IKTokenizer6x(boolean useSmart){
        super();
        offsetAtt = addAttribute(OffsetAttribute.class);
        termAtt = addAttribute(CharTermAttribute.class);
        typeAtt = addAttribute(TypeAttribute.class);
        _IKImplement = new IKSegmenter(input,useSmart);
    }
    @Override
    public boolean incrementToken() throws IOException {
        clearAttributes();
        Lexeme nextLexme = _IKImplement.next();
        if (nextLexme != null){
            //将Lexme转成attributes
            termAtt.append(nextLexme.getLexemeText());
            termAtt.setLength(nextLexme.getLength());
            offsetAtt.setOffset(nextLexme.getBeginPosition(),nextLexme.getEndPosition());
            endPosition = nextLexme.getEndPosition();
            typeAtt.setType(nextLexme.getLexemeText());
            return true;
        }
        return false;
    }

    @Override
    public void reset()throws IOException{
        super.reset();
        _IKImplement.reset(input);
    }
    @Override
    public final void end(){
        int finalOffset = correctOffset(this.endPosition);
        offsetAtt.setOffset(finalOffset,finalOffset);
    }
}
