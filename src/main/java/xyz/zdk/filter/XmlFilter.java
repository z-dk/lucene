package xyz.zdk.filter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import xyz.zdk.bean.FileModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by z_dk on 2019/5/16.
 */
public class XmlFilter {
    public static FileModel extractFile(File file){
        FileModel model = null;
        try {
            model = new FileModel(file.getName(),parserFile(file),file.getAbsolutePath());
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return model;
    }

    public static String parserFile(File file) throws DocumentException {

        SAXReader reader = new SAXReader();
        Document document = reader.read(file);
        document.normalize();

        Element root = document.getRootElement();
        List<String> resultList = new ArrayList<>();
        List<Element> list = root.elements();


        resultList.add(root.getText());

        resultList = getChildNodes(root, resultList);

        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher matcher = p.matcher(resultList.toString());

        return matcher.replaceAll("");
    }

    //递归查询节点函数,输出节点名称
    private static List<String>  getChildNodes(Element elem, List<String> list){
        if (!"".equals(elem.getText())) {
            list.add(elem.getText());
        }
        Iterator<Node> it= elem.nodeIterator();
        while (it.hasNext()){
            Node node = it.next();
            if (node instanceof Element){
                Element e1 = (Element)node;
                getChildNodes(e1,list);
            }

        }
        return list;
    }

}
