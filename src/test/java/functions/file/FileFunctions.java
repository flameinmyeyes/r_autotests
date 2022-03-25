package functions.file;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import functions.common.CommonFunctions;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.testng.Assert;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.$;

public class FileFunctions {

    /* Методы для работы с файлами */

    private volatile static File foundFile = null;

    /**
     * Сохранить значение в файле (как String)
     * @param wayFile - полный путь к файлу
     * @param text - сохраняемый текст
     */
    public static void writeValueToFile(String wayFile, String text) {
        File file = new File(wayFile);

        // Создать папку дирректорию при ее отсутствии
        File dir = new File(file.getParent());
        String dirStr = dir.toString();
        if (!dir.exists()){
            System.out.println("Дирректории " + dirStr + " не существует");
            if (dir.mkdirs()) System.out.println("Дирректория " + dirStr + " была создана");
            else Assert.fail("Не удалось создать дирректорию" + dirStr);
        }

//        if (!file.exists()) {
//            System.out.println("Файл для записи не обнаружен. Будет создан новый файл");
//            file = new File(wayFile);
//            CommonFunctions.wait(5);
////            Assert.assertTrue(new File(wayFile).exists());
//        }

        try (FileWriter writer = new FileWriter(wayFile)) {
            writer.write(text);
            System.out.println("Значение «" + text + "» было записано в файл " + file.toString());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Прочитать значение из файла (в String)
     * @param wayFile - полный путь к файлу
     */
    public static String readValueFromFile(String wayFile) {
        StringBuilder value = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(wayFile));
            String v;
            while((v = reader.readLine())!=null){
                value.append(v);
            }

        } catch (IOException ex) {
            System.out.println("Файл: " + wayFile + " не найден!");
            throw new RuntimeException(ex);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                //It's nothing!
            }
        }
        return value.toString();
    }

    /**
     * Проверить, не пустой ли файл
     * @param wayFile - полный путь к файлу
     * @param minKiloBytes - минимальный размер файла, в КБ
     */
    public static boolean assertFileIsNotEmpty(String wayFile, long minKiloBytes){
        boolean result = true;
        File document = new File(wayFile);
        if (!document.exists() || getFileSizeKiloBytes(document) < minKiloBytes) result = false;
        Assert.assertEquals(result, true);
        return result;
    }

    /**
     * Получить размер файла (в килобайтах)
     * @param file - полный путь к файлу
     */
    public static long getFileSizeKiloBytes(File file) {
        long fileSizeKiloBytes = file.length()/1024;
        System.out.println("Размер файла: " + fileSizeKiloBytes + " КБ");
        return fileSizeKiloBytes;
    }

    /**
     * Перезапись файла со своими значениями
     * @param wayFileOne - путь к 1-ому файлу
     * @param wayFileTwo - путь ко 2-ому файлу
     * @param mapReplacement - Map<Параметр который заменяем, Значение на которое меняем>
     */
    public static void overwriteFile(String wayFileOne, String wayFileTwo, Map<String, String> mapReplacement){
        String contents;
        try {
            contents = readUsingScanner(wayFileOne);
            for (Map.Entry<String, String> replace : mapReplacement.entrySet()) {
                contents = contents.replace(replace.getKey(), replace.getValue());
            }
            System.out.println(contents);
            writeValueToFile(wayFileTwo, contents);
        } catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    @Deprecated
    private static String readUsingScanner(String fileName) throws IOException {
        Scanner scanner = new Scanner(Paths.get(fileName), StandardCharsets.UTF_8.name());
        //здесь мы можем использовать разделитель, например: "\\A", "\\Z" или "\\z"
        String data = scanner.useDelimiter("\\A").next();
        scanner.close();
        return data;
    }

    public static void replaceParameterFile(String filePath, String filePathNew, String Parameter, String Value, String charsetName) {
        String fileText = new String();
        try {
            fileText = readUsingScanner(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Not reading file " + e);
        }

        fileText = fileText.replaceAll(Parameter,Value);
        Writer out = null;
        try {
            File fileDir = new File(filePathNew);
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileDir), charsetName));

            out.write(fileText);

        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        } finally {
            try {
                out.flush();
                out.close();
            } catch (Exception e){
                //It's nothing!
            }
        }
    }

    /**
     * Перенести найденный файл из папки загрузок в пользовательскую папку
     * @param containsName - часть имени разыскиваемого файла
     * @param wayInCopy - полный путь папки, в которую переносится файл
     */
    /*
     * По умолчанию selenide сохраняет файлы в "".\\build\\downloads""
     * если в настройках запуска папка сохранения не была изменена, то по умолчанию файл скачается в вышеуказанную папку
     * Принцип действия:
     * 1) ищем файл по имени в папке
     * 2) копируем файл в др. директорию для дальнейшего использования
     * 3) исходник удален
     */
    @Deprecated
    public static synchronized void cutDocumentOfDirectories(String containsName, String wayInCopy){
        String waySearch = "Z:\\files_for_tests\\downloads";
        File file = searchFileInDirectories(new File(waySearch), containsName);

        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(file);
            os = new FileOutputStream(wayInCopy);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (IOException  e){
            throw new RuntimeException(e);
        }
        finally {
            try {
                is.close();
                os.close();
            } catch (NullPointerException | IOException e)
            {
                throw new RuntimeException(e);
            }

            file.delete();

//            if(!file.getParent().equals("downloads")) {
//                new File(file.getParent()).delete();
//            } else {
//                file.delete();
//            }

        }
    }

    /**
     * Перенести файл из одной папки другую
     * @param wayToSourceFile - полный путь к файлу в изначальной папке
     * @param wayToDestinationFile - полный путь к файлу в целевой папке
     */
    public static void moveFileToFolder(String wayToSourceFile, String wayToDestinationFile) {

        int maxTimeForMovingFile = 240; //максимальное время ожидания перемещения файла

        if (wayToSourceFile.equals("null")) {
            Assert.fail("Ошибка перемещения файла. Путь к файлу в изначальной папке не может быть равен \"null\"");
        }

        wayToSourceFile = wayToSourceFile.replace(".crdownload", "");

        File sourceFile = new File(wayToSourceFile);
        File destinationFile = new File(wayToDestinationFile);
        File destinationPath = new File(destinationFile.getParent());

        // проверить наличие папки, если ее нет - создать
        if (!destinationPath.exists()) {
            System.out.println("Директория " + destinationPath.toString() + " отсутствует на диске, она будет создана впервые");
            destinationPath.mkdir();
        }

        // Удалить файл с таким же именем в целефой папке, если он существует
        if (destinationFile.delete()) {
            System.out.println("В целевой директории " + destinationFile.getParent()
                    + " уже существует файл с именем " + destinationFile.getName()
                    + ". Он будет заменен на новый");
            CommonFunctions.wait(1);
        }

        System.out.println("Ожидание перемещения файла: " + destinationFile.getName() + " из директории  "
                + sourceFile.getParent() + " в директорию " + destinationFile.getParent());
        // Переместить файл
        if (sourceFile.renameTo(destinationFile)) {
            int n = 0;
            while (Files.notExists(Paths.get(wayToDestinationFile)) && n < maxTimeForMovingFile) {
                CommonFunctions.wait(1);
                n++;
            }
            if (destinationFile.exists()) {
                System.out.println("Файл " + destinationFile.getName() + " успешно перемещен");
            } else {
                Assert.fail("Ошибка перемещения файла. Не удалось переместить файл " + destinationFile.getName()
                        + " в директорию " + destinationFile.getParent());
            }
        } else {
            Assert.fail("Ошибка перемещения файла. Не удалось найти файл " + sourceFile.getName()
                    + " в директории " + sourceFile.getParent());
        }
    }

    /**
     * Поиск файла в папке по части его имени
     * @param folder - папка, в которой разыскивается файл (new File(WAY_TEST + "\\имя_файла"))
     * @param containsName - часть имени разыскиваемого файла
     */
    public static synchronized File searchFileInDirectories(final File folder, String containsName) {
        System.out.println("Поиск файла с именем, содержащим: " + containsName);

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                searchFileInDirectories(fileEntry, containsName);
            } else {
                if (fileEntry.getAbsolutePath().contains(containsName))
                    foundFile = fileEntry;
            }
        }
        System.out.println("Найден файл: " + foundFile);
        return foundFile;
    }

    /**
     * Поиск файла в папке по части его имени (с доп. атрибутом поиска)
     * @param folder - папка, в которой разыскивается файл (new File(WAY_TEST + "\\имя_файла"))
     * @param containsName - часть имени разыскиваемого файла
     * @param notContainsName - имя файла не должно содержать
     */
    public static synchronized File searchFileInDirectories(final File folder, String containsName, String notContainsName) {
        System.out.println("Поиск файла с именем, содержащим: " + containsName + ", и не содержащим: " + notContainsName);

        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                searchFileInDirectories(fileEntry, containsName, notContainsName);
            } else {
                if (fileEntry.getAbsolutePath().contains(containsName) && !fileEntry.getAbsolutePath().contains(notContainsName))
                    foundFile = fileEntry;
            }
        }
        System.out.println("Найден файл: " + foundFile);
        return foundFile;
    }

    /**
     * Рекурсивный поиск файла в папке по части его имени (т.е. поиск внутри папки с папками)
     * @param folder - папка, в которой разыскивается файл (new File(WAY_TEST + "\\имя_файла"))
     * @param containsName - часть имени разыскиваемого файла
     */
    @Deprecated
    public static synchronized File searchFileInDirectoriesRecursive_old(final File folder, String containsName) {
        System.out.println("Поиск файла с именем, содержащим: " + containsName);

        try {
            boolean recursive = true;

            Collection files = FileUtils.listFiles(folder, null, recursive);

            //проверяем 100%-ное совпадение по имени файла
            for (Iterator iterator = files.iterator(); iterator.hasNext();) {
                File file = (File) iterator.next();
                if (file.getAbsolutePath().equals(containsName)) {
                    foundFile = new File(file.getAbsolutePath());
                    System.out.println("Найден файл с полным совпадением по имени: " + foundFile);
                    return foundFile;
                }
            }

            //проверяем совпадение по части имени файла
            ArrayList<File> foundFilesList = new ArrayList<>();
            for (Iterator iterator = files.iterator(); iterator.hasNext();) {
                File file = (File) iterator.next();
                if (file.getAbsolutePath().contains(containsName)) {
                    foundFile = new File(file.getAbsolutePath());
                    foundFilesList.add(foundFile);
                }
            }
            if (foundFilesList.size() > 1) {
                System.out.println(foundFilesList);
                throw new Exception("Найдено более 1 файла с именем, содержащим: " + containsName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Найден файл: " + foundFile);
        return foundFile;
    }

    /**
     * Рекурсивный поиск файла в папке по части его имени (т.е. поиск внутри папки с папками)
     * @param folder - папка, в которой разыскивается файл (new File(WAY_TEST + "\\имя_файла"))
     * @param containsName - часть имени разыскиваемого файла
     */
    public static synchronized File searchFileInDirectoriesRecursive(final File folder, String containsName) {
        System.out.println("Поиск файла с именем, содержащим: " + containsName);

        CommonFunctions.wait(30);

        try {
            boolean recursive = true;

            List<File> files = (List<File>) FileUtils.listFiles(folder, null, recursive);

            //ищем 100%-ное совпадение по имени файла
            foundFile = Iterables.find(files, new Predicate<File>() {
                @Override
                public boolean apply(File file) {
                    return file.getName().equals(containsName);
                }
            }, null);

            if (foundFile != null) {
                System.out.println("Найден файл с полным совпадением по имени: " + foundFile);
            }

            //если не нашли, ищем совпадение по части имени файла
            if (foundFile == null) {
                ArrayList<File> foundFilesList = new ArrayList<>();
                foundFile = Iterables.find(files, new Predicate<File>() {
                    @Override
                    public boolean apply(File file) {
                        return file.getName().contains(containsName);
                    }
                }, null);
                foundFilesList.add(foundFile);

                //проверяем, что найдено не более 1 файла:
                if (foundFilesList.size() > 1) {
                    System.out.println(foundFilesList);
                    throw new Exception("Найдено более 1 файла с именем, содержащим: " + containsName);
                }

                if (foundFile != null) {
                    System.out.println("Найден файл с совпадением по части имени: " + foundFile);
                }

            }

            if (foundFile == null) {
                throw new Exception("Файл не найден!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return foundFile;
    }

    /**
     * Дождаться окончания загрузки файла по его полному пути <wayToSourceFile>
     * @param wayToSourceFile - полный путь загружаемого файла
     * @param seconds - максимальное возможное время загрузки файла
     */
    public static void waitForFileDownload(String wayToSourceFile, int seconds) {
        if (wayToSourceFile.equals("null")) {
            Assert.fail("Ошибка загрузки файла. Путь загружаемого файла не может быть равен \"null\".");
        }
        wayToSourceFile = wayToSourceFile.replace(".crdownload", "");
        int n = 0;
        long start = System.nanoTime();
        System.out.println("Ожидание загрузки файла: " + wayToSourceFile);
        while (Files.notExists(Paths.get(wayToSourceFile)) && n < seconds) {
            CommonFunctions.wait(1);
            n ++;
        }
        long stop = System.nanoTime();
        double timeTransaction = TimeUnit.MILLISECONDS.convert(stop - start, TimeUnit.NANOSECONDS) / 1000.0;
        if (new File(wayToSourceFile).exists()) {
            System.out.println("Файл успешно загружен за " + timeTransaction + " c.");
            FileFunctions.getFileSizeKiloBytes(new File(wayToSourceFile));
        } else {
            Assert.fail("Ошибка загрузки файла. Не удалось загрузить файл за " + seconds + " с.");
        }
    }

    /**
     * Перенести файл из одной папки другую
     * @param locator - полный путь к файлу в изначальной папке
     */
    @Deprecated
    public static void clickToDownloadFile(By locator, String wayToDownloadFile) {
        File file = null;
        try {
            file = $(locator).download();
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommonFunctions.wait(1);
//        new MainPage().waitForLoading(60);

//        System.out.println("Файл для записи не обнаружен. Будет создан новый файл");
//        file = new File(wayToDownloadFile);
//        CommonFunctions.wait(5);
//        Assert.assertTrue(new File(wayToDownloadFile).exists());

    }

}
