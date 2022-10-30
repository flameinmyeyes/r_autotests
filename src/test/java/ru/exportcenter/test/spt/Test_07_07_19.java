package ru.exportcenter.test.spt;

import framework.RunTestAgain;
import framework.integration.JupyterLabIntegration;
import framework.Ways;
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
import static org.testng.Assert.assertEquals;

public class Test_07_07_19 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/SPT/Test_07_07_19/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_07_07_19_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;

    public boolean trueValue = true;

    @Owner(value = "Теребков Андрей")
    @Description("07.07.19 СТ-1 Узбекистан - СПТ (реэкспорт) + оплата + успешное оформление")
    @Link(name = "Test_07_07_19", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=188868781")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException {
        precondition();
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


    @Step("Предусловия")
    public void precondition() throws AWTException {
        CommonFunctions.printStep();
        Test_07_07_00 test_07_07_00 = new Test_07_07_00();
        test_07_07_00.url = "https://lk.t.exportcenter.ru/promo-service?key=service-spt&serviceId=dc83e6b2-138f-4a39-99ca-117d8f8a27c8&next_query=true";
        test_07_07_00.login = PROPERTIES.getProperty("Авторизация.Email");
        test_07_07_00.password = PROPERTIES.getProperty("Авторизация.Пароль");
        test_07_07_00.code = PROPERTIES.getProperty("Авторизация.Код");
        test_07_07_00.forma = PROPERTIES.getProperty("Форма");

        test_07_07_00.CompanyType = PROPERTIES.getProperty("CompanyType");

        test_07_07_00.NameOrganisaiton = PROPERTIES.getProperty("Наименование организации");
        test_07_07_00.JuricAddress = PROPERTIES.getProperty("Юридический адрес");
        test_07_07_00.INN = PROPERTIES.getProperty("ИНН");
        test_07_07_00.KPP = PROPERTIES.getProperty("КПП");
        test_07_07_00.OGRN = PROPERTIES.getProperty("ОГРН");
        test_07_07_00.email = PROPERTIES.getProperty("Email");
        test_07_07_00.phone = PROPERTIES.getProperty("Телефон");

        test_07_07_00.FIO = PROPERTIES.getProperty("Фамилия Имя Отчество");
        test_07_07_00.Address = PROPERTIES.getProperty("Адрес");
        test_07_07_00.INN_IP =PROPERTIES.getProperty("ИНН_ИП");
        test_07_07_00.OGRNIP = PROPERTIES.getProperty("ОГРНИП");
        test_07_07_00.email_IP = PROPERTIES.getProperty("Email_ИП");
        test_07_07_00.phone_IP = PROPERTIES.getProperty("Телефон_ИП");

        test_07_07_00.steps();
        requestNumber = test_07_07_00.requestNumber;
    }

    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере» [4]")
    public void step01() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .inField("E-mail").inputValue(PROPERTIES.getProperty("Email"))
                .inField("Телефон").inputValue(PROPERTIES.getProperty("Телефон"))
                .inField("Наименование").inputValue(PROPERTIES.getProperty("Наименование"))
                .inField("Страна").selectValue(PROPERTIES.getProperty("Страна"))
                .inField("Адрес по контракту (договору) на русском или английском языке").inputValue(PROPERTIES.getProperty("Адрес по контракту"))
                .waitForLoading();

        repitClick("//div[text()='Информация об условиях поставки']", 20);

        new GUIFunctions()
                .waitForLoading();
    }

    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере» [6]")
    public void step02() {
        CommonFunctions.printStep();

//        assertEquals(
//                "" + $x("//*[@id='form-open-panel']/div[2]/div/form/div[1]/div/div[2]/div/div/div[3]/div/div[1]/div/label/div[1]/div").exists()
//                , "true"
//        );
//
//        assertEquals(
//                $x("//div[@class='Radio_checkMark__18knp Radio_checkedWrapper__1JyWl']//div[1]").isSelected()
//                , "true"
//        );


        new GUIFunctions()
                .inField("Наименование").inputValue(PROPERTIES.getProperty("Наименование"))
                .inField("Страна").selectValue(PROPERTIES.getProperty("Страна"))
                .inField("Адрес по контракту (договору) на русском или английском языке").inputValue(PROPERTIES.getProperty("Адрес по контракту"));

        repitClick("//div[text()='Маршрут следования']", 20);

        new GUIFunctions()
                .waitForLoading();
    }

    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере»")
    public void step03() {
        CommonFunctions.printStep();
//        assertEquals(
//                "" + $x("//*[@id='form-open-panel']/div[2]/div/form/div[1]/div/div[2]/div/div/div[3]/div/div[1]/div/label/div[1]/div").exists()
//                , "true"
//        );

        new GUIFunctions()
                .inContainer("Место отправления").inField("Населенный пункт").inputValue(PROPERTIES.getProperty("Населенный пункт отправления"))
                .inContainer("Место отправления").inField("Вид транспорта").selectValue(PROPERTIES.getProperty("Вид транспорта"));

//        new GUIFunctions().inContainer("Место назначения").inField("Населенный пункт").inputValue(PROPERTIES.getProperty("Населенный пункт назначения"));
        $x("(//input[@class='KrInput_input__xg4vc undefined'])[2]").setValue(PROPERTIES.getProperty("Населенный пункт назначения"));

        new GUIFunctions()
                  .inField("Предполагаемая / фактическая дата отгрузки").inputValue(PROPERTIES.getProperty("Предполагаемая / фактическая дата отгрузки"));

        repitClick("//div[text()='Информация о партии товаров']", 20);

        new GUIFunctions()
                .waitForLoading();
    }

    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере» [4]")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .clickButton("Добавить +")
                .inContainer("Добавление продукции")
                    .inField("Каталог продукции").selectValue("0306240000 Крабы")
                    .clickButton("Сертификат происхождения товара, выданный другой страной")
                    .inField("Страна, выдавшая сертификат").selectValue("Белоруссия")
                    .inField("Номер сертификата").inputValue("111-11Y")
                    .inField("Вид сертификата").inputValue("Реэкспорт")
                    .inField("Номер бланка").inputValue("111-11Y")
                    .inField("Дата выдачи сертификата").inputValue("01.10.2022")

