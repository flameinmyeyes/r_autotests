package functional;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;

public final class CommonFunctions {

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
    public static void waitForElementDisappeared(By locator, int seconds) {
        $(locator).should(Condition.disappear, Duration.ofSeconds(seconds));
    }

    /**
     * Подсветка элемента (выделение границ элемента)
     *
     * @param locator - локатор
     */
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
     * Преобразование даты из String в Date
     * @param dateString   Исходная дата
     * @param dateFormat Формат исходной даты. Например dd.MM.yyyy
     * @return
     */
    public static Date parseDate(String dateString, String dateFormat) {
        Date date = null;
        try {
            date = new SimpleDateFormat(dateFormat, Locale.getDefault()).parse(dateString);
        } catch (Exception e) {
            System.out.println("Не удалось преобразовать " + dateString + " в формат даты");
        }
        return date;
    }

    /**
     * Преобразование даты из одного формата в другой
     *
     * @param dateFrom   Исходная дата
     * @param formatFrom Формат исходной даты. Например dd.MM.yyyy
     * @param formatTo   Требуемый формат даты. Например yyyy-MM-dd
     */
    public static String dateReFormat(String dateFrom, String formatFrom, String formatTo) {
        SimpleDateFormat oldDateFormat = new SimpleDateFormat(formatFrom, Locale.getDefault());
        SimpleDateFormat newDateFormat = new SimpleDateFormat(formatTo, Locale.getDefault());
        Date date = null;
        try {
            date = oldDateFormat.parse(dateFrom);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Не удалось преобразовать " + dateFrom + " в требуемый формат даты");
        }
        return newDateFormat.format(date);
    }

    /**
     * Дата сегодня
     *
     * @param format - формат даты ("dd.MM.yyyy" и т.д.)
     */
    public static String dateToday(String format) {
        String date = new SimpleDateFormat(format).format(new Date());
        return date;
    }

