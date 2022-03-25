package functions.common;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import functions.file.FileFunctions;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;

public class DateFunctions {

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


}
