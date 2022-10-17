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
        step01();
        step02();
        step03();
        step04();
        step05();
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
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"), PROPERTIES.getProperty("Авторизация.Код"))
                .waitForElementDisplayed("//*[text()='Господдержка. Демонстрационно-дегустационные павильоны АПК']")
                .closeAllPopupWindows()
                .clickByLocator("//button[text()='Субсидия на продвижение продукции АПК']");

        switchTo().alert().accept();

//        if ($x("//*[text()='Господдержка. Демонстрационно-дегустационные павильоны АПК']").isDisplayed()){
//            new GUIFunctions()
//                    .closeAllPopupWindows()
//                    .clickByLocator("//button[text()='Субсидия на продвижение продукции АПК']");
//        }

        requestNumber = $x("//div[text()='Номер заявки']/following-sibling::div").getText();
        System.out.println($x("//div[text()='Номер заявки']/following-sibling::div").getText());

        new GUIFunctions().refreshTab("Продолжить", 20);

        processID = CommonFunctions.getProcessIDFromURL();
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST, "processID.txt");

        new GUIFunctions().clickButton("Продолжить")
                .waitForElementDisplayed("//*[text()='Страна нахождения павильона']")
                .closeAllPopupWindows();

        new GUIFunctions().inField("Страна нахождения павильона").selectValue(PROPERTIES.getProperty("Авторизация.Страна нахождения павильона")).assertValue();

        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Сведения о демонстрационно-дегустационном павильоне']");
    }

    @Step("Пропуск этапа ФНС")
    public void step02() throws AWTException {
        CommonFunctions.printStep();

        //В браузере перейти по ссылке
        open("https://bpms.t.exportcenter.ru/");
        switchTo().alert().accept();

        //Ввести логин и пароль
        new GUIFunctionsLKB().authorization(PROPERTIES.getProperty("Пропуск этапа ФНС.Email"), PROPERTIES.getProperty("Пропуск этапа ФНС.Пароль"));

        //Развернуть аккордеон «camunda-exp-search»
        //Выбрать вкладку «Cockpit»
        $x("//div[text()='camunda-exp-search']").click();
        $x("//div[text()='Cockpit']").click();


        switchTo().frame($x("//iframe[contains(@src,'/camunda/')]"));

        //Выбрать вкладку «Процессы» и найти значение «Name» = Павильоны. Господдержка. Демонстрационно-дегустационные павильоны АПК
        //Нажать на «Павильоны. Господдержка. Демонстрационно-дегустационные павильоны АПК»
        new GUIFunctions().waitForElementDisplayed("//a[text()='Процессы']");
        $x("//a[text()='Процессы']").click();
        new GUIFunctions().waitForLoading()
                .scrollTo($x("//*[contains(text(),'Павильоны. Господдержка. Демонстрационно-дегустационные павильоны АПК')]"))
                .clickByLocator("//*[contains(text(),'Павильоны. Господдержка. Демонстрационно-дегустационные павильоны АПК')]")
                .waitForElementDisplayed("//input[@placeholder='Добавить критерии']");

        //Найти Заявку по номеру (Бизнес ключи)
        $x("//input[@placeholder='Добавить критерии']").setValue(requestNumber).pressEnter();
        new GUIFunctions().waitForElementDisplayed("//td[@class='instance-id ng-isolate-scope']/span/a");

        //Нажать на Идентификатор заявки
        $x("//td[@class='instance-id ng-isolate-scope']/span/a").click();
        new GUIFunctions().waitForElementDisplayed("//input[@placeholder='Добавить критерии']");
        CommonFunctions.wait(1);

        //Ввести в поле поиска переменную "passSmevFnsRequest".Начать поиск
        $x("//input[@placeholder='Добавить критерии']").setValue("passSmevFnsRequest").pressEnter();
        CommonFunctions.wait(1);

        //Нажать на кнопку редактирования переменной (красная, с карандашом)
        //Выбрать тип "String", значение ввести "1"
        //Применить изменения
        new GUIFunctions().waitForElementDisplayed("//button[@tooltip='Редактировать переменную']")
                .clickByLocator("//button[@tooltip='Редактировать переменную']")
                .waitForElementDisplayed("//select[@ng-model='variable.type']")
                .clickByLocator("//select[@ng-model='variable.type']")
                .clickByLocator("//*[text()='String']")
                .clickByLocator("//span[text()='<null>']");

        $x("//input[@placeholder='Значение переменной']").setValue("1");
        $x("//*[text()='Значение']").click();
        $x("//button[@tooltip='Сохранить переменную']").click();
        new GUIFunctions().waitForElementDisplayed("//*[text()=\"Переменная 'passSmevFnsRequest' изменена.\"]");
        switchTo().defaultContent();

        new GUIFunctions().clickByLocator("//button[text()='Выйти']");

    }

    @Step("Заполнение заявки")
    public void step03() throws AWTException {
        CommonFunctions.printStep();

        open("https://lk.t.exportcenter.ru/ru/main");
        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"), PROPERTIES.getProperty("Авторизация.Код"));

        //Вернуться в ЕЛК, в заявку
        new GUIFunctions()
                .waitForURL("https://lk.t.exportcenter.ru/ru/main")
                .clickByLocator("//*[contains(text(),'" + requestNumber + "')]/parent::div/parent::div")
                .waitForElementDisplayed("//*[text()='" + requestNumber + "']")
                .refreshTab("Продолжить", 15)
                .clickButton("Продолжить")
                .waitForElementDisplayed("//*[text()='Добавить +']");

        //Перейти к блоку "Дополнительные сведения"
        //В поле "Комментарий" ввести значение "Дополнительные сведения"

    }

    @Step("Блок \"Информация о продукции\"")
    public void step04() {
        CommonFunctions.printStep();

        //Нажать кнопку "Добавить +"
        //В поле "Каталог продукции" ввести значение "1704" и выбрать "1704909900 Белёвская пастила с чёрной смородиной"
        new GUIFunctions().clickButton("Добавить +");
        new CommonFunctions().wait(2);
        new GUIFunctions().inContainer("Сведения о продукции")
                .inField("Каталог продукции").inputValue(PROPERTIES.getProperty("Информация о продукции.Каталог продукции.Номер"))
                .waitForElementDisplayed("//*[contains(text(), '" + PROPERTIES.getProperty("Информация о продукции.Каталог продукции") + "')]")
                .clickByLocator("//*[contains(text(), '" + PROPERTIES.getProperty("Информация о продукции.Каталог продукции") + "')]");

        //В поле "Количество ед. продукции" ввести значение "12"
        //В поле "Единица измерения" выбрать значение из выпадающего списка Выбираем "мм"
        //В поле "Общая стоимость партии товара, включая затраты на транспортировку" ввести значение "25000"
        //В поле "Условия транспортировки и хранения продукции" ввести значение "Условия хранения"
        //Активировать чек-бокс "Розничная продажа"
        //Активировать чек-бокс "Оптовая продажа"
        //В поле "Оценка спроса (в рублях)" ввести значение "15000"
        //Активировать чек-бокс "Готовность к адаптации"
        //Активировать чек-бокс "Наличие сертификата страны размещения"
        //В поле "Номер сертификата" ввести значение "12345"
        //В поле "Дата выдачи" выбрать значение "01.07.2022"
        //В поле "Наименование органа, выдавшего сертификат" ввести значение "Наименование"
        //Активировать чек-бокс "Наличие презентационных материалов на английском языке или на языке страны размещения"
        //Активировать чек-бокс "Наличие товарного знака в стране размещения"
        //В поле "Наименование ЭТП размещения продукции" выбрать значение из выпадающего списка Выбираем "Umico"
        //В поле "Данные дистрибьютора на рынке павильона" ввести значение "Данные"
        new GUIFunctions()
                .inField("Количество ед. продукции").inputValue(PROPERTIES.getProperty("Информация о продукции.Количество ед. продукции")).assertValue()
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
                .inField("Данные дистрибьютора на рынке павильона").inputValue(PROPERTIES.getProperty("Информация о продукции.Данные дистрибьютора на рынке павильона")).assertValue();

        //Нажать кнопку "Добавить"
        new GUIFunctions().clickButton("Добавить");

        //Нажать кнопку "Далее"
        new GUIFunctions()
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Подписать электронной подписью']");
    }

    @Step("Подписание заявки")
    public void step05() {
        CommonFunctions.printStep();

        //Нажать кнопку "Подписать электронной подписью"
        //Выбрать электронный сертификат
        //Нажать кнопку "Подписать"
        //Нажать кнопку "Далее"
        new GUIFunctions()
                .clickButton("Подписать электронной подписью")
                .inField("Выберите сертификат").selectValue(PROPERTIES.getProperty("Подписание заявки.Выберите сертификат")).assertValue()
                .clickButton("Подписать")
                .waitForElementDisplayed("//*[text()='Подписано']")
                .clickButton("Далее")
                .waitForElementDisplayed("//button[contains(text(),'Господдержка. Демонстрационно-дегустационные павильоны АПК')]");

//        //Перейти на сводную информацию о заявке по "хлебным крошкам"
//        new GUIFunctions().clickByLocator("//button[contains(text(),'Господдержка. Демонстрационно-дегустационные павильоны АПК')]");
//        switchTo().alert().accept();
//        new GUIFunctions().waitForElementDisplayed("//div[text()='Номер заявки']/following-sibling::div");
    }
}
