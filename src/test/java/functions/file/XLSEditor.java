package functions.file;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class XLSEditor {

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



}
