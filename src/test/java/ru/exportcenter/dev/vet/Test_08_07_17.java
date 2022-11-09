package ru.exportcenter.dev.vet;

import com.codeborne.selenide.Selenide;
import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.$x;
import static functions.file.FileFunctions.searchFileInDefaultDownloadDir;

public class Test_08_07_17 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/vet/Test_08_07_17/";
    public String WAY_TO_PROPERTIES = Ways.TEST.getWay() + "/vet/Test_08_07_17/" + "Test_08_07_17_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;


    @Owner(value = "Селедцов Вадим")
    @Description("08.07.17 Страновой отчет - Детализированный")
    @Link(name = "Test_08_07_17", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=183200759")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        precondition();
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    public void precondition() {
        //Предусловие: выполнить шаги 1-4 из ТК https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163308618
        Test_08_07_15 test_08_07_15 = new Test_08_07_15();
        test_08_07_15.WAY_TEST = this.WAY_TEST;
        test_08_07_15.steps();

    }

    @Step("Ввод данных в карточку \"Тип услуги\" ")
    public void step01() {
        CommonFunctions.printStep();

        //В поле Выберите тип услуги выбрать Оформить новое разрешение  Нажать "Продолжить"
        new GUIFunctions()
                .inContainer("Запрос разрешения на вывоз подконтрольной продукции")
                .clickByLocator("//a[text()='Сформировать отчет']")
                .waitForLoading()
                .inContainer("Выбор отчёта")
                .waitForElementDisplayed("//span[text()='Общий']");

    }

    @Step("В поле Выбор отчёта, выбираем - Общий ")
    public void step02() {
        CommonFunctions.printStep();


        new GUIFunctions()
                .inContainer("Выбор отчёта")
                .clickByLocator("//span[text()='Страновой']")
                .waitForLoading()
                .inContainer("Страновой отчёт")
                .clickByLocator("//span[text()='Детализированный']")
                .inContainer("Формирование отчетности по разрешениям на вывоз подконтрольной продукции")
                .clickButton("Далее")
                .waitForElementDisplayed("//span[text()='Заполните поле']");

        $x("//input[@name='$.createItem.country.search.period1.create_date_from']").setValue("01.09.2022");
        $x("//input[@name='$.createItem.country.search.period1.create_date_to']").setValue("30.09.2022");
        $x("//input[@name='$.createItem.country.search.period2.create_date_from']").setValue("01.10.2022");
        $x("//input[@name='$.createItem.country.search.period2.create_date_to']").setValue("20.10.2022");
        new GUIFunctions()   //заполнение полей с последующим закрытием каждого поля после проверки
                .inContainer("Страновой отчёт")
                .clickByLocator("//span[text()='Страна импорта']")
                .inField("Необязательно").selectValue("КИТАЙСКАЯ НАРОДНАЯ РЕСПУБЛИКА")
                .clickByLocator("//span[text()='Страна импорта']")
                .clickByLocator("//span[text()='Вид продукции']")
                .inField("Необязательно").selectValue("сухой заменитель молока")
                .clickByLocator("//span[text()='Вид продукции']")
                .clickByLocator("//span[text()='Наименование продукции']")
                .inField("Необязательно").inputValue("Наименование продукции")


        //span[text()='Скачать архив со всеми сформированными отчётами']
                .inContainer("Формирование отчетности по разрешениям на вывоз подконтрольной продукции")
                .clickButton("Далее")
                .waitForElementDisplayed("//div[text()='Сформированные отчеты']", 360);

    }
    @Step("Скачать и провертиь отчет")
    public void step03() {
        //нажать на просмотр общего отчета
        new GUIFunctions()
                .inContainer("Сформированные отчеты")
                .clickByLocator("//div[text()='Страновой отчет.xlsx']");
        // File report = (File) $("//div[text()='Общий отчет.pdf']");
        Selenide.sleep(10000);  // после правки бага с зависанием , можно заменить на нормальное ожидание

        System.out.println("done");
        searchFileInDefaultDownloadDir("Страновой отчет");

    }

}
