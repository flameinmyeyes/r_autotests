package functions.file;

import org.testng.Assert;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RTFEditor {

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

}
