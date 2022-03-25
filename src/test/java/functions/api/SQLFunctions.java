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

public class SQLFunctions {

    /**
     * SQL-запрос к БД
     * @param classBD     - класс БД ("oracle.jdbc.OracleDriver", "org.postgresql.Driver", "com.mysql.jdbc.Driver")
     * @param typeRequest - тип запроса ("update", "remove", "insert", "select")
     * @param url         - адрес
     * @param login       - логин
     * @param password    - пароль
     * @param request     - SQL-запрос
     */
    public static String SQLRequest(String classBD, String typeRequest, String url, String login, String password, String request) {
        String response = "";

        try {
            Class.forName(classBD);
            Connection connection = DriverManager.getConnection(url, login, password);
            Statement statement = connection.createStatement();

            if (typeRequest.equals("insert")) {
                statement.executeUpdate(request);
            } else if (typeRequest.equals("remove") || typeRequest.equals("update")) {
                statement.execute(request);
            } else if (typeRequest.equals("select")) {
                ResultSet result = statement.executeQuery(request);
                while (result.next()) {
                    if (response.isEmpty()) {
                        response = response + result.getString(1);
                    } else {
                        response = response + " " + result.getString(1);
                    }
                }
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        return response;
    }

    /**
     * SQL-запрос к БД (вариант с файлом конфигурации БД)
     * @param wayToDBConfig - путь к файлу конфигурации БД (.properties)
     * @param request - SQL-запрос
     */
    public static String SQLRequest(String wayToDBConfig, String request) {
        String response = "";

        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream(wayToDBConfig), "ANSI-1251"));
        } catch (Exception e) {
            System.out.println("Файл конфигурации БД не найден!");
        }

        String classBD = properties.getProperty("classBD");
        String url = properties.getProperty("url");
        String login = properties.getProperty("login");
        String password = properties.getProperty("password");

        System.out.println("Выполняется SQL-запрос: " + request);

        try {
            Class.forName(classBD);
            Connection connection = DriverManager.getConnection(url, login, password);
            Statement statement = connection.createStatement();

            if (request.toLowerCase().startsWith("insert")) {
//                System.out.println("Выполняется: \"executeUpdate\"");
                statement.executeUpdate(request);

            } else if (request.toLowerCase().startsWith("remove") || request.toLowerCase().startsWith("update")) {
//                System.out.println("Выполняется: \"execute\"");
                statement.execute(request);

            } else if (request.toLowerCase().startsWith("select")) {
//                System.out.println("Выполняется: \"executeQuery\"");
                ResultSet result = statement.executeQuery(request);
                while (result.next()) {
                    if (response.isEmpty()) {
                        response = response + result.getString(1);
                    } else {
                        response = response + " " + result.getString(1);
                    }
                }
            }
            connection.close();
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        return response;
    }

}
