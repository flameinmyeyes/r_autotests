package ru.exportcenter.uidm_dev;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
import functional.CommonFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import org.openqa.selenium.By;
import org.testng.annotations.Test;
import ru.exportcenter.uidm_dev.Hooks_UIDM_DEV;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;

public class Test_03_07_04 extends Hooks_UIDM_DEV {

    @Owner(value="Ворожко Александр")
    @Description("Тест_1")
    @Link(name="TSE-T5590", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T5590")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_03", "BP_004"})
    public void steps() {
        step01();
        step02();
        step03();
        stepLast();
    }

    //    @Step("Авторизоваться в ЛК Клиента ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step01() {
        CommonFunctions.printStep();
        authorization("demo_exporter", "password");

        selectTab("Сервисы");

        inputSearchField("Поиск по разделу", "Логистика. Доставка продукции \"Агроэкспрессом\"");
        openResult("Логистика. Доставка продукции \"Агроэкспрессом\"", "Оформить");

        if (!$x("//*[contains(text(), 'Продолжить')]").isDisplayed()) {
            refreshTab("//*[contains(text(), 'Продолжить')]", 5);
        }

        $x("//*[contains(text(), 'Продолжить')]")
                .shouldBe(Condition.visible)
                .click();
    }

    private void step02() {
        CommonFunctions.printStep();
        inputValueInBlock("Информация о компании", "Почтовый адрес", "Корнилаева 2");
    }

    private void step03() {
        CommonFunctions.printStep();
        inputValueInBlock("Информация о грузополучателе", "Наименование грузополучателя", "Ss-password");
        inputValueInBlock("Информация о грузополучателе", "Страна", "USA");
        inputValueInBlock("Информация о грузополучателе", "Город", "Moscow");
        inputValueInBlock("Информация о грузополучателе", "Дом", "Ff");
        inputValueInBlock("Информация о грузополучателе", "Регион", "St-Peterburg");
        inputValueInBlock("Информация о грузополучателе", "Район", "Raion");
        inputValueInBlock("Информация о грузополучателе", "Улица", "Lenina street");
        inputValueInBlock("Информация о грузополучателе", "Регистрационный номер грузополучателя", "223 22 44 2");
        inputValueInBlock("Информация о грузополучателе", "Телефон", "+79999999999");
        inputValueInBlock("Информация о грузополучателе", "Представитель грузополучателя", "Moscow disco rule");
        inputValueInBlock("Информация о грузополучателе", "Email", "www@mail.ru");
    }

    private void step04() {
        clickCheckBoxInBlock("Информация о заявителе", "Дополнительный контакт");
    }

    private void stepLast() {
        CommonFunctions.printStep();
        System.out.println("Success");
        CommonFunctions.wait(10);
    }

    public void authorization(String login, String pswd) {
        $x("//h1[contains(text(), 'Вход в личный кабинет')]")
                .shouldBe(Condition.visible);

        $x("//input[@type = 'username']")
                .shouldBe(Condition.visible)
                .setValue(login);

        $x("//input[@type = 'password']")
                .shouldBe(Condition.visible)
                .setValue(pswd);

        $x("//button[@id = 'submit'][descendant::*[text() = 'Войти']]")
                .shouldBe(Condition.visible)
                .click();

        $x("//*[@id = 'elk-menu']").shouldBe(Condition.visible);
    }

    public void selectTab(String tabName) {
        String xpathToTab = "//li[@data-name = '" + tabName + "']";
        $x(xpathToTab)
                .shouldBe(Condition.visible)
                .click();
//        waitForLoading(120);
    }

    public void waitForLoading(int maxWaitSeconds) {
        while ($(By.xpath("//*[contains(@class, 'preloader') or contains(@class,'spinner')]")).isDisplayed()) {
            System.out.println("Ожидание обработки запроса...");
            $(By.xpath("//*[contains(@class, 'preloader')]")).shouldBe(Condition.not(Condition.visible), Duration.ofSeconds(maxWaitSeconds));
        }
    }

    public void inputSearchField(String placeHolder, String searchText) {
        String xpathToField = "//input[contains(@placeholder, '" + placeHolder + "')][following-sibling::*[descendant::*[name()='use' and contains(@*, '#search')]]]";
        String xpathToButton = "//input[contains(@placeholder, '" + placeHolder + "')]//following-sibling::*[descendant::*[name()='use' and contains(@*, '#search')]]";

        CommonFunctions.wait(2);
        $x(xpathToField)
                .shouldBe(Condition.visible)
                .sendKeys(searchText);

        $x(xpathToButton)
                .shouldBe(Condition.visible)
                .click();

        waitForLoading(120);
    }

    public void openResult(String name, String buttonName) {
        String xpathToButton = "//*[contains(text(), '" + name + "')]//following-sibling::div//descendant::*[contains(text(), '" + buttonName + "')]";

        $x(xpathToButton)
                .shouldBe(Condition.visible)
                .click();

        switchTo().window(1);
        waitForLoading(120);
    }

    public void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            System.out.println("Refreshing");
            refresh();
            CommonFunctions.wait(1);
        }
    }

    public void inputValueInBlock(String block, String field, String value) {
        String xpathToField = "//div[text() = '" + block + "']/ancestor::div[contains(@class, 'WithExpansionPanel')]/descendant::div[descendant::*[text() = '" + field + "']]/following-sibling::div//input";

        executeJavaScript("arguments[0].scrollIntoView();", $x(xpathToField));
        $x(xpathToField)
                .shouldBe(Condition.visible)
                .setValue(value);
    }

    public void clickCheckBoxInBlock(String block, String field) {
        String xpathToField = "//div[text() = '" + block + "']/ancestor::div[contains(@class, 'WithExpansionPanel')]/descendant::div[descendant::*[text() = '" + field + "']]/preceding-sibling::div[contains(@class, 'Checkbox')]";

        executeJavaScript("arguments[0].scrollIntoView();", $x(xpathToField));
        $x(xpathToField)
                .shouldBe(Condition.visible)
                .click();
    }

}
