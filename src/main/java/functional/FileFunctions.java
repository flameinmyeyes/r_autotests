package functional;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static com.codeborne.selenide.Selenide.$;

public final class FileFunctions {

    /* Методы для работы с файлами */

    private volatile static File foundFile = null;

    /**
     * Сохранить значение в файле (как String)
     * @param wayFile - полный путь к файлу
     * @param text - сохраняемый текст
     */
    public static void writeValueFile(String wayFile, String text) {
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
    public static String readValueFile(String wayFile) {
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
     * Сравнить два PDF по строкам (+ по кол-ву страниц)
     * @param wayFileOne - полный путь к 1-ому файлу (проверяемому)
     * @param wayFileTwo - полный путь ко 2-ому файлу (шаблону)
     * @param compareCountsLists - сравнить количество листов (true/false)
     * @param lines - номера строк, которые необходимо сравнить, через запятую (0 без параметра)
     */
    public static boolean comparePDF(String wayFileOne, String wayFileTwo, boolean compareCountsLists, int ... lines) {
        boolean result = true;

        PDDocument document1 = null;
        PDDocument document2 = null;

        ArrayList<Integer> badLinesList = new ArrayList<>();

        System.out.println("Файл 1: " + wayFileOne);
        System.out.println("Файл 2: " + wayFileTwo);

        try {
            File file1 = new File(wayFileOne);
            document1 = PDDocument.load(file1);
            PDFTextStripper pdfStripper1 = new PDFTextStripper();
            String text1 = pdfStripper1.getText(document1);
            String[] ls1 = text1.split("\\r?\\n");

            File file2 = new File(wayFileTwo);
            document2 = PDDocument.load(file2);
            PDFTextStripper pdfStripper2 = new PDFTextStripper();
            String text2 = pdfStripper2.getText(document2);
            String[] ls2 = text2.split("\\r?\\n");

            if (compareCountsLists) {
                int count1 = document1.getNumberOfPages();
                int count2 = document2.getNumberOfPages();
                if (count1!=count2) result=false;
            }

            for (int i : lines) {
                if (i<1) break;
                i--;
                String str1 = ls1[i];
                System.out.println("файл 1 строка " + (i+1) + ": " + str1);
                String str2 = ls2[i];
                System.out.println("файл 2 строка " + (i+1) + ": " + str2);
                if (!str1.equals(str2)) {
                    result = false;
                    badLinesList.add((i+1));
                }

            }
        } catch (IOException ex){
//            System.out.println("Один из файлов для сравнения не обнаружен!");
            throw new RuntimeException(ex);
        } finally {
            try {
                document1.close();
                document2.close();
            } catch (IOException e) {
                //It's nothing!
            }
        }

        //вывод сообщения о строках, значения которых не не совпадают
        if(!badLinesList.isEmpty()) {
            System.out.println("Обнаружено несоответствие между файлами по строкам: " + badLinesList);
        }

        Assert.assertEquals(result, true);
        return result;
    }

    /**
     * Сравнить два RTF(word) по строкам
     * @param wayFileOne - полный путь к 1-ому файлу (проверяемому)
     * @param wayFileTwo - полный путь ко 2-ому файлу (шаблону)
     * @param lines - номера строк, которые необходимо сравнить, через запятую (0 без параметра)
     */
    public static boolean compareRTF(String wayFileOne, String wayFileTwo, int ... lines) {
        boolean result = true;

        FileInputStream fisOne = null;
        FileInputStream fisTwo = null;

        ArrayList<Integer> badLinesList = new ArrayList<>();

        System.out.println("Файл 1: " + wayFileOne);
        System.out.println("Файл 2: " + wayFileTwo);

        try {
            fisOne = new FileInputStream(wayFileOne);
            fisTwo = new FileInputStream(wayFileTwo);

            RTFEditorKit kit = new RTFEditorKit();
            Document doc = kit.createDefaultDocument();

            kit.read(fisOne, doc, 0);
            kit.read(fisTwo, doc, 0);

            //извлечь текст, и разделить по "\\r?\\n"
            String plainText1 = doc.getText(0, doc.getLength());
            String[] ls1 = plainText1.split("\\r?\\n");

            String plainText2 = doc.getText(0, doc.getLength());
            String[] ls2 = plainText2.split("\\r?\\n");

            for (int i : lines) {
                if (i < 1) break;
                i--;
                String str1 = ls1[i];
                System.out.println("файл 1 строка " + (i+1) + ": " + str1);
                String str2 = ls2[i];
                System.out.println("файл 2 строка " + (i+1) + ": " + str2);
                if (!str1.equals(str2)) {
                    result = false;
                    badLinesList.add((i+1));
                }
            }

        } catch (BadLocationException | IOException ex){
            System.out.println("Один из файлов для сравнения не обнаружен!");
            throw new RuntimeException(ex);
        } finally {
            try {
                fisOne.close();
                fisTwo.close();
            } catch (IOException e){
            }
        }

        //вывод сообщения о строках, значения которых не не совпадают
        if(!badLinesList.isEmpty()) {
            System.out.println("Обнаружено несоответствие между файлами по строкам: " + badLinesList);
        }

        Assert.assertEquals(result, true);
        return result;
    }

    /**
     * Сравнить два XLS по строкам
     * @param wayFileOne - полный путь к 1-ому файлу (проверяемому)
     * @param wayFileTwo - полный путь ко 2-ому файлу (шаблону)
     * @param lines - номера строк, которые необходимо сравнить, через запятую (как в XLS-файле)
     * @param sheetNum - номер листа в XLS-файле
     */
    public static boolean compareXLS(String wayFileOne, String wayFileTwo, int sheetNum, int ... lines) {
        boolean result = true;

        FileInputStream fisOne = null;
        FileInputStream fisTwo = null;

        ArrayList<Integer> badLinesList = new ArrayList<>();

        System.out.println("Файл 1: " + wayFileOne);
        System.out.println("Файл 2: " + wayFileTwo);

        try {
            fisOne = new FileInputStream(new File(wayFileOne));
            fisTwo = new FileInputStream(new File(wayFileTwo));

            //проверка формата файла
            if(wayFileOne.endsWith(".xls") || wayFileOne.endsWith(".XLS")) {

                HSSFWorkbook workbookOne = new HSSFWorkbook(fisOne);
                HSSFWorkbook workbookTwo = new HSSFWorkbook(fisTwo);
                HSSFSheet sheetFileOne = workbookOne.getSheetAt(sheetNum);
                HSSFSheet sheetFileTwo = workbookTwo.getSheetAt(sheetNum);
                HSSFRow rowFileOne;
                HSSFRow rowFileTwo;
                HSSFCell cellFileOne;
                HSSFCell cellFileTwo;

                //цикл по сравнению строк
                for (int rowNum : lines) {
                    if (rowNum < 1) break;
                    rowNum--;

                    rowFileOne = sheetFileOne.getRow(rowNum);
                    rowFileTwo = sheetFileTwo.getRow(rowNum);

                    //общее число ячеек в каждой строке (по номеру последней ячейки)
                    int cellNumFileOne = rowFileOne.getLastCellNum();
                    int cellNumFileTwo = rowFileTwo.getLastCellNum();

                    //вытаскиваем значения всех ячеек в строке, и составляем из них строку (файл 1)
                    String lineFileOne = "";
                    for(int i = 1; i <= cellNumFileOne; i++) {
                        cellFileOne = rowFileOne.getCell(i - 1);
                        lineFileOne = (lineFileOne + cellFileOne).replace("null", "");
                    }
                    System.out.println("файл 1 лист " + sheetNum + " строка " + (rowNum+1) + ": " + lineFileOne);

                    //вытаскиваем значения всех ячеек в строке, и составляем из них строку (файл 2)
                    String lineFileTwo = "";
                    for(int i = 1; i <= cellNumFileTwo; i++) {
                        cellFileTwo = rowFileTwo.getCell(i - 1);
                        lineFileTwo = (lineFileTwo + cellFileTwo).replace("null", "");
                    }
                    System.out.println("файл 2 лист " + sheetNum + " строка " + (rowNum+1) + ": " + lineFileTwo);

                    //проверка, что строки равны
                    if(!lineFileOne.equals(lineFileTwo)) {
                        result = false;
                        badLinesList.add((rowNum+1));
                    }
                }

            } else if(wayFileOne.endsWith(".xlsx") || wayFileOne.endsWith(".XLSX")) {

                XSSFWorkbook workbookOne = new XSSFWorkbook(fisOne);
                XSSFWorkbook workbookTwo = new XSSFWorkbook(fisTwo);
                XSSFSheet sheetFileOne = workbookOne.getSheetAt(sheetNum);
                XSSFSheet sheetFileTwo = workbookTwo.getSheetAt(sheetNum);
                XSSFRow rowFileOne;
                XSSFRow rowFileTwo;
                XSSFCell cellFileOne;
                XSSFCell cellFileTwo;

                //цикл по сравнению строк
                for (int rowNum : lines) {
                    if (rowNum < 1) break;
                    rowNum--;

                    rowFileOne = sheetFileOne.getRow(rowNum);
                    rowFileTwo = sheetFileTwo.getRow(rowNum);

                    //общее число ячеек в каждой строке (по номеру последней ячейки)
                    int cellNumFileOne = rowFileOne.getLastCellNum();
                    int cellNumFileTwo = rowFileTwo.getLastCellNum();

                    //вытаскиваем значения всех ячеек в строке, и составляем из них строку (файл 1)
                    String lineFileOne = "";
                    for(int i = 1; i <= cellNumFileOne; i++) {
                        cellFileOne = rowFileOne.getCell(i - 1);
                        lineFileOne = (lineFileOne + cellFileOne).replace("null", "");
                    }
                    System.out.println("файл 1 лист " + sheetNum + " строка " + (rowNum+1) + ": " + lineFileOne);

                    //вытаскиваем значения всех ячеек в строке, и составляем из них строку (файл 2)
                    String lineFileTwo = "";
                    for(int i = 1; i <= cellNumFileTwo; i++) {
                        cellFileTwo = rowFileTwo.getCell(i - 1);
                        lineFileTwo = (lineFileTwo + cellFileTwo).replace("null", "");
                    }
                    System.out.println("файл 2 лист " + sheetNum + " строка " + (rowNum+1) + ": " + lineFileTwo);

                    //проверка, что строки равны
                    if(!lineFileOne.equals(lineFileTwo)) {
                        result = false;
                        badLinesList.add((rowNum+1));
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("Один из файлов для сравнения не обнаружен!");
            e.printStackTrace();
        } finally {
            try {
                fisOne.close();
                fisTwo.close();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        //вывод сообщения о строках, значения которых не не совпадают
        if(!badLinesList.isEmpty()) {
            System.out.println("Обнаружено несоответствие между файлами по строкам: " + badLinesList);
        }

        Assert.assertEquals(result, true);
        return result;
    }

    /**
     * Сравнить два ODS по строкам (БЕТА)
     * @param wayFileOne - полный путь к 1-ому файлу (проверяемому)
     * @param wayFileTwo - полный путь ко 2-ому файлу (шаблону)
     * @param lines - номера строк, которые необходимо сравнить, через запятую (как в XLS-файле)
     * @param sheetNum - номер листа в XLS-файле
     */
    public static boolean compareODS(String wayFileOne, String wayFileTwo, int sheetNum, int ... lines) {

        //с помощью либы SODS
        //в случае с объединенными ячейками, выводит их значение для каждой из объединенных ячеек (доработано)

        boolean result = true;

        SpreadSheet spreadsheetFileOne = null;
        SpreadSheet spreadsheetFileTwo = null;

        ArrayList<Integer> badLinesList = new ArrayList<>();

        System.out.println("Файл 1: " + wayFileOne);
        System.out.println("Файл 2: " + wayFileTwo);

        try {
            File fileOne = new File(wayFileOne);
            File fileTwo = new File(wayFileTwo);

            spreadsheetFileOne = new SpreadSheet(fileOne);
            spreadsheetFileTwo = new SpreadSheet(fileTwo);

            Sheet sheetFileOne = spreadsheetFileOne.getSheet(sheetNum);
            Sheet sheetFileTwo = spreadsheetFileTwo.getSheet(sheetNum);

            //Считаем количество столбцов и строк
            int nColCount = sheetFileOne.getMaxColumns();
            int nRowCount = sheetFileOne.getMaxRows();
//            System.out.println("Число столбцов: " + nColCount);
//            System.out.println("Число строк: " + nRowCount);

            Range cellFileOne = null;
            Range cellFileTwo = null;

            //цикл по сравнению строк
            for(int rowNum : lines) {
                if (rowNum < 1) break;
                rowNum--;

                //Iterating through each column
                //вытаскиваем значения всех ячеек в строке, и составляем из них строку (файл 1)
                String lineFileOne = "";
                //
                boolean isPartOfMergeFileOne = false;
                String cellValueFileOne = "";
                String previousCellValueFileOne = "";
                //
                for(int nColIndex = 0; nColIndex < nColCount; nColIndex++) {
                    cellFileOne = sheetFileOne.getRange(rowNum, nColIndex);

                    //РЕШЕНИЕ ПРОБЛЕМЫ С ВЫВОДОМ ЗНАЧЕНИЙ ОБЪЕДИНЕННЫХ ЯЧЕЕК
                    //проверяем, merged ли ячейка
                    isPartOfMergeFileOne = cellFileOne.isPartOfMerge();
                    //проверяем значение ячейки
                    cellValueFileOne = String.valueOf(cellFileOne.getValue());
                    //если ячейка merged, и ее значение совпадает с предыдущим, то пропускаем ее
                    if(!(isPartOfMergeFileOne && cellValueFileOne.equals(previousCellValueFileOne))) {
                        lineFileOne = (lineFileOne + cellFileOne.getValue()).replace("null", "");
                    }
                    //перезаписываем значение предыдущей ячейки
                    previousCellValueFileOne = cellValueFileOne;
                }
                System.out.println("файл 1 лист " + sheetNum + " строка " + (rowNum+1) + ": " + lineFileOne);

                //вытаскиваем значения всех ячеек в строке, и составляем из них строку (файл 2)
                String lineFileTwo = "";
                //
                boolean isPartOfMergeFileTwo = false;
                String cellValueFileTwo = "";
                String previousCellValueFileTwo = "";
                //
                for(int nColIndex = 0; nColIndex < nColCount; nColIndex++) {
                    cellFileTwo = sheetFileTwo.getRange(rowNum, nColIndex);

                    //РЕШЕНИЕ ПРОБЛЕМЫ С ВЫВОДОМ ЗНАЧЕНИЙ ОБЪЕДИНЕННЫХ ЯЧЕЕК
                    //проверяем, merged ли ячейка
                    isPartOfMergeFileTwo = cellFileTwo.isPartOfMerge();
                    //проверяем значение ячейки
                    cellValueFileTwo = String.valueOf(cellFileTwo.getValue());
                    //если ячейка merged, и ее значение совпадает с предыдущим, то пропускаем ее
                    if(!(isPartOfMergeFileTwo && cellValueFileTwo.equals(previousCellValueFileTwo))) {
                        lineFileTwo = (lineFileTwo + cellFileTwo.getValue()).replace("null", "");
                    }
                    //перезаписываем предыдущее значение ячейки
                    previousCellValueFileTwo = cellValueFileTwo;
                }
                System.out.println("файл 2 лист " + sheetNum + " строка " + (rowNum+1) + ": " + lineFileTwo);

                //проверка, что строки равны
                if(!lineFileOne.equals(lineFileTwo)) {
                    result = false;
                    badLinesList.add((rowNum+1));
                }
            }

        } catch (IOException e) {
            System.out.println("Один из файлов для сравнения не обнаружен!");
            e.printStackTrace();
        }

        //вывод сообщения о строках, значения которых не не совпадают
        if(!badLinesList.isEmpty()) {
            System.out.println("Обнаружено несоответствие между файлами по строкам: " + badLinesList);
        }

        Assert.assertEquals(result, true);
        return result;
    }

    /**
     * Проверить, не пустой ли файл
     * @param wayFile - полный путь к файлу
     * @param minKiloBytes - минимальный размер файла, в КБ
     */
    public static boolean isNotEmptyFile(String wayFile, long minKiloBytes){
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
            writeValueFile(wayFileTwo, contents);
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
     * Проверить на ключевые значения value
     * @param value -
     */
    @Deprecated
    public static String compareValues(String value){
        String val = "";

        if (new File(value).exists()){
            val = readValueFile(value);
        } else if(value.equals("Сегодня") || value.equals("Today")){
            val = CommonFunctions.dateToday("dd.MM.yyyy");}
        else if (value.equals("Число") || value.equals("Number")) {
            val = CommonFunctions.randomNumber(111, 999);
        } else{
            val = value;
        }
        return val;
    }

    /**
     * Обновить XML-файл по тэгу
     * @param filePath - полный путь к файлу
     * @param xmlTag - название тэга
     * @param tagValue - желаемое значение тега
     */
    public static void updateXML(String filePath, String xmlTag, String tagValue) {
        tagValue = compareValues(tagValue);
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
        tagValue = compareValues(tagValue);
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
     * Прочесть значение ячейки из таблицы XLS/XLSX
     */
    //чтобы узнать номер столбца, в настройках установить чек-бокс: "Параметры" - "Формулы" - "Стиль ссылок R1C1"
    public static String getXLSCellValue(String filePath, String sheetName, int rowNum, int columnNum) {
        String cellValue = null;

        try {
            //получаем файл в формате xlsx
            FileInputStream file = new FileInputStream(new File(filePath));
            DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            //проверка формата файла
            if(filePath.endsWith(".xls") || filePath.endsWith(".XLS")) {
                HSSFWorkbook workbook = new HSSFWorkbook(file);
                HSSFSheet sheet = workbook.getSheet(sheetName);
                HSSFRow row = sheet.getRow(rowNum - 1);
                HSSFCell cell = row.getCell(columnNum - 1);

                if (cell.getCellType().equals(CellType.STRING)) {
                    cellValue = cell.getStringCellValue();
                } else {
                    cellValue = format.format(cell.getDateCellValue());
                }

            } else if(filePath.endsWith(".xlsx")  || filePath.endsWith(".XLSX")) {
                XSSFWorkbook workbook = new XSSFWorkbook(file);
                XSSFSheet sheet = workbook.getSheet(sheetName);
                XSSFRow row = sheet.getRow(rowNum - 1);
                XSSFCell cell = row.getCell(columnNum - 1);

                if (cell.getCellType().equals(CellType.STRING)) {
                    cellValue = cell.getStringCellValue();
                } else {
                    cellValue = format.format(cell.getDateCellValue());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellValue;
    }

    //переделать под Assert
    public static Boolean checkTextIsInPDF(String filePath, String textForCheck) {
        File file = new File(filePath);
        PDFParser parser;
        try {
            parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            COSDocument cosDocument = parser.getDocument();
            PDFTextStripper pdfStripper = new PDFTextStripper();
            PDDocument pdDocument = new PDDocument(cosDocument);
            pdDocument.getNumberOfPages();
            pdfStripper.setStartPage(0);
            pdfStripper.setEndPage(pdDocument.getNumberOfPages());
            return pdfStripper.getText(pdDocument).contains(textForCheck);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
        return false;
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
        new MainPage().waitForLoading(60);

//        System.out.println("Файл для записи не обнаружен. Будет создан новый файл");
//        file = new File(wayToDownloadFile);
//        CommonFunctions.wait(5);
//        Assert.assertTrue(new File(wayToDownloadFile).exists());

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
