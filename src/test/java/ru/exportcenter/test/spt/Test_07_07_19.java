package ru.exportcenter.test.spt;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import functions.gui.ext.XPath;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;

public class Test_07_07_19 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/SPT/Test_07_07_19/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_07_07_19_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    public String requestNumber;
    private String processID;
    private String docUUID;
    private String token;

    @Owner(value = "Теребков Андрей")
    @Description("07.07.19 СТ-1 Узбекистан - СПТ (реэкспорт) + оплата + успешное оформление")
    @Link(name = "Test_07_07_19", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=188868781")
    @Test(retryAnalyzer = RunTestAgain.class)

    public void steps() {
        precondition();
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Предусловия")
    public void precondition() {
        CommonFunctions.printStep();
        Test_07_07_00 test_07_07_00 = new Test_07_07_00();
        test_07_07_00.url = "https://lk.t.exportcenter.ru/promo-service?key=service-spt&serviceId=dc83e6b2-138f-4a39-99ca-117d8f8a27c8&next_query=true";
        test_07_07_00.login = PROPERTIES.getProperty("Авторизация.Email");
        test_07_07_00.password = PROPERTIES.getProperty("Авторизация.Пароль");
        test_07_07_00.code = PROPERTIES.getProperty("Авторизация.Код");
        test_07_07_00.forma = PROPERTIES.getProperty("Форма");

        test_07_07_00.smevFlag = "{\"smevSkip\":true,\"__coment1__\":\"//smevSkip=true//smevAction[positive,negative,techError]\",\"smevAction\":{\"vs1\":\"positive\",\"vs2\":\"positive\",\"vs3\":\"positive\",\"vs4\":\"positive\",\"vs5\":\"positive\",\"vs6\":\"positive\",\"vs7\":\"positive\",\"vs8\":\"positive\",\"vs9\":\"positive\",\"vs9pay\":\"positive\",\"vs9cmnt\":\"positive\",\"vs10\":\"positive\"},\"__comment2__\":\"//smevSkip=false//smevMode[dev,prod]\",\"smevMode\":\"dev\"}";
        test_07_07_00.isNegative = false;

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
                $x("//input[contains(@class,'KrInput_input__xg4vc undefined')]").getValue()
                        + " == "
                        + PROPERTIES.getProperty("Email")
        );
        System.out.println(
                $x("(//input[contains(@class,'KrInput_input__xg4vc undefined')])[2]").getValue()
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
                $x("//input[contains(@class,'KrInput_input__xg4vc undefined')]").getValue()
                , PROPERTIES.getProperty("Email")
        );
        assertEquals(
                $x("(//input[contains(@class,'KrInput_input__xg4vc undefined')])[2]").getValue()
                , PROPERTIES.getProperty("Телефон")
        );
        assertEquals(
                "" + $x("//*[@id='form-open-panel']/div[2]/div/form/div[2]/div/div[2]/div/div/div[3]/div/div[2]/div/label/div/input").isSelected()
                , "true"
        );

        new GUIFunctions()
                .inField("Наименование").inputValue(PROPERTIES.getProperty("Наименование"))
                .inField("Страна").selectValue(PROPERTIES.getProperty("Страна"))
                .inField("Адрес по контракту (договору) на русском или английском языке").inputValue(PROPERTIES.getProperty("Адрес по контракту"))
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
                .inField("Наименование").inputValue(PROPERTIES.getProperty("Наименование"))
                .inField("Страна").selectValue(PROPERTIES.getProperty("Страна"))
                .inField("Адрес по контракту (договору) на русском или английском языке").inputValue(PROPERTIES.getProperty("Адрес по контракту"));

        repitClick("//div[text()='Маршрут следования']", 20);

        new GUIFunctions()
                .waitForLoading();

    }

    @Step("Ввод данных в карточку «Маршрут следования")
    public void step03() {
        CommonFunctions.printStep();
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

    @Step("Ввод данных в карточку «Информация о партии товаров»")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .clickButton("Добавить +")
                .inContainer("Добавление продукции")
                .inField("Каталог продукции").selectValue(PROPERTIES.getProperty("Каталог продукции"))
                .waitForLoading()
                .clickButton("Сертификат происхождения товара, выданный другой страной")
                .inField("Страна, выдавшая сертификат").selectValue(PROPERTIES.getProperty("Страна, выдавшая сертификат"))
                .inField("Номер сертификата").inputValue(PROPERTIES.getProperty("Номер сертификата"))
                .inField("Вид сертификата").inputValue(PROPERTIES.getProperty("Вид сертификата"))
                .inField("Дата выдачи сертификата").inputValue(PROPERTIES.getProperty("Дата выдачи сертификата"))
                .inField("Номер бланка").inputValue(PROPERTIES.getProperty("Номер бланка"))
                .inField("Единица измерения товара").selectValue(PROPERTIES.getProperty("Единица измерения товара"))
                .inField("Количество товара").inputValue(PROPERTIES.getProperty("Количество товара"))
                .inField("Количество мест").inputValue(PROPERTIES.getProperty("Количество мест"))
                .inField("Вид упаковки").selectValue(PROPERTIES.getProperty("Вид упаковки"))
//              .inField("Масса в кг (нетто)").inputValue(PROPERTIES.getProperty("Масса в кг (нетто)"))
                .inField("Масса в кг (брутто)").inputValue(PROPERTIES.getProperty("Масса в кг (брутто)"))
                .clickButton("Сохранить")
                .waitForLoading();

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

        new GUIFunctions()
                .waitForLoading()
                .clickButton("№" + requestNumber)
                .waitForLoading()
                .clickButton("Продолжить")
                .waitForLoading()
                .clickButton("Перейти к заявлению на экспертизу");

        new GUIFunctions()
                .clickButton("Проект заявления на экспертизу")
                .waitForLoading()
                .waitForElementDisplayed("//*[@id='form-open-panel']/div[2]/div/form/div[1]/div/div[2]/div/div");

        CommonFunctions.wait(10);

        String text = $x("//*[@id='form-open-panel']/div[2]/div/form/div[1]/div/div[2]/div/div").getText();

        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Наименование организации")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Юридический адрес")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("ИНН")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("КПП")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("ОГРН")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Страна отправления")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Населенный пункт отправления")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Вид транспорта")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Страна назначения")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Населенный пункт назначения")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Предполагаемая / фактическая дата отгрузки")));

        $x("(//div[@class='KrDropdownMenu_container__3c8fs']//button)[2]").click();
        $x("//li[@class='KrDropdownMenu_option__ylk9A KrDropdownMenu_primary__3oZmg']//span[1]").click();

        new GUIFunctions()
                .waitForLoading()
                .waitForElementDisplayed("//*[@id='root']/div/div[3]/div/div/div/div[2]/div/div/div/div/div[2]/div[2]");

        text = $x("//*[@id='root']/div/div[3]/div/div/div/div[2]/div/div/div/div/div[2]/div[2]").getText();

        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Страна, выдавшая сертификат")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Номер сертификата")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Вид сертификата")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Дата выдачи сертификата")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Номер бланка")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Единица измерения товара")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Количество товара")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Количество мест")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Вид упаковки")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Масса в кг (нетто)")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Масса в кг (брутто)")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Наименование производителя")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Каталог продукции. пр.")));
        Assert.assertEquals(true, text.contains(PROPERTIES.getProperty("Описание")));

        new GUIFunctions()
                .clickButton("Закрыть");

        new GUIFunctions()
                .inField("Организация").selectValue(PROPERTIES.getProperty("Организация"))
                .inField("ФИО уполномоченного лица").selectValue("Отров Отр Отрович")
                .waitForLoading()
                .clickButton("Продолжить")
                .waitForLoading();

        new GUIFunctions()
                .clickButton("Подписать и отправить")
                .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022").assertValue()
                .clickButton("Подписать")
                .waitForLoading()
                .clickButton("К перечню заявлений")
                .waitForLoading();
    }

    @Step("Ввод данных в карточку «Информация о заявителе»  и в карточку «Информация об импортере» [4]")
    public void step06() {
        CommonFunctions.printStep();

        CommonFunctions.wait(25);

        refresh();

        new GUIFunctions()
                .waitForLoading()
                .clickButton("№" + requestNumber)
                .waitForLoading()
                .clickButton("Продолжить")
                .waitForLoading();

        $x("(//div[@class='KrDropdownMenu_container__3c8fs']//button)[3]").click();
        $x("//li[@class='KrDropdownMenu_option__ylk9A KrDropdownMenu_primary__3oZmg']//span[1]").click();

        new GUIFunctions()
                .inField("Дата оформления").inputValue(PROPERTIES.getProperty("Дата оформления"))
                .inField("Номер").inputValue(PROPERTIES.getProperty("Номер"))

                .uploadFile("Сертификат происхождения товара, выданный другой страной", "C:\\auto-tests\\tmp.pdf")
//              .uploadFile("Сертификат происхождения товара, выданный другой страной", "/share/" + WAY_TEST + "tmp.pdf")
//                .uploadFile("Укажите информацию о товаре (продукции), которую считаете необходимым сообщить дополнительно, например страна производства (произрастания) продукции, сорт продукции и т.д.", "C:\\auto-tests\\tmp.pdf")
//                .uploadFile("Укажите информацию о товаре (продукции), которую считаете необходимым сообщить дополнительно, например страна производства (произрастания) продукции, сорт продукции и т.д.", "/share/" + WAY_TEST + "tmp.pdf")
//                .clickButton("Сохранить")
//                .waitForURL("")
        ;

       // $x("//span[text()='Загрузите документ']").uploadFile("C:\\auto-tests\\tmp.pdf");
System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
 //       $x("//span[text()='Загрузите документ']']").click();

        //*[@id='uploadInput-50ae27eb-761d-4bbd-8b69-3a80371bb7de']

        $x("//*[@id='uploadInput-50ae27eb-761d-4bbd-8b69-3a80371bb7de']").sendKeys("C:\\auto-tests\\tmp.pdf");
        $x("//*[@id='uploadInput-50ae27eb-761d-4bbd-8b69-3a80371bb7de']").submit();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
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