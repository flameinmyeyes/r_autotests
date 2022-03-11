package functional;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.gson.JsonObject;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import javax.xml.soap.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public final class APIFunctions {

    /* Методы для работы с API */

    /**
     * Отправить файл на FTP
     * @param hostAddress - адрес
     * @param login - логин
     * @param password - пароль
     * @param sendingFilePath - полный путь к загружаемому файлу
     * @param sentFilePath - целевой путь к файлу на FTP
     */
    public static void sendFileToFTP(String hostAddress, String login, String password, String sendingFilePath, String sentFilePath) {
        FTPClient ftpClient = new FTPClient();
        try {
            FileInputStream fileInputStream = new FileInputStream(sendingFilePath);
            ftpClient.connect(hostAddress);
            ftpClient.enterLocalPassiveMode();
            ftpClient.login(login, password);
            ftpClient.storeFile(sentFilePath, fileInputStream);
//            ftpClient.logout();
//            ftpClient.disconnect();
            System.out.println("Файл успешно отправлен на FTP");
        } catch (IOException ex) {
            System.err.println(ex);
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
        }
    }

    /**
     * Проверить наличие файла на FTP
     * @param hostAddress - адрес
     * @param login - логин
     * @param password - пароль
     * @param fileDirectoryFTP - папка на FTP, где ищется файл
     * @param fileName - имя разыскиваемого файла
     */
    public static void assertFileFTP(String hostAddress, String login, String password, String fileDirectoryFTP, String fileName) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(hostAddress);
            ftpClient.enterLocalPassiveMode();
            ftpClient.login(login, password);

            List<FTPFile> files = Arrays.asList(ftpClient.listFiles(fileDirectoryFTP));
            final FTPFile foundFilename = Iterables.find(files , new Predicate<FTPFile>() {
                @Override
                public boolean apply(FTPFile ftpFile) {
                    return fileName.equals(ftpFile.getName());
                }
            }, null);
            assert foundFilename != null;

            System.out.println("Файл " + fileName + " успешно найден на FTP");

        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.logout();
                    ftpClient.disconnect();
                } catch (IOException ioe) {
                    // do nothing
                }
            }
        }
    }

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

    /**
     * Отправить REST запрос (GET)
     * @param requestType - тип запроса
     * @param requestURL  - адрес запроса
     */
    public static String sendRESTRequest(String requestType, String requestURL) {
        String response = "";

        URL url;
        HttpURLConnection httpUrlConnection = null;

        if (requestType.equals("GET")) {
//            System.out.println("Выполняется REST-запрос с типом " + requestType + " по адресу: " + requestURL);
            try {
                url = new URL(requestURL);

                //создаем соединение
                httpUrlConnection = (HttpURLConnection) url.openConnection();

                //задаем параметры соединения
                httpUrlConnection.setRequestMethod("GET");
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setRequestProperty("Content-Type", "application/json");
                httpUrlConnection.setRequestProperty("Accept", "application/json");
                httpUrlConnection.setConnectTimeout(120000);
                httpUrlConnection.setReadTimeout(120000);

//                if (!String.valueOf(con.getResponseCode()).startsWith("2")) {
//                    System.out.println("Код ответа на REST-запрос: " + con.getResponseCode());
//                }

                //получаем ответ из входящего потока
                InputStream inputStream = httpUrlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();

                //закрываем входящий поток
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

                /*
                //с помощью InputStream получить ответ
                final StringBuilder content = new StringBuilder();
                try (final BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    response = content.toString();
                } catch (final Exception ex) {
//                    System.out.println("Не удалось получить ответ на REST-запрос!");
                    ex.printStackTrace();
                    return "";
                }
                 */

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }

        }
        return response;
    }

    /**
     * Отправить REST запрос (GET) с авторизацией
     * @param requestType - тип запроса
     * @param requestURL  - адрес запроса
     * @param login  - логин для авторизации
     * @param password  - пароль для авторизации
     */
    public static String sendRESTRequest(String requestType, String requestURL, String login, String password) {

        String response = "";

        URL url;
        HttpURLConnection httpUrlConnection = null;

        if (requestType.equals("GET")) {
//            System.out.println("Выполняется REST-запрос с типом " + requestType + " по адресу: " + requestURL);
            try {
                url = new URL(requestURL);

                //создаем соединение
                httpUrlConnection = (HttpURLConnection) url.openConnection();

                //задаем параметры соединения
                httpUrlConnection.setRequestMethod("GET");
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setRequestProperty("Authorization", "Basic " + CommonFunctions.encodeToBase64(login + ":" + password));
                httpUrlConnection.setRequestProperty("Content-Type", "application/json");
                httpUrlConnection.setRequestProperty("Accept", "application/json");
                httpUrlConnection.setConnectTimeout(120000);
                httpUrlConnection.setReadTimeout(120000);

//                if (!String.valueOf(httpUrlConnection.getResponseCode()).startsWith("2")) {
//                    System.out.println("Код ответа на REST-запрос: " + con.getResponseCode());
//                }

                //получаем ответ из входящего потока
                InputStream inputStream = httpUrlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();

                //закрываем входящий поток
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }

        }
        return response;
    }

    /**
     * Отправить REST запрос (PUT/POST)
     * @param requestType - тип запроса
     * @param requestURL  - адрес запроса
     * @param requestBody  - тело запроса
     * @param login  - логин для авторизации
     * @param password  - пароль для авторизации
     */
    public static String sendRESTRequest(String requestType, String requestURL, String requestBody, String login, String password) {

        String response = "";

        URL url;
        HttpURLConnection httpUrlConnection = null;
        DataOutputStream dataOutputStream = null;

        if (requestType.equals("PUT")) {
//            System.out.println("Выполняется REST-запрос с типом " + requestType + " по адресу: " + requestURL);
            try {
                url = new URL(requestURL);

                //создаем соединение
                httpUrlConnection = (HttpURLConnection) url.openConnection();

                //задаем параметры соединения
                httpUrlConnection.setRequestMethod("PUT");
                httpUrlConnection.setDoInput(true);
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setRequestProperty("Authorization", "Basic " + CommonFunctions.encodeToBase64(login + ":" + password));
                httpUrlConnection.setRequestProperty("Content-Type", "application/json");
                httpUrlConnection.setRequestProperty("Accept", "application/json");
                httpUrlConnection.setConnectTimeout(120000);
                httpUrlConnection.setReadTimeout(120000);

                //получаем исходящий поток
                OutputStream outputStream = httpUrlConnection.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                //записываем в исходящий поток тело запроса
                dataOutputStream.writeBytes(requestBody);
                //обновляем исходящий поток
                dataOutputStream.flush();

//                if (!String.valueOf(con.getResponseCode()).startsWith("2")) {
//                    System.out.println("Код ответа на REST-запрос: " + con.getResponseCode());
//                }

                //получаем ответ из входящего потока
                InputStream inputStream = httpUrlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();

                //закрываем входящий поток
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

                //закрываем исходящий поток
                dataOutputStream.close();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.flush();
                        dataOutputStream.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }

        }

        if (requestType.equals("POST")) {
//            System.out.println("Выполняется REST-запрос с типом " + requestType + " по адресу: " + requestURL);
            try {
                url = new URL(requestURL);

                //создаем соединение
                httpUrlConnection = (HttpURLConnection) url.openConnection();

                //задаем параметры соединения
                httpUrlConnection.setRequestMethod("POST");
                httpUrlConnection.setDoInput(true);
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setRequestProperty("Authorization", "Basic " + CommonFunctions.encodeToBase64(login + ":" + password));
                httpUrlConnection.setRequestProperty("Content-Type", "application/json");
                httpUrlConnection.setRequestProperty("Accept", "application/json");
                httpUrlConnection.setConnectTimeout(120000);
                httpUrlConnection.setReadTimeout(120000);

                //получаем исходящий поток
                OutputStream outputStream = httpUrlConnection.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                //записываем в исходящий поток тело запроса
                dataOutputStream.writeBytes(requestBody);
                //обновляем исходящий поток
                dataOutputStream.flush();

//                if (!String.valueOf(con.getResponseCode()).startsWith("2")) {
//                    System.out.println("Код ответа на REST-запрос: " + con.getResponseCode());
//                }

                //получаем ответ из входящего потока
                InputStream inputStream = httpUrlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();

                //закрываем входящий поток
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

                //закрываем исходящий поток
                dataOutputStream.close();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.flush();
                        dataOutputStream.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }

        }
        return response;
    }

    /**
     * Отправить файл через REST запрос (PUT/POST)
     * @param requestType - тип запроса
     * @param requestURL  - адрес запроса
     * @param file  - файл
     * @param login  - логин для авторизации
     * @param password  - пароль для авторизации
     */
    public static String sendRESTRequest(String requestType, String requestURL, File file, String login, String password) {

        String response = "";

        URL url;
        HttpURLConnection httpUrlConnection = null;
        DataOutputStream dataOutputStream = null;

        String filename = file.getName();

        String twoHyphens = "--";   // Define connection strings
        String boundary = "******"; // Define the delimitation string
        String end = "\r\n";    //Define end newline string

        if (requestType.equals("POST")) {
//            System.out.println("Выполняется REST-запрос с типом " + requestType + " по адресу: " + requestURL);
            try {
                url = new URL(requestURL);

                //создаем соединение
                httpUrlConnection = (HttpURLConnection) url.openConnection();

                //задаем параметры соединения:
                httpUrlConnection.setRequestMethod("POST");
                httpUrlConnection.setDoInput(true);
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setRequestProperty("Authorization", "Basic " + CommonFunctions.encodeToBase64(login + ":" + password));
                httpUrlConnection.setRequestProperty("Accept", "*/*");
                httpUrlConnection.setRequestProperty("Connection", "keep-alive");
                httpUrlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                httpUrlConnection.setConnectTimeout(120000);
                httpUrlConnection.setReadTimeout(120000);

                //получаем исходящий поток
                OutputStream outputStream = httpUrlConnection.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);

                //записываем в исходящий поток разделитель
                dataOutputStream.writeBytes(twoHyphens + boundary + end);
                //записываем в исходящий поток параметр и имя файла
                dataOutputStream.writeBytes("Content-Disposition:form-data;name=file;filename=" + filename + end);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + attachmentName + "\";filename=\"" + attachmentFileName + "\"" + end);
                //записываем в исходящий поток закрывающий символ
                dataOutputStream.writeBytes(end);

                //создаем входящий поток для чтения файла
                FileInputStream fileInputStream = new FileInputStream(file);
                //преобразовываем файл в байты и записываем в исходящий поток
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fileInputStream.read(buffer)) != -1) {
                    dataOutputStream.write(buffer, 0, length);
                }
                //после записи файла в поток, записываем в поток закрывающий символ
                dataOutputStream.writeBytes(end);

                //закрываем входящий поток
                fileInputStream.close();

                //записываем в исходящий поток разделитель
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + end);
                //обновляем исходящий поток
                dataOutputStream.flush();

                /*
                //особая уличная магия
                FileInputStream fileInputStream = new FileInputStream(file);
                OutputStream outputStream = httpUrlConnection.getOutputStream();
                try {
                    outputStream.write(("--" + boundary + EOL +
                            "Content-Disposition: form-data; name=\"file\"; " +
                            "filename=\"" + filename + "\"" + EOL +
                            "Content-Type: application/octet-stream" + EOL + EOL)
                            .getBytes(StandardCharsets.UTF_8)
                    );
                    byte[] buffer = new byte[128];
                    int size = -1;
                    while (-1 != (size = fileInputStream.read(buffer))) {
                        outputStream.write(buffer, 0, size);
                    }
                    outputStream.write((EOL + "--" + boundary + "--" + EOL).getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                 */

//                if (!String.valueOf(httpUrlConnection.getResponseCode()).startsWith("2")) {
//                    System.out.println("Код ответа на REST-запрос: " + httpUrlConnection.getResponseCode());
//                }

                //получаем ответ из входящего потока
                InputStream inputStream = httpUrlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();

                //закрываем входящий поток
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();

                //закрываем исходящий поток
                dataOutputStream.close();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.flush();
                        dataOutputStream.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
                if (httpUrlConnection != null) {
                    httpUrlConnection.disconnect();
                }
            }

        }
        return response;
    }


}
