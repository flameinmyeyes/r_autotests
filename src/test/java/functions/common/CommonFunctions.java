package functions.common;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import framework.integration.JupyterLabIntegration;
import functions.file.FileFunctions;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;

public class CommonFunctions {

    /* Общие методы */

    /**
     * Ожидание
     */
    public static void wait(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public static void wait(double seconds) {
        long milliseconds = (long) (seconds * 1000);
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Ожидание отображения элемента
     *
     * @param locator - локатор
     * @param seconds - время ожидания в секундах
     */
    @Deprecated
    public static void waitForElementDisplayed(By locator, int seconds) {
        $(locator).shouldBe(Condition.visible, Duration.ofSeconds(seconds));
        highlightSelenideElement(locator);
    }

    /**
     * Ожидание отображения элемента (с опциональностью подсветки элемента)
     *
     * @param locator   - локатор
     * @param seconds   - время ожидания в секундах
     * @param highlight - необходимость подсветки элемента
     */
    @Deprecated
    public static void waitForElementDisplayed(By locator, int seconds, boolean highlight) {
        $(locator).shouldBe(Condition.visible, Duration.ofSeconds(seconds));
        if (highlight) {
            highlightSelenideElement(locator);
        }
    }

    /**
     * Ожидание исчезновения элемента
     *
     * @param locator - локатор
     * @param seconds - время ожидания в секундах
     */
    @Deprecated
    public static void waitForElementDisappeared(By locator, int seconds) {
        $(locator).should(Condition.disappear, Duration.ofSeconds(seconds));
    }

    /**
     * Подсветка элемента (выделение границ элемента)
     *
     * @param locator - локатор
     */
    @Deprecated
    public static void highlightSelenideElement(By locator) {
        SelenideElement element = $(locator);
        for (int i = 0; i < 1; i++) {
            // draw a border around the found element
            executeJavaScript("arguments[0].style.border='3px solid red'", element);
            CommonFunctions.wait(0.15);
            executeJavaScript("arguments[0].style.border='none'", element);
            CommonFunctions.wait(0.15);
        }
    }


    /**
     * Рандомный номер
     *
     * @param min - минимальное значение
     * @param max - максимальное значение
     */
    public static String randomNumber(int min, int max) {
        Random rnd = new Random(System.currentTimeMillis());
        int num = min + rnd.nextInt(max - min + 1);
        String numString = Integer.toString(num);
        return numString;
    }

    /**
     * Номер со счетчиком вида ddMM## (с использованием файла) (генерация + сохранение)
     *
     * @param docNumCounterPath - путь к файлу счетчика (должен быть создан и заполнен вручную)
     * @param docNumPath        - путь к файлу генерируемого номера документа
     */
    @Deprecated
    public static String generateNiceDocNum(String docNumCounterPath, String docNumPath) {
        String docNum = null;
        try {
            //Создание объекта BufferedReader
            BufferedReader reader = new BufferedReader(new FileReader(docNumCounterPath));
            //Читаем и разделяем номер на части, где docNum_a = ДДММ, а docNum_b - двузначный счетчик в разрезе дня
            String docNumCounter = reader.readLine();
            String docNum_a = docNumCounter.substring(0, 4);
            String docNum_b = docNumCounter.substring(4);

            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMM");

            if (docNum_a.equals(dateFormat.format(new Date()))) {
                if (Integer.valueOf(docNum_b) < 9) {
                    docNum = docNum_a + 0 + (Integer.valueOf(docNum_b) + 1);
                } else {
                    docNum = docNum_a + (Integer.valueOf(docNum_b) + 1);
                }
            } else {
                docNum_a = dateFormat.format(new Date());
                docNum_b = "01";
                docNum = docNum_a + docNum_b;
            }
            //System.out.println(docNum);
            //Создание объекта BufferedWriter
            BufferedWriter writer = new BufferedWriter(new FileWriter(docNumCounterPath));
            BufferedWriter writer_2 = new BufferedWriter(new FileWriter(docNumPath));
            //Запишем в файл
            writer.write(docNum);
            writer_2.write(docNum);

            reader.close();
            writer.close();
            writer_2.close();

        } catch (Exception e) {
            System.out.println("Файл не найден!");
        }
        return docNum;
    }

    /**
     * Сгенерировать номер вида ddMM## из счетчика (с использованием файла) (генерация БЕЗ сохранения)
     *
     * @param docNumCounterPath - путь к файлу счетчика (должен быть создан и заполнен вручную)
     */
    public static String generateDocNumFromCounter(String docNumCounterPath) {

        String docNum = null;

        try {
            //проверить наличие файла счетчика, если его нет - создаем файл со значением ddMM01
            File docNumCounterPathFile = new File(docNumCounterPath);
            if (!docNumCounterPathFile.exists()) {
                System.out.println("Файл счетчика не обнаружен. Будет создан новый файл счетчика");
                docNumCounterPathFile = new File(docNumCounterPath);
                CommonFunctions.wait(5);
                FileFunctions.writeValueToFile(docNumCounterPath, DateFunctions.dateToday("ddMM") + "01");
            }

            //Создание объекта BufferedReader
            BufferedReader reader = new BufferedReader(new FileReader(docNumCounterPath));
            //Читаем и разделяем номер на части, где docNum_a = ddMM, а docNum_b - двузначный счетчик в разрезе дня
            String docNumCounter = reader.readLine();
            String docNum_a = docNumCounter.substring(0, 4);
            String docNum_b = docNumCounter.substring(4);

            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMM");

            if (docNum_a.equals(dateFormat.format(new Date()))) {
                if (Integer.valueOf(docNum_b) < 9) {
                    docNum = docNum_a + 0 + (Integer.valueOf(docNum_b) + 1);
                } else {
                    docNum = docNum_a + (Integer.valueOf(docNum_b) + 1);
                }
            } else {
                docNum_a = dateFormat.format(new Date());
                docNum_b = "01";
                docNum = docNum_a + docNum_b;
            }
            //Создание объекта BufferedWriter
            BufferedWriter writer = new BufferedWriter(new FileWriter(docNumCounterPath));
            //Запишем в файл
            writer.write(docNum);

            reader.close();
            writer.close();

        } catch (Exception e) {
        }
        return docNum;
    }

    /**
     * Вывести в консоль выполняемый шаг
     */
    public static void printStep() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        System.out.println((char) 27 + "[33m***" + stack[2].getClassName() + " --> " + stack[2].getMethodName() + "***" + (char) 27 + "[0m");
    }

    /**
     * Сделать скриншот
     *
     * @param way - полный путь к сохраняемому файлу
     */
//    @Step("Сделать скриншот")
    @Attachment(value="Вложение", type="image/png")
    public static byte[] screenShot(String way) {
        byte[] fileScreen = screenshot(OutputType.BYTES);
        JupyterLabIntegration.uploadBinaryContent(fileScreen, way, "screen.png");

//        try {
//            FileUtils.copyFile(fileScreen, new File(way));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //для Allure
//        screenShotAllure();
        return fileScreen;
    }

//    @Attachment(value="Вложение", type="image/png")
    public static byte[] screenShotAllure() {
        return screenshot(OutputType.BYTES);
    }

    /**
     * Счеткик времени
     */
    public static long StartTransaction() {
        return System.nanoTime();
    }

    public static double TimeTransaction(long startTransaction) {
        long endTransaction = System.nanoTime();
        double timeTransaction = TimeUnit.MILLISECONDS.convert(endTransaction - startTransaction, TimeUnit.NANOSECONDS) / 1000.0;
        return timeTransaction;
    }


}
