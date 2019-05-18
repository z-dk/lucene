package parsers;

import org.apache.poi.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import xyz.zdk.bean.FileModel;
import xyz.zdk.filter.XmlFilter;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * Created by z_dk on 2019/5/16.
 */
public class Xmlparser {
    public static void main(String[] args) {
        try {
            xmlTest();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    //递归查询节点函数,输出节点名称
    private static void  getChildNodes(Element elem){
        //System.out.println(elem.getName());
        if (!"".equals(elem.getText())) {
            System.out.println(elem.getText());
        }
        Iterator<Node> it= elem.nodeIterator();
        while (it.hasNext()){
            Node node = it.next();
            if (node instanceof Element){
                Element e1 = (Element)node;
                getChildNodes(e1);
            }

        }
    }

    public static void xmlTest() throws DocumentException {
        //SAXReader reader = new SAXReader();
        //Document document = reader.read();

        FileModel fileModel = XmlFilter.extractFile(new File("E:\\文档\\JAVA-api\\文档检索系统\\子文件夹3\\配置文件.xml"));
        System.out.println(fileModel.getContent());
        /**
         * 节点对象的操作方法
         */

        //获取文档根节点
        //Element root = document.getRootElement();
        //输出根标签的名字
        //System.out.println(root.getName());

        //root.getData()


        //获取根节点下面的所有子节点（不包过子节点的子节点）
        //List<Element> list = root.elements() ;
        //遍历List的方法
        /*for (Element e:list){
            System.out.println(e.getName());
            if (!"\n".equals(e.getText())) {
                System.out.println(e.getText());
            }
        }*/


        //获得指定节点下面的子节点
        /*Element contactElem = root.element("contact");//首先要知道自己要操作的节点。
        List<Element> contactList = contactElem.elements();
        for (Element e:contactList){
            System.out.println(e.getName());
        }*/


        //调用下面获取子节点的递归函数。
        //getChildNodes(root);


        //获得当前标签下指定名称的第一个子标签
        //Element conElem = root.element("contact");
        //System.out.println(conElem.getName());


        //获得更深层次的标签（一层一层的获取）
        //Element nameElem = root.element("contact").element("name");
        //System.out.println(nameElem.getName());
    }
}
