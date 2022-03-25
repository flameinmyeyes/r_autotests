package functions.file;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XMLHandler {

    /**
     * Обновить XML-файл по тэгу
     * @param filePath - полный путь к файлу
     * @param xmlTag - название тэга
     * @param tagValue - желаемое значение тега
     */
    public static void updateXML(String filePath, String xmlTag, String tagValue) {
//        tagValue = compareValues(tagValue);
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            //обновляем значения
            updateXMLTag(doc, xmlTag, tagValue);
            //запишем отредактированный элемент в файл
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    //тех. функция для updateXML
    private static void updateXMLTag(org.w3c.dom.Document doc, String xmlTag, String tagValue) {
        NodeList nodeList = doc.getChildNodes();
        Element lang = null;
        //проходим по каждому элементу
        for(int i= 0; i < nodeList.getLength(); i++) {
            lang = (Element) nodeList.item(i);
            Node name = lang.getElementsByTagName(xmlTag).item(0).getFirstChild();
            name.setNodeValue(tagValue);
        }
    }

    /**
     * Обновить XML-файл по номеру повторяющегося тега (если несколько одинаковых тегов)
     * @param filePath - полный путь к файлу
     * @param xmlTag - название тэга
     * @param tagValue - желаемое значение тега
     * @param num - номер тэга
     */
    public static void updateXML(String filePath, String xmlTag, String tagValue, int num) {
//        tagValue = compareValues(tagValue);
        File xmlFile = new File(filePath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            org.w3c.dom.Document doc = builder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            //обновляем значения
            updateXMLTag(doc, xmlTag, tagValue, num);
            //запишем отредактированный элемент в файл
            doc.getDocumentElement().normalize();
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(source, result);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }
    //тех. функция для updateXML
    private static void updateXMLTag(org.w3c.dom.Document doc, String xmlTag, String tagValue, int num) {
        NodeList nodeList = doc.getChildNodes();
        Element lang = null;
        //проходим по каждому элементу
        for(int i= 0; i < nodeList.getLength(); i++) {
            lang = (Element) nodeList.item(i);
            Node name = lang.getElementsByTagName(xmlTag).item(num).getFirstChild();
            name.setNodeValue(tagValue);
        }
    }

}
