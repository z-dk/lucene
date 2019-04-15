package xyz.zdk.parsers;

import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.openxmlformats.schemas.drawingml.x2006.main.CTRegularTextRun;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextBody;
import org.openxmlformats.schemas.drawingml.x2006.main.CTTextParagraph;
import org.openxmlformats.schemas.presentationml.x2006.main.CTGroupShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTShape;
import org.openxmlformats.schemas.presentationml.x2006.main.CTSlide;

import java.io.*;
import java.util.List;

/**
 * Created by z_dk on 2018/12/7.
 */
public class PPTparser {
    public static void main(String[] args) throws FileNotFoundException {
        String resultString = null;
        StringBuffer sb = new StringBuffer();
        InputStream inputStream = new FileInputStream(new File("E:\\文档\\JAVA api\\毕业-----------------------设计\\文档\\41-1507084237-朱登奎-第二组.pptx"));
        try {
            SlideShow xmlSlideShow = new XMLSlideShow(inputStream);
            List<XSLFSlide> slides = xmlSlideShow.getSlides();
            for(XSLFSlide slide:slides){
                CTSlide rawSlide = slide.getXmlObject();
                CTGroupShape gs = rawSlide.getCSld().getSpTree();
                CTShape[] shapes = gs.getSpArray();
                for(CTShape shape:shapes){
                    CTTextBody tb = shape.getTxBody();
                    if(tb==null&&true){
                        continue;
                    }
                    CTTextParagraph[] paras = tb.getPArray();
                    for(CTTextParagraph textParagraph:paras){
                        CTRegularTextRun[] textRuns = textParagraph.getRArray();
                        for(CTRegularTextRun textRun:textRuns){
                            sb.append(textRun.getT());
                        }
                    }
                }
            }
            resultString = sb.toString();
            xmlSlideShow.close();
            System.out.println(resultString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
