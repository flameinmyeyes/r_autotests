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

public class FTPFunctions {

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


}
