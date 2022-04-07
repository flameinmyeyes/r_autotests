package ru.exportcenter.test.agroexpress.Test_03_07_02_1_new;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import functions.common.DateFunctions;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.test.agroexpress.HooksTEST_agroexpress;

import java.time.Duration;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_03_07_02_1_10 extends HooksTEST_agroexpress {

    public String WAY_TEST = Ways.TEST.getWay() + "/agroexpress/Test_03_07_02_1_new/Test_03_07_02_1_10/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_03_07_02_1_10.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String docNum;
    private String processID;

    @Owner(value="Камаев Евгений")
    @Description("03 07 02.1.10 Ввод и редактирование данных Заявки (Сборный груз). Отправка Заявки на рассмотрение")
    @Link(name="Test_03_07_02_1_10", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123880785")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
        step08();
        step09();
        step10();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }


    @Step("Авторизация")
    public void step01() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .authorization("test-otr@yandex.ru", "Password1!", "1234")
                .waitForLoading();

        $x("//*[contains(text(), 'Логистика. Доставка продукции \"Агроэкспрессом\"')]").shouldBe(Condition.visible, Duration.ofSeconds(60));

        if ($x("//*[contains(text(), 'Информация о заявителе')]").isDisplayed()) {
            $x("//button[contains(text(), 'Логистика. Доставка продукции \"Агроэкспрессом\". Заявка')]").click();
            switchTo().alert().accept();
        }

        refreshTab("//*[contains(text(), 'Продолжить')]", 20);
        processID = CommonFunctions.getProcessIDFromURL();

//        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST,"processID.txt");

        new GUIFunctions()
                .closeAllPopupWindows()
                .clickButton("Продолжить");
    }

    @Step("Навигация")
    public void step02() {
        CommonFunctions.printStep();
//        new GUIFunctions().waitForElementDisplayed("//button[contains(text(), 'Логистика. Доставка продукции')]")

        CommonFunctions.wait(3);


//        if ($x("//div[contains(@class, 'CardInfo_nameBlock' )]/*[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']").isDisplayed()){
//            refreshTab("//*[contains(text(), 'Продолжить')]", 60);
//            new GUIFunctions().clickButton("Продолжить");
//        }

    }

    @Step("Блок «Информация о компании»")
    public void step03() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о компании")
                .inField("Почтовый адрес").inputValue(PROPERTIES.getProperty("Информация о компании.Почтовый адрес")).assertNoControl();
    }

    @Step("Блок «Информация о заявителе»")
    public void step04() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о заявителе")
                .inField("Дополнительный контакт").setCheckboxON().assertNoControl()
                .inField("ФИО").inputValue(PROPERTIES.getProperty("Информация о заявителе.ФИО")).assertNoControl().assertValue()
                .inField("Телефон").inputValue(PROPERTIES.getProperty("Информация о заявителе.Телефон")).assertNoControl().assertValue()
                .inField("Должность").inputValue(PROPERTIES.getProperty("Информация о заявителе.Должность")).assertNoControl().assertValue()
                .inField("Email").inputValue(PROPERTIES.getProperty("Информация о заявителе.Email")).assertNoControl().assertValue();
    }

    @Step("Блок  «Информация для оказания услуги»")
    public void step05() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация для оказания услуги")
                .inField("Город отправления").selectValue(PROPERTIES.getProperty("Информация для оказания услуги.Город отправления")).assertNoControl().assertValue()
                .inField("Город назначения").selectValue(PROPERTIES.getProperty("Информация для оказания услуги.Город назначения")).assertNoControl().assertValue()
