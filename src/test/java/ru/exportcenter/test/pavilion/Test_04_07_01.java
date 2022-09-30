package ru.exportcenter.test.pavilion;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import functions.gui.lkb.GUIFunctionsLKB;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import java.awt.*;
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;

public class Test_04_07_01 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/pavilion/Test_04_07_01/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_04_07_01_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;
    private String processID;

    @Owner(value = "Петрищев Руслан")
    @Description("04 07 01 Заполнение Заявки на получение услуги, подписание Заявки УКЭП и автоматическая передача Заявки на верификацию")
    @Link(name = "Test_04_07_01", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163302431")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
//        requestNumber = "S/2022/302123";
        step01();
        step02();
        step03();
        step04();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Блок «Сведения о демонстрационно-дегустационном павильоне»")
    public void step01() {
        CommonFunctions.printStep();

        //Ввести логин и пароль
        open("https://lk.t.exportcenter.ru/ru/promo-service?key=pavilion&serviceId=a546931c-0eb9-4545-853a-8a683c0924f7&next_query=true");
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"), PROPERTIES.getProperty("Авторизация.Код"));

        requestNumber = $x("//div[text()='Номер заявки']/following-sibling::div").getText();
        System.out.println($x("//div[text()='Номер заявки']/following-sibling::div").getText());

        new GUIFunctions().refreshTab("Продолжить", 20);

        processID = CommonFunctions.getProcessIDFromURL();
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST, "processID.txt");

        new GUIFunctions().clickButton("Продолжить")
                .waitForElementDisplayed("//*[text()='Страна нахождения павильона']")
                .inContainer("Сведения о демонстрационно-дегустационном павильоне")
                .inField("Страна нахождения павильона").selectValue(PROPERTIES.getProperty("Авторизация.Страна нахождения павильона")).assertValue()
                .waitForLoading();
        new GUIFunctions().clickButton("Далее")
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");

        CommonFunctions.wait(20);

        //Костыль
        new GUIFunctions()
                .waitForURL("https://lk.t.exportcenter.ru/ru/main")
                .clickButton("Показать все (100)")
                .scrollTo($x("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div"))
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div")
                .waitForElementDisplayed("//*[text()='" + requestNumber + "']")
                .refreshTab("Продолжить", 15)
                .clickButton("Продолжить");
//        closeWebDriver();
    }

    @Step("Заполнение заявки")
    public void step02() throws AWTException {
        CommonFunctions.printStep();

        open("https://bpms.t.exportcenter.ru/");

        new GUIFunctionsLKB()
                .authorization("bpmn_admin", "password");

        $x("//div[text()='camunda-exp-search']").click();
        $x("//div[text()='Cockpit']").click();
//        CommonFunctions.wait(5);
        switchTo().frame($x("//iframe[@src='/camunda/camunda-exp-search/app/cockpit/default/#']"));
        new GUIFunctions().waitForElementDisplayed("//a[text()='Процессы']");
        $x("//a[text()='Процессы']").click();

        new GUIFunctions().waitForLoading();
//        switchTo().frame($x("//div[@class='ctn-view cockpit-section-dashboard processes-dashboard ng-scope']"));

        new GUIFunctions().scrollTo($x("//*[contains(text(),'Павильоны. Господдержка. Демонстрационно-дегустационные павильоны АПК')]"))
                .clickByLocator("//*[contains(text(),'Павильоны. Господдержка. Демонстрационно-дегустационные павильоны АПК')]")
                .waitForElementDisplayed("//input[@placeholder='Добавить критерии']");

        $x("//input[@placeholder='Добавить критерии']").setValue(requestNumber).pressEnter();
        new GUIFunctions().waitForElementDisplayed("//td[@class='instance-id ng-isolate-scope']/span/a");
        $x("//td[@class='instance-id ng-isolate-scope']/span/a").click();
        new GUIFunctions().waitForElementDisplayed("//input[@placeholder='Добавить критерии']");
        $x("//input[@placeholder='Добавить критерии']").setValue("passSmevFnsRequest").pressEnter();
        new GUIFunctions().waitForElementDisplayed("//button[@tooltip='Редактировать переменную']")
                .clickByLocator("//button[@tooltip='Редактировать переменную']")
                .waitForElementDisplayed("//select[@ng-model='variable.type']")
                .clickByLocator("//select[@ng-model='variable.type']")
                .clickByLocator("//*[text()='String']")
                .clickByLocator("//span[text()='<null>']");

        $x("//input[@placeholder='Значение переменной']").setValue("1");
        $x("//*[text()='Значение']").click();
        $x("//button[@tooltip='Сохранить переменную']").click();
        switchTo().defaultContent();
//        new GUIFunctions().waitForElementDisplayed("//*[text()='Переменная 'passSmevFnsRequest' изменена.']");
//        closeWebDriver();
    }

    @Step("Блок «Информация о продукции»")
    public void step03() throws AWTException {
        CommonFunctions.printStep();

        open("https://lk.t.exportcenter.ru/ru/login");
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"), PROPERTIES.getProperty("Авторизация.Код"));

        new GUIFunctions()
                .waitForURL("https://lk.t.exportcenter.ru/ru/main")
                .clickButton("Показать все (100)")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div")
                .waitForElementDisplayed("//*[text()='" + requestNumber + "']")
                .refreshTab("Продолжить", 15)
                .clickButton("Продолжить")
                .waitForElementDisplayed("//*[text()='Добавить +']");

        new GUIFunctions().clickButton("Добавить +");
        new CommonFunctions().wait(2);
        new GUIFunctions().inContainer("Сведения о продукции")
                .inField("Каталог продукции").inputValue("1704")
                .waitForElementDisplayed("//*[contains(text(), 'Белёвская пастила с чёрной смородиной')]")
                .clickByLocator("//*[contains(text(), 'Белёвская пастила с чёрной смородиной')]");

//        new GUIFunctions().inField("Каталог продукции").selectValue("1704909900\u00a0Белёвская пастила с чёрной смородиной");

        new GUIFunctions().inField("Количество ед. продукции").inputValue(PROPERTIES.getProperty("Информация о продукции.Количество ед. продукции")).assertValue()
                .inField("Единица измерения").selectValue(PROPERTIES.getProperty("Информация о продукции.Единица измерения")).assertValue()
                .inField("Общая стоимость партии товара, включая затраты на транспортировку (юань)").inputValue(PROPERTIES.getProperty("Информация о продукции.Общая стоимость партии товара"))
                .inField("Условия транспортировки и хранения продукции").inputValue(PROPERTIES.getProperty("Информация о продукции.Условия транспортировки и хранения продукции")).assertValue()
                .inField("Розничная продажа").setCheckboxON().assertCheckboxON()
                .inField("Оптовая продажа").setCheckboxON().assertCheckboxON()
                .inField("Оценка спроса (в рублях)").inputValue(PROPERTIES.getProperty("Информация о продукции.Оценка спроса"))
                .inField("Готовность к адаптации").setCheckboxON().assertCheckboxON()
                .inField("Наличие сертификата страны размещения").setCheckboxON().assertCheckboxON()
                .inField("Номер сертификата").inputValue(PROPERTIES.getProperty("Информация о продукции.Номер сертификата")).assertValue()
                .inField("Дата выдачи").inputValue(PROPERTIES.getProperty("Информация о продукции.Дата выдачи")).assertValue()
                .inField("Наименование органа, выдавшего сертификат").inputValue(PROPERTIES.getProperty("Информация о продукции.Наименование органа")).assertValue()
                .inField("Наличие презентационных материалов на английском языке или на языке страны размещения").setCheckboxON().assertCheckboxON()
                .inField("Наличие товарного знака в стране размещения").setCheckboxON().assertCheckboxON()
                .inField("Наименование ЭТП размещения продукции").selectValue(PROPERTIES.getProperty("Информация о продукции.Наименование ЭТП размещения продукции"))
                .inField("Данные дистрибьютора на рынке павильона").inputValue(PROPERTIES.getProperty("Информация о продукции.Данные дистрибьютора на рынке павильона")).assertValue()
                .clickButton("Добавить")
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Подписать электронной подписью']");
    }

    @Step("Подписание заявки")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions().clickButton("Подписать электронной подписью")
                .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022").assertValue()
                .clickButton("Подписать")
                .waitForElementDisplayed("//*[text()='Подписано']")
                .clickButton("Далее");
//        closeWebDriver();
    }
}
