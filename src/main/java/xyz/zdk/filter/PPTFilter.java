package xyz.zdk.filter;

import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;
import xyz.zdk.bean.FileModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by z_dk on 2018/12/7.
 */
public class PPTFilter {
    public static FileModel extractFile(File file){
        FileModel fileModel = new FileModel(file.getName(),parserFile(file),file.getAbsolutePath());
        return fileModel;
    }
    public static String parserFile(File file){
        String content = "";
        StringBuffer sb = new StringBuffer();
        try {
            InputStream inputStream = new FileInputStream(file);
            if (file.getName().endsWith(".ppt")){
                PowerPointExtractor extractor = new PowerPointExtractor(inputStream);
                return extractor.getText();
            }else if (file.getName().endsWith(".pptx")){
                SlideShow xmlSlideShow = new XMLSlideShow(inputStream);
                List<XSLFSlide> slides = xmlSlideShow.getSlides();
                for(XSLFSlide slide : slides){
                    CTSlide rawSlide = slide.getXmlObject();
                    CTGroupShape gs = rawSlide.getCSld().getSpTree();
                    CTShape[] shapes = gs.getSpArray();
                    for(CTShape shape:shapes){
                        CTTextBody textBody = shape.getTxBody();
                        if(null==textBody){
                            continue;
                        }
                        CTTextParagraph[] paras = textBody.getPArray();
                        for(CTTextParagraph textParagraph:paras){
                            CTRegularTextRun[] textRuns = textParagraph.getRArray();
                            for(CTRegularTextRun textRun:textRuns){
                                sb.append(textRun.getT());
                            }
                        }
                    }
                }
                content = sb.toString();
                xmlSlideShow.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
