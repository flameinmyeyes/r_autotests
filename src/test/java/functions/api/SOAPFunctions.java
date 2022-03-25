package functions.api;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import functions.common.CommonFunctions;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SOAPFunctions {

    /**
     * Отправить SOAP запрос
     * @param url - адрес сервиса
     * @param filePath - полный путь к файлу
     */
    public static void sendSOAPRequest(String url, String filePath) {
        try {
            //Создаем подключение
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            //Открываем запрос из файла
            SOAPMessage requestMessage = messageFactory.createMessage(new MimeHeaders(), new FileInputStream(Paths.get(filePath).toFile()));
            //Отправляем запрос
            URL endpoint = new URL(url);
            SOAPConnection connection = soapConnectionFactory.createConnection();
            connection.call(requestMessage, endpoint);
            connection.close();
        } catch (IOException | SOAPException ex){
            throw new RuntimeException(ex);
        }
        System.out.println("Отправлен SOAP-запрос на сервис: " + url);
    }

    /**
     * Отправить SOAP запрос и сохранить ответ
     * @param url - адрес сервиса
     * @param filePath - полный путь к файлу
     * @param savingAnswerPath - полный путь к файлу, в который сохранится ответ
     */
    public static void sendSOAPRequest(String url, String filePath, String savingAnswerPath) {
        try {
            //Создаем подключение
            MessageFactory messageFactory = MessageFactory.newInstance();
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            //Открываем запрос из файла
            SOAPMessage requestMessage = messageFactory.createMessage(new MimeHeaders(), new FileInputStream(Paths.get(filePath).toFile()));
            //Отправляем запрос
            URL endpoint = new URL(url);
            SOAPConnection connection = soapConnectionFactory.createConnection();
            // Получаем ответ от Soup
            SOAPMessage soapResponse = connection.call(requestMessage, endpoint);

            // Инициализируем трансформер и форматируем ответ в красивую xml
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            Source sourceContent = soapResponse.getSOAPPart().getContent();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            StreamResult result = new StreamResult(System.out);
            StreamResult writeResult = new StreamResult(new File(savingAnswerPath));
            transformer.transform(sourceContent, result);
            transformer.transform(sourceContent, writeResult);

            // Сохраняем в файл
           /* FileOutputStream out = new FileOutputStream(savingAnswerPath);
            soapResponse.writeTo(out);*/
            connection.close();

        } catch (IOException | SOAPException | TransformerException ex){
            throw new RuntimeException(ex);
        }
    }


}
