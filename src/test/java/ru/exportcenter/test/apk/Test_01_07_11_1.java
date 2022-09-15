package ru.exportcenter.test.apk;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.junit.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.awt.*;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class Test_01_07_11_1 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_11_1/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_11_1_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 11.1 Заполнение Заявки на получение услуги, подписание Заявки УКЭП и автоматическая передача Заявки на верификацию")
    @Link(name = "Test_01_07_11_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163299538")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
        precondition();
        step01();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Предусловия")
    public void precondition() throws AWTException, InterruptedException  {
        CommonFunctions.printStep();

        Test_01_07_05_2 test_01_07_05_2 = new Test_01_07_05_2();
        test_01_07_05_2.steps();
    }

    @Step("Переход к заявке")
    public void step01() {
        CommonFunctions.printStep();

        //Перезагрузить страницу
        //Нажать кнопку «Продолжить»
        new GUIFunctions().refreshTab("//*[text()='Продолжить']", 10)
                .clickButton("Продолжить")
                .waitForLoading();
    }

    @Step("Заполнение блока \"Информация о Заявителе\"")
    public void step02() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Далее»
        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Сведения о затратах']");
    }

    @Step("Заполнение блока \"Сведения о затратах\"")
    public void step03() {
        CommonFunctions.printStep();

        //В блоке "Сведения о затратах" нажать кнопку "Добавить +"
        new GUIFunctions().clickButton("Добавить +")
                .waitForElementDisplayed("//*[text()='Затрата']")
                .waitForElementDisplayed("//*[text()='Вид затраты, связанной с сертификацией продукции']")
                .waitForElementDisplayed("//*[text()='Основание понесенных затрат']");

        //Из выпадающего списка "Вид затраты, связанной с сертификацией продукции" выбрать значение "1 Услуги компетентного органа или уполномоченной..."
        //Из выпадающего списка "Основание понесенных затрат" выбрать значение "Требование контракта"
        //Выбрать приложенный файл с устройства формата xlsx и нажать кнопку «Открыть»
        new GUIFunctions().inContainer("Затрата")
                .inField("Вид затраты, связанной с сертификацией продукции").selectValue("1 Услуги компетентного органа или уполномоченной организации в стране экспорта по осуществлению процедур оценки соответствия продукции (регистрации, подтверждения соответствия, испытаний, сертификации и других форм оценки соответствия, установленных законодательством иностранного государства или являющихся условием внешнеэкономического контракта) ")
                .inField("Основание понесенных затрат").selectValue("Требование контракта")
                .uploadFile("Загрузить шаблон","C:\\auto-tests\\Шаблон 1 - фаст (1).xlsm")
                .waitForElementDisplayed("//*[text()='Шаблон 1 - фаст (1).xlsm']/ancestor::a[contains(@class,'FileInput')]/following-sibling::button[contains(@class,'delete')]");
    }

    @Step("Заполнение блока \"Загрузка подтверждающих документов\"")
    public void step04() {
        CommonFunctions.printStep();

        //Выбрать приложенный файл с устройства формата zip и нажать кнопку «Открыть»
        //Выбрать приложенный файл с устройства формата zip и нажать кнопку «Открыть»
        //Нажать на кнопку «Далее»
        new GUIFunctions().inContainer("Загрузка подтверждающих документов")
                .uploadFile("Подтверждающие документы", "C:\\auto-tests\\rec.zip")
                .waitForElementDisplayed("//*[text()='rec.zip']/ancestor::a[contains(@class,'FileInput')]/following-sibling::button[contains(@class,'delete')]")
                .uploadFile("Платежное поручение", "C:\\auto-tests\\payment 228.zip")
                .waitForElementDisplayed("//*[text()='payment 228.zip']/ancestor::a[contains(@class,'FileInput')]/following-sibling::button[contains(@class,'delete')]")
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Сведения о затратах']");
    }

    @Step("Заполнение блока \"Подтверждение сведений заявителем\"")
    public void step05() {
        CommonFunctions.printStep();

        new GUIFunctions().clickByLocator("(//*[text()='Добавить +'])[1]")
                .inField("ОКВЭД2").selectValue("01.11 Выращивание зерновых (кроме риса), зернобобовых культур и семян масличных культур ")
                .inField("Код типа вида деятельности").inputValue("1221");

        Assert.assertEquals("Дополнительный", $x("//*[text()='Признак вида деятельности']/ancestor::div[contains(@class,'TextInput')]//following::textarea").getText());

        new GUIFunctions().clickButton("Сохранить");

        new GUIFunctions()
                .inField("Код по ОКОПФ").inputValue("12247")
                .inField("Наименование по ОКОПФ").inputValue("Частные учреждения")
                .inField("Код по ОКПО").inputValue("12345678")
                .inField("Код по ОКТМО").inputValue("12345678901")
                .inField("Почтовый индекс").inputValue("123456")
                .inField("Дата постановки на налоговый учёт").inputValue("01.08.2022")
                .inField("Наименование субъекта Российской Федерации").selectValue("Новгородская область")
                .inField("Тип населенного пункта").inputValue("город")
                .inField("Наименование населенного пункта").selectValue("Населенный пункт")
                .inField("Тип элемента планировочной структуры").inputValue("улица")
                .inField("Наименование элемента планировочной структуры").inputValue("Волкова")
                .inField("Тип элемента улично-дорожной сети").inputValue("дом")
                .inField("Наименование элемента улично-дорожной сети").selectValue("Улица")
                .inField("Тип объекта адресации").inputValue("Телевизионная")
                .inField("Тип помещения").inputValue("поле")
                .inField("Номер помещения, расположенного в здании или сооружении").inputValue("10")
                .inField("Цифровое или буквенно-цифровое обозначение объекта адресации").inputValue("108")
                .inField("Номер банковского счета").inputValue("40702810000000000046")
                .inField("БИК банка").selectValue("004525659")
                .inField("Корреспондентский счет").inputValue("30101810100000000722");

        new GUIFunctions().inContainer("Данные о руководителе / уполномоченном лице компании")
                .inField("Номер доверенности").inputValue("3434")
                .inField("Дата выдачи").inputValue("01.09.2022")
                .inField("Срок действия").inputValue("01.09.2022")
                .inField("ИНН").inputValue("123456789012")
                .inField("СНИЛС").inputValue("12345678901")
                .inField("Телефон").inputValue("+71234567890")
                .inField("Добавочный номер").inputValue("20");

        new GUIFunctions().inContainer("Контактные данные лица, ответственного за работу в ГИИС \"Электронный бюджет\"")
                .inField("ФИО").selectValue("фва ыва ва ");

        new GUIFunctions().inContainer("Подтверждение сведений Заявителем")
                .inField("ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \"ОРГАНИЗАЦИОННО-ТЕХНОЛОГИЧЕСКИЕ РЕШЕНИЯ 2000\" подтверждает, что на 15.09.2022 не находится в статусе реорганизации").setCheckboxON()
                .inField("ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \"ОРГАНИЗАЦИОННО-ТЕХНОЛОГИЧЕСКИЕ РЕШЕНИЯ 2000\" подтверждает, что на 15.09.2022 не имеет задолженности перед Бюджетом в рамках субсидии").setCheckboxON()
                .inField("ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \"ОРГАНИЗАЦИОННО-ТЕХНОЛОГИЧЕСКИЕ РЕШЕНИЯ 2000\" подтверждает, что на  15.09.2022 заявленные затраты осуществлены на получение необходимых документов о сертификации продукции агропромышленного комплекса на внешних рынках на соответствие требованиям, предъявляемым на внешних рынках, которые могут включать требования заказчика, содержащиеся в контракте").setCheckboxON()
                .clickButton("Далее");
    }

}
