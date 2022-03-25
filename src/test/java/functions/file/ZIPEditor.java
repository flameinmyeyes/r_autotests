package functions.file;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import functions.common.CommonFunctions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZIPEditor {

    /**
     * Распаковка zip архива
     * @param wayToZip - полный путь к файлу архива
     * @param wayUnpackingFile - полный путь для распаковываемых файлов
     */
    public static void unpackingZip(String wayToZip,String wayUnpackingFile){
        //проверяем, есть ли старая версия файла с таким именем, если есть - удаляем
        File file = new File(wayUnpackingFile);
        try {
            wayUnpackingFile = file.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Charset CP866 = Charset.forName("CP866");
        try(ZipInputStream zin = new ZipInputStream(new FileInputStream(wayToZip),CP866))
        {
            ZipEntry entry;
            String name;
            long size;
            while((entry=zin.getNextEntry())!=null){

                name = entry.getName(); // получим название файла
                size = entry.getSize();  // получим его размер в байтах
                System.out.printf("File name: %s \t File size: %d \n", name, size);
                if (wayUnpackingFile.equals("")) ;
                // распаковка
                FileOutputStream fout = new FileOutputStream(wayUnpackingFile);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Распаковка zip-архива в папку
     * @param wayToZip - полный путь к файлу zip-архива
     * @param wayUnpackingFolder - полный путь к папке для распакованных файлов (папка создается автоматически)
     */
    public static void unpackZipIntoFolder(String wayToZip, String wayUnpackingFolder) {
        //если папка с таким наименованием уже существует, то удалить из нее все файлы
        File folder = new File(wayUnpackingFolder);
        File[] listFiles = folder.listFiles();
        if (folder.exists()) {
            for (File file:listFiles) {
                if (file.isFile()) {
                    file.delete();
                    CommonFunctions.wait(1);
                }
            }
            CommonFunctions.wait(5);

            //затем удалить саму папку
            try {
                Files.deleteIfExists(Paths.get(wayUnpackingFolder));
                CommonFunctions.wait(5);
            } catch (Exception e) {}
        }

        //затем создать папку заново
        if (!folder.exists()) {
            folder = new File(wayUnpackingFolder);
            folder.mkdir();
            CommonFunctions.wait(5);
        }

        //

        FileInputStream fis = null;
        ZipInputStream zis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(wayToZip);
            zis = new ZipInputStream(fis, Charset.forName("CP866"));
            ZipEntry entry;
            String name;
            long size;

            while((entry = zis.getNextEntry()) != null) {
                name = entry.getName(); //получим название файла
                size = entry.getSize(); //получим его размер в байтах
                System.out.printf("File name: %s \t File size: %d \n", name, size);
                String wayUnpackingFile = wayUnpackingFolder + "\\" + name;
                fos = new FileOutputStream(wayUnpackingFile);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    //записать прочитанный из архива файл
                    fos.write(c);
                }
                fos.flush();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                zis.closeEntry();
                zis.close();
                fis.close();
                fos.close();
            } catch (Exception e) {}
        }
    }

    /**
     * Проверка наличия файла в zip-архиве (по части имени)
     * @param wayToZip - полный путь к файлу zip-архива
     * @param containsName - часть имени разыскиваемого файла
     */
    public static void assertFileInZip(String wayToZip, String containsName) {
        FileInputStream fis = null;
        ZipInputStream zis = null;

        try {
            fis = new FileInputStream(wayToZip);
            zis = new ZipInputStream(fis, Charset.forName("CP866"));
            ZipEntry entry;
            String name;
            ArrayList<String> names = new ArrayList<>();

            //получим имя каждого файла и добавим его в ArrayList
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                System.out.println("File name: " + name);
                names.add(name);
            }

            //проверим наличие элемента в списке
            final String foundFileName = Iterables.find(names, new Predicate<String>() {
                @Override
                public boolean apply(String fileName) {
                    return fileName.contains(containsName);
                }
            }, null);
            assert foundFileName != null;
            System.out.println("Требуемый файл успешно найден: " + foundFileName);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                zis.closeEntry();
                zis.close();
                fis.close();
            } catch (Exception e) {
                //do nothing
            }
        }
    }

    /**
     * Проверка наличия файла в zip-архиве (по части имени) (с доп. атрибутом поиска)
     * @param wayToZip - полный путь к файлу zip-архива
     * @param containsName - часть имени разыскиваемого файла
     * @param notContainsName - имя разыскиваемого файла НЕ должно содержать
     */
    public static void assertFileInZip(String wayToZip, String containsName, String notContainsName) {
        FileInputStream fis = null;
        ZipInputStream zis = null;

        try {
            fis = new FileInputStream(wayToZip);
            zis = new ZipInputStream(fis, Charset.forName("CP866"));
            ZipEntry entry;
            String name;
            ArrayList<String> names = new ArrayList<>();
            //получим имя каждого файла и добавим его в ArrayList
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                System.out.println("File name: " + name);
                names.add(name);
            }
            //проверим наличие элемента в списке
            final String foundFileName = Iterables.find(names, new Predicate<String>() {
                @Override
                public boolean apply(String fileName) {
                    return (fileName.contains(containsName) && !fileName.contains(notContainsName));
                }
            }, null);
            assert foundFileName != null;
            System.out.println("Требуемый файл успешно найден: " + foundFileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                zis.closeEntry();
                zis.close();
                fis.close();
            } catch (Exception e) {
                //do nothing
            }
        }
    }

    public static void packToZipVirtualDisk(String sourceDirPath, String zipFilePath) throws IOException {
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                        try {
                            zs.putNextEntry(zipEntry);
                            Files.copy(path, zs);
                            zs.closeEntry();
                        } catch (IOException e) {
                            System.err.println(e);
                        }
                    });
        }
    }

}
