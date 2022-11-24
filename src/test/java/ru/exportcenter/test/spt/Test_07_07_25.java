package ru.exportcenter.test.spt;

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
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;


public class Test_07_07_25 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/SPT/Test_07_07_25/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_07_07_25_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;
    private String processID;
    private String docUUID;
    private String token;

    @Owner(value = "Теребков Андрей")
    @Description("07.07.25 Итог Сертификат формы A : сертификат происхождения другой страны не подтверждается, мотивированный отказ  (Черногория)")
    @Link(name = "Test_07_07_25", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=194314843")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps(){
        precondition();
        step01();
        step02();
        step03();
        step04();
        step05();
//        step06();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Предусловия")
    public void precondition(){
        CommonFunctions.printStep();
        Test_07_07_00 test_07_07_00 = new Test_07_07_00();
        test_07_07_00.url = "https://lk.t.exportcenter.ru/promo-service?key=service-spt&serviceId=dc83e6b2-138f-4a39-99ca-117d8f8a27c8&next_query=true";
        test_07_07_00.login = PROPERTIES.getProperty("Авторизация.Email");
        test_07_07_00.password = PROPERTIES.getProperty("Авторизация.Пароль");
        test_07_07_00.code = PROPERTIES.getProperty("Авторизация.Код");
        test_07_07_00.forma = PROPERTIES.getProperty("Форма");

//       Проставить значения для smevFlag путем вызова API (vs2 = positive2, vs9 = negative)
        test_07_07_00.smevFlag = "{\"smevSkip\":true,\"__coment1__\":\"//smevSkip=true//smevAction[positive,negative,techError]\",\"smevAction\":{\"vs1\":\"positive\",\"vs2\":\"positive2\",\"vs3\":\"positive\",\"vs4\":\"positive\",\"vs5\":\"positive\",\"vs6\":\"positive\",\"vs7\":\"positive\",\"vs8\":\"positive\",\"vs9\":\"negative\",\"vs9pay\":\"positive\",\"vs9cmnt\":\"positive\",\"vs10\":\"positive\"},\"__comment2__\":\"//smevSkip=false//smevMode[dev,prod]\",\"smevMode\":\"dev\"}";
        test_07_07_00.isNegative = true;

        test_07_07_00.steps();

        requestNumber = test_07_07_00.requestNumber;
        processID = test_07_07_00.processID;
        docUUID = test_07_07_00.docUUID;
        token = test_07_07_00.token;

        System.out.println("processID = " + processID + "; docUUID = " + docUUID + "; token = " + token + "; requestNumber = " + requestNumber);
    }

    @Step("Ввод данных в карточку «Информация о заявителе / Информация об импортере")
    public void step01() {
        CommonFunctions.printStep();


        System.out.println(
                $x("//span[text()='Наименование организации']/following-sibling::span").getText()
                        + " == "
                        + PROPERTIES.getProperty("Наименование организации")
        );
        System.out.println(
                $x("//span[text()='Юридический адрес']/following-sibling::span").getText()
                        + " == "
                        + PROPERTIES.getProperty("Юридический адрес")
        );
        System.out.println(
                $x("//span[text()='ИНН']/following-sibling::span").getText()
                        + " == "
                        + PROPERTIES.getProperty("ИНН")
        );
        System.out.println(
                $x("//span[text()='КПП']/following-sibling::span").getText()
                        + " == "
                        + PROPERTIES.getProperty("КПП")
        );
        System.out.println(
                $x("//span[text()='ОГРН']/following-sibling::span").getText()
                        + " == "
                        + PROPERTIES.getProperty("ОГРН")
        );

        System.out.println(
                $x("(//input[contains(@class,'KrInput_input__xg4vc undefined')])[3]").getValue()
                        + " == "
                        + PROPERTIES.getProperty("Email")
        );
        System.out.println(
                $x("//*[@id='form-open-panel']/div[2]/div/form/div[1]/div/div[2]/div/div/div[12]/div/div[1]/div/div/div[2]/div/div/input").getValue()
                        + " == "
                        + PROPERTIES.getProperty("Телефон")
        );

        assertEquals(
                $x("//span[text()='Наименование организации']/following-sibling::span").getText()
                , PROPERTIES.getProperty("Наименование организации")
        );
        assertEquals(
                $x("//span[text()='Юридический адрес']/following-sibling::span").getText()
                , PROPERTIES.getProperty("Юридический адрес")
        );
        assertEquals(
                $x("//span[text()='ИНН']/following-sibling::span").getText()
                , PROPERTIES.getProperty("ИНН")
        );
        assertEquals(
                $x("//span[text()='КПП']/following-sibling::span").getText()
                , PROPERTIES.getProperty("КПП")
        );
        assertEquals(
                $x("//span[text()='ОГРН']/following-sibling::span").getText()
                , PROPERTIES.getProperty("ОГРН")
        );
        assertEquals(
                $x("(//input[contains(@class,'KrInput_input__xg4vc undefined')])[3]").getValue()
                , PROPERTIES.getProperty("Email")
        );
        assertEquals(
                $x("//*[@id='form-open-panel']/div[2]/div/form/div[1]/div/div[2]/div/div/div[12]/div/div[1]/div/div/div[2]/div/div/input").getValue()
                , PROPERTIES.getProperty("Телефон")
        );
        assertEquals(
                "" + $x("//*[@id='form-open-panel']/div[2]/div/form/div[2]/div/div[2]/div/div/div[3]/div/div[2]/div/label/div/input").isSelected()
                , "true"
        );

        new GUIFunctions()
                .inContainer("Информация о заявителе")
                .inField("Наименование организации (на английском языке)").inputValue(PROPERTIES.getProperty("Наименование"))
                .inField("Адрес на английском языке").inputValue(PROPERTIES.getProperty("Адрес по контракту"));


//        new GUIFunctions()
//                .inContainer("Информация о заявителе")
//                .inField("E-mail").inputValue(PROPERTIES.getProperty("Email"));

        new GUIFunctions()
                .inContainer("Информация об импортере")
                .inField("Наименование (на английском языке)").inputValue(PROPERTIES.getProperty("Наименование"))
                .inField("Страна").selectValue(PROPERTIES.getProperty("Страна"))
                .inField("Адрес на английском языке").inputValue(PROPERTIES.getProperty("Адрес по контракту"))
                .waitForLoading();

        repitClick("//div[text()='Информация об условиях поставки']", 20);

        new GUIFunctions()
                .waitForLoading();
    }

    @Step("Ввод данных в карточку «Информация об условиях поставки")
    public void step02() {
        CommonFunctions.printStep();
        CommonFunctions.wait(5);
//          Проверка. Грузоотправитель
        assertEquals(
                $x("//*[@id='form-open-panel']/div[2]/div/form/div[1]/div/div[2]/div/div/div[3]/div/div[1]/div/label/div[1]/div").exists()
                , true
        );

//          Проверка. Грузополучатель известен
        assertEquals(
                $x("//div[@class='Radio_checkMark__18knp Radio_checkedWrapper__1JyWl']//div[1]").exists()
                , true
        );

        new GUIFunctions()
                .inField("Наименование (на английском языке)").inputValue(PROPERTIES.getProperty("Наименование"))
                .inField("Страна").selectValue(PROPERTIES.getProperty("Страна"))
                .inField("Адрес на английском языке").inputValue(PROPERTIES.getProperty("Адрес по контракту"));

        repitClick("//div[text()='Маршрут следования']", 20);

        new GUIFunctions()
                .waitForLoading();

    }

    @Step("Ввод данных в карточку «Маршрут следования")
    public void step03() {
        CommonFunctions.printStep();
//        assertEquals(
//                "" + $x("//*[@id='form-open-panel']/div[2]/div/form/div[1]/div/div[2]/div/div/div[3]/div/div[1]/div/label/div[1]/div").exists()
//                , "true"
//        );

        new GUIFunctions()
                .inContainer("Место отправления").inField("Населенный пункт").inputValue(PROPERTIES.getProperty("Населенный пункт отправления"))
                .inContainer("Место отправления").inField("Вид транспорта").selectValue(PROPERTIES.getProperty("Вид транспорта"));


        $x("(//input[@class='KrDropdown_input__1h8gb KrDropdown_inputPointer__CFf-G'])[3]").setValue("Австрия");
        $x("(//input[@class='KrInput_input__xg4vc undefined'])[2]").setValue(PROPERTIES.getProperty("Населенный пункт назначения"));

        new GUIFunctions()
                .inField("Предполагаемая / фактическая дата отгрузки").inputValue(PROPERTIES.getProperty("Предполагаемая / фактическая дата отгрузки"));

        repitClick("//div[text()='Информация о партии товаров']", 20);

        new GUIFunctions()
                .waitForLoading();
    }

    @Step("Ввод данных в карточку «Информация о партии товаров»")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .clickButton("Добавить +")
                .inContainer("Добавление продукции")
                .inField("Каталог продукции").selectValue(PROPERTIES.getProperty("Каталог продукции"))
                .inField("Наименование продукции на английском языке").inputValue(PROPERTIES.getProperty("EngName"))
                .waitForLoading()
                .clickButton("Документы отсутствуют")
                .inField("Единица измерения товара").selectValue(PROPERTIES.getProperty("Единица измерения товара"))
                .inField("Количество товара").inputValue(PROPERTIES.getProperty("Количество товара"))
                .inField("Количество мест").inputValue(PROPERTIES.getProperty("Количество мест"))
                .inField("Вид упаковки").selectValue(PROPERTIES.getProperty("Вид упаковки"))
                .clickButton("Сохранить")
                .waitForLoading();

        new GUIFunctions()
                .waitForLoading()
                .clickButton("Отправить на проверку")
                .waitForLoading()
//                .clickButton("К перечню заявлений")
//                .waitForLoading()
                ;

    }

    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере» [4]")
    public void step05() {
        CommonFunctions.printStep();

        $x("(//div[@class='KrDropdownMenu_container__3c8fs']//button)[3]").click();
        $x("//li[@class='KrDropdownMenu_option__ylk9A KrDropdownMenu_primary__3oZmg']//span[1]").click();

        new GUIFunctions()
                .inField("Дата оформления").inputValue(PROPERTIES.getProperty("Дата оформления"))
                .inField("Номер").inputValue(PROPERTIES.getProperty("Номер"));
        $x("//*[text()='Каталог продукции']/ancestor::div[@class='Grid_container__2Nvhh']//following::input[@type='file']").sendKeys("C:\\auto-tests\\tmp.pdf");

        $x("//textarea[contains(@class,'Input_input__29Pew Input_textarea__2U8uL')]").setValue("Доп. Инфо");


//

        new GUIFunctions()
                .waitForLoading()
//                .clickButton("Сохранить")
//                .waitForLoading()
//                .clickButton("Сохранить")
//                .waitForURL("")
        ;
        while ($x("(//div[@class='Button_primary__27YYO']//span)[2]").isDisplayed()) {
            System.out.println("Каталог продукции / Сохранить");
            $x("(//div[@class='Button_primary__27YYO']//span)[2]").click();
            CommonFunctions.wait(10);
        }

        // Проверки...
        // Проверки...

        new GUIFunctions()
                .clickButton("Продолжить")
        ;
        // Проверки...
        // Проверки...
        new GUIFunctions()



//                .inField("Номер").inputValue("111")
//                .inField("Дата").inputValue(PROPERTIES.getProperty("Дата оформления"))
                .waitForLoading();

//        $x("//*[text()='Дополнение проекта заявления']/ancestor::div[@class='Grid_container__2Nvhh']//following::input[@type='file']").sendKeys("C:\\auto-tests\\tmp.pdf");
        $x("//[text()='Дополнение проекта заявления']/following::[text()='Загрузите документ'][1]").sendKeys("C:\\auto-tests\\tmp.pdf");
        new GUIFunctions().waitForURL("")
//                .clickButton("Продолжить")

        ;
//        CommonFunctions.wait(15);
//
//        refresh();
////      refreshTab("//span[text()='№" + requestNumber + "']", 20);
//
//        new GUIFunctions()
//                .waitForLoading()
//                .clickButton("№" + requestNumber)
//                .waitForLoading()
//                .clickButton("Продолжить")
//                .waitForLoading()
//                .clickButton("Перейти к заявлению на экспертизу")
//                .waitForLoading();
//
//        new GUIFunctions()
//                .inField("Организация").selectValue(PROPERTIES.getProperty("Организация"))
//                .inField("ФИО уполномоченного лица").selectValue("Отров Отр Отрович")
//                .waitForLoading()
//                .clickButton("Продолжить")
//                .waitForLoading();
//
//        new GUIFunctions()
//                .clickButton("Подписать и отправить")
//                .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022").assertValue()
//                .clickButton("Подписать")
//                .waitForLoading()
//                .clickButton("К перечню заявлений")
//                .waitForLoading();
    }

    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере» [4]")
    public void step06() {
        CommonFunctions.printStep();

        CommonFunctions.wait(15);

        refresh();
//      refreshTab("//span[text()='№" + requestNumber + "']", 20);

        new GUIFunctions()
                .waitForLoading()
                .clickButton("№" + requestNumber)
                .waitForLoading()
                .clickButton("Продолжить")
                .waitForLoading().waitForURL("")
                .clickButton("Перейти к заявлению на экспертизу")
                .waitForLoading();
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