    /**
     * Дата со сдвигом на нужное число дней вперед(+)/назад(-) (смещение относительной текущей даты)
     * @param format    - формат даты ("dd.MM.yyyy" и т.д.)
     * @param dateShift - сдвиг даты в календарных днях вперед(+) / назад(-)
     */
    public static String dateShift(String format, Integer dateShift) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, dateShift);
        //так же можно сделать смещение в годах и в месяцах
        //calendar.add(Calendar.YEAR, yearShift);
        //calendar.add(Calendar.MONTH, monthShift);
        Date date = calendar.getTime();
        return dateFormat.format(date);
    }

    /**
     * Дата со сдвигом на нужное число дней вперед(+)/назад(-) (смещение относительно кастомной даты)
     * @param date      - дата, от которой идет сдвиг
     * @param format    - формат даты ("dd.MM.yyyy" и т.д.)
     * @param dateShift - сдвиг даты в календарных днях вперед(+) / назад(-)
     */
    public static String dateShift(String date, String format, Integer dateShift) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (Exception e) {
            System.out.println("Не удалось преобразовать " + date + " в формат даты");
        }
        calendar.add(Calendar.DATE, dateShift);
        //так же можно сделать смещение в годах и в месяцах
        //calendar.add(Calendar.YEAR, yearShift);
        //calendar.add(Calendar.MONTH, monthShift);
        Date newDate = calendar.getTime();
        return dateFormat.format(newDate);
    }

    /**
     * Дата со сдвигом на нужное число дней вперед(+)/назад(-) (для смещения относительно кастомной даты) с возможностью исключения выходных дней
     *
     * @param date            - дата, от которой идет сдвиг
     * @param format          - формат даты ("dd.MM.yyyy" и т.д.)
     * @param dateShift       - сдвиг даты в календарных днях вперед(+) / назад(-)
     * @param excludeHolidays - исключить выходные дни (true/false)
     */
    public static String dateShift(String date, String format, Integer dateShift, boolean excludeHolidays) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(date));
        } catch (Exception e) {
            System.out.println("Не удалось преобразовать " + date + " в формат даты");
        }
        calendar.add(Calendar.DATE, dateShift);

        //проверка на выходной день
        if (excludeHolidays) {
            while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                if (dateShift > 0) {
                    calendar.add(Calendar.DATE, +1);
                } else {
                    calendar.add(Calendar.DATE, -1);
                }
            }
        }

        //так же можно сделать смещение в годах и в месяцах
        //calendar.add(Calendar.YEAR, yearShift);
        //calendar.add(Calendar.MONTH, monthShift);
        Date newDate = calendar.getTime();
        return dateFormat.format(newDate);
    }

    /**
     * Первый день месяца
     *
     * @param format - формат даты ("dd.MM.yyyy" и т.д.)
     */
    public static String firstDayOfMonth(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        Date firstDay = calendar.getTime();
        return dateFormat.format(firstDay);
    }

    /**
     * Первый день месяца (со смещением месяца)
     *
     * @param format     - формат даты ("dd.MM.yyyy" и т.д.)
     * @param monthShift - смещение месяца вперед(+) / назад(-)
     */
    public static String firstDayOfMonth(String format, Integer monthShift) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, monthShift);
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
        Date firstDay = calendar.getTime();
        return dateFormat.format(firstDay);
    }

    /**
     * Последний день месяца
     *
     * @param format - формат даты ("dd.MM.yyyy" и т.д.)
     */
    public static String lastDayOfMonth(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date lastDay = calendar.getTime();
        return dateFormat.format(lastDay);
    }

    /**
     * Последний день месяца (со смещением месяца)
     *
     * @param format     - формат даты ("dd.MM.yyyy" и т.д.)
     * @param monthShift - смещение месяца вперед(+) / назад(-)
     */
    public static String lastDayOfMonth(String format, Integer monthShift) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, monthShift);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        Date lastDay = calendar.getTime();
        return dateFormat.format(lastDay);
    }

    /**
     * Первый день недели
     *
     * @param format - формат даты ("dd.MM.yyyy" и т.д.)
     */
    public static String firstDayOfWeek(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //устанавливаем, что неделя начинается с ПН
        calendar.getFirstDayOfWeek();
        Date firstDay = calendar.getTime();
        return dateFormat.format(firstDay);
    }

    /**
     * Последний день недели
     *
     * @param format - формат даты ("dd.MM.yyyy" и т.д.)
     */
    public static String lastDayOfWeek(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //устанавливаем, что неделя начинается с ПН
        calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DATE, +6);
        Date lastDay = calendar.getTime();
        return dateFormat.format(lastDay);
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
                FileFunctions.writeValueFile(docNumCounterPath, CommonFunctions.dateToday("ddMM") + "01");
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
    public static void screenShot(String way) {
        File fileScreen = Selenide.screenshot(OutputType.FILE);
        try {
            FileUtils.copyFile(fileScreen, new File(way));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //для Allure
        screenShotAllure();
    }

    @Attachment(value="Вложение", type="image/png")
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

    /**
     * Закодировать в base64
     */
    public static String encodeToBase64(String value) {
        String encodedValue = Base64.getEncoder().encodeToString(value.getBytes());
        return encodedValue;
    }

    /**
     * Раскодировать из base64
     */
    public static String decodeFromBase64(String value) {
        byte[] decodedBytes = Base64.getDecoder().decode(value);
        String decodedValue = new String(decodedBytes);
        return decodedValue;
    }

    /**
     * Сбросить кэш
     */
    public static void resetCache() {
        // Пользователь: Пятница Анна Сергеевна
        new LoginPage().authorization("dc02ea88-c09c-429a-9329-adf35f1d3513");
        new MainPage().clickWebElement("//span[text()=' Настройки']")
                .clickWebElement("//span[text()=' Администрирование комплекса']")
                .clickWebElement("//span[text()=' Настройки кэширования']")
                .clickWebElement("//button[text()=' Сбросить кэш']");
    }


}
