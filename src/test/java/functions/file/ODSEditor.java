package functions.file;

import com.github.miachm.sods.Range;
import com.github.miachm.sods.Sheet;
import com.github.miachm.sods.SpreadSheet;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ODSEditor {

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

}
