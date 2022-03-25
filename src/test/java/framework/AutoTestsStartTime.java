package framework;

import functions.common.CommonFunctions;
import functions.common.DateFunctions;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AutoTestsStartTime {

//    private final String START_TIME = "26.07.2021 22:00";
    private String START_TIME = null;

    @Parameters({"autoTestsStartTime"})
    @Test
    public void waitForStartTime(String autoTestsStartTime) {
        //определяем время старта (по тест-сьюту)
        START_TIME = autoTestsStartTime;

        //Текущая дата
        String currentTime = DateFunctions.dateToday("dd.MM.yyyy HH:mm");
        System.out.println("Текущее время: " + currentTime);
        //Преобразовываем в Date
        Date currentTime_date = null;
        try {
            currentTime_date = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Время старта
        System.out.println("Время запуска: " + START_TIME);
        //Преобразовываем в Date
        Date startTime_date = null;
        try {
            startTime_date = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(START_TIME);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(currentTime_date.before(startTime_date)) {
            System.out.println("Ожидание времени запуска...");
        }

        //Сравниваем время
        while(currentTime_date.before(startTime_date)) {
            CommonFunctions.wait(30);
            //Обновляем текущее время
            currentTime = DateFunctions.dateToday("dd.MM.yyyy HH:mm");
            //Преобразовываем в Date
            try {
                currentTime_date = new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(currentTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Время запуска наступило");
    }

}
