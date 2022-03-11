package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_069_PZ_2_3 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_069_PZ_2_3\\";
    public static String WAY_DOWNLOADS = Ways.DOWNLOADS.getWay();
    private static Date date;

    @Owner(value = "Ворожко Александр")
    @Description("NSI_069. ПЗ п. 2.3. Реквизитный состав спр-ка")
    @Link(name="TSE-T3160", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3160")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "vorozhko_aleksandr", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        WAY_DOWNLOADS = setWay(WAY_DOWNLOADS);

        step01();
        step02();
        step03();
        step04();
        step05();
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Перечень КБК, допущенных к проведению операций по кассовым выплатам».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage()
                .openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Перечень КБК, допущенных к проведению операций по кассовым выплатам в период приостановления операций на лицевом счете")
                .resetUserSettings();
    }

    @Step("Выбрать запись где Номер л/с =03601108380")
    public void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .filterColumnInList("Номер л/с", "03601108380")
                .filterColumnInList("Дата действия с", "05.05.2021");

        $x("//td//div[text()='Нет элементов, удовлетворяющих Вашему запросу']").shouldNotBe(Condition.visible);
        new MainPage()
                .clickRowsInList(1);
    }

    @Step("Нажать на кнопку \"Просмотреть\"")
    public void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .clickButtonTitle("Открыть документ на просмотр");
    }

    @Step("Проверить на ВФ реквизиты")
    public void step05() {
        CommonFunctions.printStep();
        assertValuefromField("Номер л/с", "03601108380");
        assertValuefromField("Шаблон КБК", "%111");
        assertValuefromField("Код ТОФК (где введен)", "6000");
        assertValuefromField("Наименование ТОФК (где введен)", "Управление Федерального казначейства по Саратовской области");
        assertValuefromField("Статус", "Актуальная");
        assertValuefromField("Дата действия с", "05.05.2021");
        assertValuefromField("Дата изменения", "05.05.2021");
    }

    public void assertValuefromField(String title, String expected) {
        System.out.println("Проверка поля " + title);
        String xpath = "//div[contains(@class, 'doc-edit-frame')]//*[text()='" + title + "']" +
                "//ancestor::*[descendant::*[contains(@class, 'z-advtextarea') or contains(@class, 'z-textbox') or contains(@class, 'z-datebox-input')]][1]" +
                "//*[contains(@class, 'z-advtextarea') or contains(@class, 'z-textbox') or contains(@class, 'z-datebox-input')]";
        String value = $x(xpath).getAttribute("value");
        Assert.assertEquals(value, expected);
    }
}