//                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON()
//                .inField("Адрес").inputValue(PROPERTIES.getProperty("Информация для оказания услуги.Адрес")).assertNoControl().assertValue()
                .inField("Предполагаемая дата отправления груза").inputValue(DateFunctions.dateShift("dd.MM.yyyy", 14)).assertValue();
    }

    @Step("Блок «Информация о грузе»")
    public void step06() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о грузе")
                .clickButton("Сборный груз")
                .waitForElementDisplayed("//*[text()= 'Температурный режим на всю партию (от -30°C до 0°C или от 0°C до +30°C) ']")
                .inField("Температурный режим на всю партию (от -30°C до 0°C или от 0°C до +30°C) ").setCheckboxON().assertNoControl()
                .inField("От").inputValue(PROPERTIES.getProperty("Информация о грузе.От")).assertNoControl().assertValue()
                .inField("До").inputValue(PROPERTIES.getProperty("Информация о грузе.До")).assertNoControl().assertValue()
                .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
               .inField("Наименование продукции").selectValue(PROPERTIES.getProperty("Информация о грузе.Наименование продукции")).assertNoControl().assertValue()
                .inField("Код ТН ВЭД").assertValue(PROPERTIES.getProperty("Информация о грузе.Код ТН ВЭД")).assertNoControl().assertUneditable()
                .inField("Вес продукции, кг").inputValue(PROPERTIES.getProperty("Информация о грузе.Вес продукции, кг")).assertNoControl().assertValue()
                .inField("Упаковка").selectValue(PROPERTIES.getProperty("Информация о грузе.Упаковка")).assertNoControl().assertValue()
                .inField("Длина, см").inputValue(PROPERTIES.getProperty("Информация о грузе.Длина, см")).assertValue().assertNoControl().assertValue()
                .inField("Ширина, см").inputValue(PROPERTIES.getProperty("Информация о грузе.Ширина, см")).assertValue().assertNoControl().assertValue()
                .inField("Высота, см").inputValue(PROPERTIES.getProperty("Информация о грузе.Высота, см")).assertValue().assertNoControl().assertValue()
                .inField("Количество грузовых мест, шт").inputValue(PROPERTIES.getProperty("Информация о грузе.Количество грузовых мест, шт")).assertValue().assertNoControl().assertValue()
                .clickButton("Сохранить")
                .inContainer("Информация о грузе").waitForElementDisplayed("//td[text() = '1']" +
                "/following-sibling::td[text() = '" + PROPERTIES.getProperty("Информация о грузе.Наименование продукции") + "']" +
                "/following-sibling::td[text() = '" + PROPERTIES.getProperty("Информация о грузе.Количество грузовых мест, шт") + "']" +
                "/following-sibling::td[text() = '" + PROPERTIES.getProperty("Информация о грузе.Вес продукции, кг").split(",")[0] + "']" +
                "/following-sibling::td");
        new GUIFunctions().inContainer("Информация о грузе").clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .clickButton("Добавить новую")
                .inField("Наименование продукции").inputValue(PROPERTIES.getProperty("Информация о грузе.Наименование продукции.2")).assertNoControl().assertValue()
                .inField("Код ТН ВЭД").selectValue(PROPERTIES.getProperty("Информация о грузе.Код ТН ВЭД.2")).assertNoControl().assertValue()
                .inField("Вес продукции, кг").inputValue(PROPERTIES.getProperty("Информация о грузе.Вес продукции, кг.2")).assertNoControl().assertValue()
                .inField("Упаковка").selectValue(PROPERTIES.getProperty("Информация о грузе.Упаковка.2")).assertNoControl().assertValue()
                .inField("Длина, см").inputValue(PROPERTIES.getProperty("Информация о грузе.Длина, см.2")).assertValue().assertNoControl()
                .inField("Ширина, см").inputValue(PROPERTIES.getProperty("Информация о грузе.Ширина, см.2")).assertValue().assertNoControl()
                .inField("Высота, см").inputValue(PROPERTIES.getProperty("Информация о грузе.Высота, см.2")).assertValue().assertNoControl()
                .inField("Количество грузовых мест, шт").inputValue(PROPERTIES.getProperty("Информация о грузе.Количество грузовых мест, шт.2"))
                .assertValue().assertNoControl()
                .clickButton("Сохранить")
                .inContainer("Информация о грузе").waitForElementDisplayed("//td[text() = '2']" +
                "/following-sibling::td[text() = '" + PROPERTIES.getProperty("Информация о грузе.Наименование продукции.2") + "']" +
                "/following-sibling::td[text() = '" + PROPERTIES.getProperty("Информация о грузе.Количество грузовых мест, шт.2") + "']" +
                "/following-sibling::td[text() = '" + PROPERTIES.getProperty("Информация о грузе.Вес продукции, кг.2").split(",")[0] + "']" +
                "/following-sibling::td");
        new GUIFunctions().inContainer("Информация о грузе").clickByLocator("//td[text() = '2']/following-sibling::td//button[@class = 'dropdown-icon']");
        new GUIFunctions().clickByLocator("//td[text() = '2']/following-sibling::td//*[text() = ' Удалить' ]")
                //.clickButton("")
                .waitForElementDisappeared("//td[text() = '2']" +
                        "/following-sibling::td[text() = '" + PROPERTIES.getProperty("Информация о грузе.Наименование продукции.2") + "']" +
                                "/following-sibling::td[text() = '" + PROPERTIES.getProperty("Информация о грузе.Количество грузовых мест, ш.2") + "']" +
                                "/following-sibling::td[text() = '" + PROPERTIES.getProperty("Информация о грузе.Вес продукции, кг.2") + "']" +
                                "/following-sibling::td");
    }

    @Step("Блок  «Информация о грузополучателе»")
    public void step07() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о грузополучателе")
                .inField("Наименование грузополучателя").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Наименование грузополучателя")).assertNoControl()
                .inField("Страна").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Страна")).assertNoControl().assertValue()
                .inField("Город").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Город")).assertNoControl().assertValue()
                .inField("Дом").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Дом")).assertNoControl().assertValue()
                .inField("Регион").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Регион")).assertNoControl().assertValue()
                .inField("Район").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Район")).assertNoControl().assertValue()
                .inField("Улица").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Улица")).assertNoControl().assertValue()
                .inField("Регистрационный номер грузополучателя").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Регистрационный номер грузополучателя")).assertNoControl().assertValue()
                .inField("Телефон").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Телефон")).assertNoControl().assertValue()
                .inField("Представитель грузополучателя").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Представитель грузополучателя")).assertNoControl().assertValue()
                .inField("Email").inputValue(PROPERTIES.getProperty("Информация о грузополучателе.Email")).assertNoControl().assertValue();
    }

    @Step("Блок «Дополнительные услуги»")
    public void step08() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Дополнительные услуги")
//                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertNoControl()
                .inField("Таможенное оформление").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Комплексная услуга таможенного оформления")
                .inField("Оформление ветеринарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении ветеринарных сертификатов")
                .inField("Оформление фитосанитарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении фитосанитарных сертификатов");
    }

    @Step("Отправка Заявки на рассмотрение ")
    public void step09() {
        CommonFunctions.printStep();
        new GUIFunctions().clickButton("Далее").waitForLoading();
        docNum = $x("//div[contains (@class, 'FormHeader_title' )]//span[contains (@class, 'Typography_body' )]").getText().split("№")[1];
        JupyterLabIntegration.uploadTextContent(docNum, WAY_TEST,"docNum.txt");
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST,"processID.txt");
    }

    @Step("Проверка изменения статуса заявки")
    public void step10() {
        CommonFunctions.printStep();
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);
        Assert.assertEquals(status, "Проводится проверка");

        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST,"processID.txt");
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
}
