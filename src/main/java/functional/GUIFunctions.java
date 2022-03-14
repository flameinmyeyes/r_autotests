package functional;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Map;

import static com.codeborne.selenide.Selenide.*;

public class GUIFunctions {

    /* Методы для работы с главной страницей */

    public GUIFunctions() {
        super();
    }

    public String mainPageWindow = "//div[@class='docbrowse-center z-center']";
    public String windowDirectory = "//div[@class='doc-dialog-content z-center']";
    public int defaultWaitForLoadingTime = 60;

    /**
     * Ожидание окончания Обработки запроса
     * @param maxWaitSeconds - максимальное время ожидания
     * */
    public GUIFunctions waitForLoading(int maxWaitSeconds) {
        CommonFunctions.wait(1);
        if ($(By.xpath("//div[@class='z-loading-indicator']")).isDisplayed()) {
            System.out.println("Ожидание обработки запроса...");
            $(By.xpath("//div[@class='z-loading-indicator']")).shouldBe(Condition.not(Condition.visible), Duration.ofSeconds(maxWaitSeconds));
        }
        return this;
    }



}
