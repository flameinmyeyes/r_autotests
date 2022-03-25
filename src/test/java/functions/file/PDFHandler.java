package functions.file;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PDFHandler {

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


}
