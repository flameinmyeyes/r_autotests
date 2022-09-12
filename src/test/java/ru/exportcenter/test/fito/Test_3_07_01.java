package ru.exportcenter.test.fito;

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
import ru.exportcenter.Hooks;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_3_07_01 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/fito/Test_3_07_01/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_3_07_01_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String docNum;
    private String processID;

    @Owner(value = "Балашов Илья")
    @Description("3.07.01 (Р) Сценарий получения услуги по ЗКФС (положительный результат)")
    @Link(name = "Test_3_07_01", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163308618")

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
        open(P.getProperty("start_URL"));

        //Ввести логин и пароль demo_exporter/password
        new GUIFunctions()
                .authorization(P.getProperty("Авторизация.Email"), P.getProperty("Авторизация.Пароль"))
                .waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");
    }

    @Step("Выбор сервиса")
    public void step02() {
        CommonFunctions.printStep();

        //Перейти во вкладку "Сервисы", выбрать "Государственные"
        new GUIFunctions()
                .selectTab("Сервисы")
//                .waitForElementDisplayed("//div[text()='Государственные']")
                .clickByLocator("//div[@data-history-code='/services/state'][normalize-space(text()='Государственные')]")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/state");

        //В строке "Поиск по разделу" ввести "ФИТО"
        new GUIFunctions()
                .waitForElementDisplayed("//div[@class='js-tabs__block open']//input[@placeholder='Поиск по разделу']")
                .inputInSearchField("Поиск по разделу", "ФИТО")
                .closeAllPopupWindows();

        //Выбрать сервис "Запрос заключения о карантинном фитосанитарном состоянии подкарантинной продукции", нажать "Оформить"
        new GUIFunctions().openSearchResult("Запрос заключения о карантинном фитосанитарном состоянии", "Оформить");
    }

    @Step("Начальный экран")
    public void step03() {
        CommonFunctions.printStep();

        //На "Начальном экране" формирования запроса нажать "Продолжить"
        refreshTab("//*[contains(text(), 'Продолжить')]", 20);

        processID = CommonFunctions.getProcessIDFromURL();
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST,"processID.txt");
    }

    @Step("Блок \"Заявитель/Получатель\"")
    public void step04() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Получатель")
                .inField("Наименование получателя (на английском языке)").inputValue(P.getProperty("Получатель.Наименование получателя (на английском языке)")).assertNoControl().assertValue()
                .inField("Страна").selectValue(P.getProperty("Получатель.Страна")).assertNoControl().assertValue()
                .inField("Юридический адрес на английском языке в формате: номер дома, улица, город, индекс").inputValue(P.getProperty("Получатель.Юридический адрес на английском языке в формате: номер дома, улица, город, индекс")).assertNoControl().assertValue()
                .clickButton("Продолжить");

        //http://uidm.uidm-dev.d.exportcenter.ru/ru/services/camunda-exp-search/713374f0-327a-11ed-b249-a6926da449e9
    }

    @Step("Блок  \"Условия поставки\"")
    public void step05() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Условия поставки")
                .inField("Вид транспорта").selectValue(P.getProperty("Условия поставки.Вид транспорта")).assertNoControl().assertValue()
                .inField("Страна назначения").selectValue(P.getProperty("Условия поставки.Страна назначения")).assertNoControl().assertValue()
                .inField("Пункт ввоза в стране назначения").inputValue(P.getProperty("Условия поставки.Пункт ввоза в стране назначения")).assertNoControl().assertValue()

                .inField("Тип документа о происхождении груза. Если тип документа в списке отсутствует — выберите «другое»").selectValue(P.getProperty("Условия поставки.Тип документа о происхождении груза. Если тип документа в списке отсутствует — выберите «другое»")).assertNoControl().assertValue()
                .inField("Номер документа на груз").inputValue(P.getProperty("Условия поставки.Номер документа на груз")).assertNoControl().assertValue()
                .inField("Дата").inputValue(DateFunctions.dateToday("dd.MM.yyyy")).assertNoControl().assertValue()
                .clickButton("Продолжить");
    }

    @Step("Блок \"Добавление продукции\"")
    public void step06() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о грузе")
                .clickButton("Сборный груз")
                .waitForElementDisplayed("//*[text()= 'Температурный режим на всю партию (от -30°C до 0°C или от 0°C до +30°C) ']")
                .inField("Температурный режим на всю партию (от -30°C до 0°C или от 0°C до +30°C) ").setCheckboxON().assertNoControl()
                .inField("От").inputValue(P.getProperty("Информация о грузе.От")).assertNoControl().assertValue()
                .inField("До").inputValue(P.getProperty("Информация о грузе.До")).assertNoControl().assertValue()
                .clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .inField("Наименование продукции").selectValue(P.getProperty("Информация о грузе.Наименование продукции")).assertNoControl().assertValue()
                .inField("Код ТН ВЭД").assertValue(P.getProperty("Информация о грузе.Код ТН ВЭД")).assertNoControl().assertUneditable()
                .inField("Вес продукции, кг").inputValue(P.getProperty("Информация о грузе.Вес продукции, кг")).assertNoControl().assertValue()
                .inField("Упаковка").selectValue(P.getProperty("Информация о грузе.Упаковка")).assertNoControl().assertValue()
                .inField("Длина, см").inputValue(P.getProperty("Информация о грузе.Длина, см")).assertValue().assertNoControl().assertValue()
                .inField("Ширина, см").inputValue(P.getProperty("Информация о грузе.Ширина, см")).assertValue().assertNoControl().assertValue()
                .inField("Высота, см").inputValue(P.getProperty("Информация о грузе.Высота, см")).assertValue().assertNoControl().assertValue()
                .inField("Количество грузовых мест, шт").inputValue(P.getProperty("Информация о грузе.Количество грузовых мест, шт")).assertValue().assertNoControl().assertValue()
                .clickButton("Сохранить")
                .inContainer("Информация о грузе").waitForElementDisplayed("//td[text() = '1']" +
                "/following-sibling::td[text() = '" + P.getProperty("Информация о грузе.Наименование продукции") + "']" +
                "/following-sibling::td[text() = '" + P.getProperty("Информация о грузе.Количество грузовых мест, шт") + "']" +
                "/following-sibling::td[text() = '" + P.getProperty("Информация о грузе.Вес продукции, кг").split(",")[0] + "']" +
                "/following-sibling::td");
        new GUIFunctions().inContainer("Информация о грузе").clickButton("Добавить +")
                .inContainer("Сведения о продукции")
                .clickButton("Добавить новую")
                .inField("Наименование продукции").inputValue(P.getProperty("Информация о грузе.Наименование продукции.2")).assertNoControl().assertValue()
                .inField("Код ТН ВЭД").selectValue(P.getProperty("Информация о грузе.Код ТН ВЭД.2")).assertNoControl().assertValue()
                .inField("Вес продукции, кг").inputValue(P.getProperty("Информация о грузе.Вес продукции, кг.2")).assertNoControl().assertValue()
                .inField("Упаковка").selectValue(P.getProperty("Информация о грузе.Упаковка.2")).assertNoControl().assertValue()
                .inField("Длина, см").inputValue(P.getProperty("Информация о грузе.Длина, см.2")).assertValue().assertNoControl()
                .inField("Ширина, см").inputValue(P.getProperty("Информация о грузе.Ширина, см.2")).assertValue().assertNoControl()
                .inField("Высота, см").inputValue(P.getProperty("Информация о грузе.Высота, см.2")).assertValue().assertNoControl()
                .inField("Количество грузовых мест, шт").inputValue(P.getProperty("Информация о грузе.Количество грузовых мест, шт.2"))
                .assertValue().assertNoControl()
                .clickButton("Сохранить")
                .inContainer("Информация о грузе").waitForElementDisplayed("//td[text() = '2']" +
                "/following-sibling::td[text() = '" + P.getProperty("Информация о грузе.Наименование продукции.2") + "']" +
                "/following-sibling::td[text() = '" + P.getProperty("Информация о грузе.Количество грузовых мест, шт.2") + "']" +
                "/following-sibling::td[text() = '" + P.getProperty("Информация о грузе.Вес продукции, кг.2").split(",")[0] + "']" +
                "/following-sibling::td");
        new GUIFunctions().inContainer("Информация о грузе").clickByLocator("//td[text() = '2']/following-sibling::td//button[@class = 'dropdown-icon']");
        new GUIFunctions().clickByLocator("//td[text() = '2']/following-sibling::td//*[text() = ' Удалить' ]")
                //.clickButton("")
                .waitForElementDisappeared("//td[text() = '2']" +
                        "/following-sibling::td[text() = '" + P.getProperty("Информация о грузе.Наименование продукции.2") + "']" +
                        "/following-sibling::td[text() = '" + P.getProperty("Информация о грузе.Количество грузовых мест, ш.2") + "']" +
                        "/following-sibling::td[text() = '" + P.getProperty("Информация о грузе.Вес продукции, кг.2") + "']" +
                        "/following-sibling::td");
    }

    @Step("Блок \"Договор на установление карантинного фитосанитарного состояния\"")
    public void step07() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Информация о грузополучателе")
                .inField("Наименование грузополучателя").inputValue(P.getProperty("Информация о грузополучателе.Наименование грузополучателя")).assertNoControl()
                .inField("Страна").inputValue(P.getProperty("Информация о грузополучателе.Страна")).assertNoControl().assertValue()
                .inField("Город").inputValue(P.getProperty("Информация о грузополучателе.Город")).assertNoControl().assertValue()
                .inField("Дом").inputValue(P.getProperty("Информация о грузополучателе.Дом")).assertNoControl().assertValue()
                .inField("Регион").inputValue(P.getProperty("Информация о грузополучателе.Регион")).assertNoControl().assertValue()
                .inField("Район").inputValue(P.getProperty("Информация о грузополучателе.Район")).assertNoControl().assertValue()
                .inField("Улица").inputValue(P.getProperty("Информация о грузополучателе.Улица")).assertNoControl().assertValue()
                .inField("Регистрационный номер грузополучателя").inputValue(P.getProperty("Информация о грузополучателе.Регистрационный номер грузополучателя")).assertNoControl().assertValue()
                .inField("Телефон").inputValue(P.getProperty("Информация о грузополучателе.Телефон")).assertNoControl().assertValue()
                .inField("Представитель грузополучателя").inputValue(P.getProperty("Информация о грузополучателе.Представитель грузополучателя")).assertNoControl().assertValue()
                .inField("Email").inputValue(P.getProperty("Информация о грузополучателе.Email")).assertNoControl().assertValue();
    }

    @Step("Блок \"Запрос отбора проб\"")
    public void step08() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Дополнительные услуги")
                .inField("Вывоз груза с адреса («Первая миля»)").setCheckboxON().assertNoControl()
                .inField("Таможенное оформление").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Комплексная услуга таможенного оформления")
                .inField("Оформление ветеринарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении ветеринарных сертификатов")
                .inField("Оформление фитосанитарного сертификата").setCheckboxON().assertNoControl()
                .inField("РЖД Логистика").setRadiobuttonByDescription("Содействие в получении фитосанитарных сертификатов");
    }

    @Step("Экран ознакомления с результатом проверки. Блок \"Форма заключения\"")
    public void step09() {
        CommonFunctions.printStep();
        new GUIFunctions().clickButton("Далее").waitForLoading();
        docNum = $x("//div[contains (@class, 'FormHeader_title' )]//span[contains (@class, 'Typography_body' )]").getText().split("№")[1];
        JupyterLabIntegration.uploadTextContent(docNum, WAY_TEST, "docNum.txt");
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST, "processID.txt");
    }

    @Step("Экран ознакомления с результатом проверки. Блок \"Уполномоченное лицо для получения заключения\"")
    public void step10() {
        CommonFunctions.printStep();
        CommonFunctions.wait(20);
        String status = RESTFunctions.getOrderStatus(processID);
        System.out.println(status);
        Assert.assertEquals(status, "Проводится проверка");
    }

    public void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            if ($x(expectedXpath).isDisplayed()) {
                break;
            }
            System.out.println("Refreshing");
            refresh();
            CommonFunctions.wait(1);
        }
    }

}
