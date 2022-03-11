package framework;

import functional.CommonFunctions;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.refresh;

public class RunTestAgain implements IRetryAnalyzer {

    private int retryCount = 0;
    private int retryMaxCount = 0; //установить необходимое число перезапусков

//    @Parameters({"retryMaxCount"})
//    @Test
//    public void retryMaxCountReader(@Optional("1") int retryMaxCount) {
//        //определяем число перезапусков (по тест-сьюту)
//        this.retryMaxCount = retryMaxCount;
//        System.out.println("Число перезапусков: " + this.retryMaxCount);
//    }

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retryCount < retryMaxCount) {
            refresh();
            CommonFunctions.wait(5);
            retryCount++;
            System.out.println("ТЕСТ ПРОВАЛЕН. ВЫПОЛНЯЕТСЯ ПЕРЕЗАПУСК ТЕСТА"); //пишем в лог
            return true; //пока истина перезапускаем
        }
        if (retryCount > 0) {
            System.out.println("ТЕСТ ПРОВАЛЕН " + (retryCount + 1) + " РАЗ(А)!"); //пишем в лог
        }
        retryCount = 0;
        return false;
    }

}