//                  В поле Наименование товара не совпадает с наименованием в сертификате происхождения товара другой страны не указано значение  (не заполнен чекбокс)
                    .inField("Единица измерения товара").selectValue("Килограмм")
                    .inField("Количество товара").inputValue("100")
                    .inField("Количество мест").inputValue("10")
                    .inField("Вид упаковки").selectValue("Контейнер средней грузоподъемности для массовых грузов из древесного материала")
//                  .inField("Масса в кг (нетто)").inputValue("100")
                    .inField("Масса в кг (брутто)").inputValue("110")
                    .clickButton("Сохранить");

        new GUIFunctions()
                .waitForLoading()
                .clickButton("Отправить на проверку")
                .waitForLoading()
                .clickButton("К перечню заявлений")
                .waitForLoading();

    }


    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере» [4]")
    public void step05() {
        CommonFunctions.printStep();

        CommonFunctions.wait(15);

        refresh();
//        refreshTab("//span[text()='№" + requestNumber + "']", 20);

        new GUIFunctions()
                .waitForLoading()
                .clickButton("№" + requestNumber)
                .waitForLoading()
                .clickButton("Продолжить")
                .waitForLoading()
                .clickButton("Перейти к заявлению на экспертизу")
                .waitForLoading();

        new GUIFunctions()
                .inField("Организация").selectValue("Союз «Пензенская областная торгово-промышленная палата»")
                .inField("ФИО уполномоченного лица").selectValue("Иванов  Иван Иванович")
                .waitForLoading()
                .clickButton("Продолжить")
                .waitForLoading();

        new GUIFunctions()
                .clickButton("Подписать и отправить")
                .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022").assertValue()
                .clickButton("Подписать")
                .waitForElementDisplayed("//*[text()='Подписано']")
                .clickButton("Далее")
                .waitForLoading()
                .clickButton("К перечню заявлений")
                .waitForLoading();

    }


    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new functions.gui.GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            System.out.println("refresh()");
            CommonFunctions.wait(5);
        }
    }

    private void repitClick(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            System.out.println("repitClick()");
            new functions.gui.GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                System.out.println("тут");
                break;
            }
            $x("//button[text()='Продолжить']").click();
            CommonFunctions.wait(5);
        }
    }

}