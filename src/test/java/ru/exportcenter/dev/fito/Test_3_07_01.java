package ru.exportcenter.dev.fito;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.common.CommonFunctions;
import functions.common.DateFunctions;
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

public class Test_3_07_01 extends Hooks {

    public String WAY_TEST = Ways.TEST.getWay() + "/fito/Test_3_07_01/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_3_07_01_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
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
                .clickByLocator("//div[@data-history-code='/services/state'][normalize-space(text()='Государственные')]")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/state");

        //В строке "Поиск по разделу" ввести "ФИТО"
        new GUIFunctions()
                .waitForElementDisplayed("//div[@class='js-tabs__block open']//input[@placeholder='Поиск по разделу']")
                .inputInSearchField("Поиск по разделу", "ФИТО")
                .closeAllPopupWindows();

        //Выбрать сервис "Запрос заключения о карантинном фитосанитарном состоянии подкарантинной продукции", нажать "Оформить"
        new GUIFunctions()
                .openSearchResult("Запрос заключения о карантинном фитосанитарном состоянии", "Оформить")
                .switchPageTo(1)
                .waitForLoading();
    }

    @Step("Начальный экран")
    public void step03() {
        CommonFunctions.printStep();

        //сохраним processID в файл
        processID = CommonFunctions.getProcessIDFromURL();
        System.out.println("processID: " + processID);
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST, "processID.txt");

        //На "Начальном экране" формирования запроса нажать "Продолжить"
        refreshTab("//*[contains(text(), 'Продолжить')]", 60);
        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Блок \"Заявитель/Получатель\"")
    public void step04() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 1 из 9']")

                .inContainer("Получатель")
                    .inField("Наименование получателя (на английском языке)").inputValue(P.getProperty("Получатель.Наименование получателя")).assertNoControl().assertValue()
                    .inField("Страна").selectValue(P.getProperty("Получатель.Страна")).assertNoControl().assertValue()
                    .inField("Юридический адрес на английском языке в формате: номер дома, улица, город, индекс").inputValue(P.getProperty("Получатель.Юридический адрес")).assertNoControl().assertValue()

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading();
    }

    @Step("Блок  \"Условия поставки\"")
    public void step05() {
        CommonFunctions.printStep();

        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 2 из 9']")

                .inContainer("Условия поставки")
                    .inField("Вид транспорта").selectValue(P.getProperty("Условия поставки.Вид транспорта")).assertNoControl().assertValue()
                    .inField("Страна назначения").selectValue(P.getProperty("Условия поставки.Страна назначения")).assertNoControl().assertValue()
                    .inField("Пункт ввоза в стране назначения").inputValue(P.getProperty("Условия поставки.Пункт ввоза в стране назначения")).assertNoControl().assertValue()

                    .inField("Тип документа о происхождении груза. Если тип документа в списке отсутствует — выберите «другое»").selectValue(P.getProperty("Условия поставки.Тип документа о происхождении груза. Если тип документа в списке отсутствует — выберите «другое»")).assertNoControl().assertValue()
                    .inField("Номер документа на груз").inputValue(P.getProperty("Условия поставки.Номер документа на груз")).assertNoControl().assertValue()
                    .inField("Дата").inputValue(DateFunctions.dateToday("dd.MM.yyyy")).assertNoControl().assertValue()

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading();
    }

    @Step("Блок \"Добавление продукции\"")
    public void step06() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 3 из 9']")

                .inContainer("Добавление продукции")
//                    .inField("Каталог продукции").selectValue(" --- кофе").assertNoControl().assertValue()
                    .inField("Каталог продукции").clickByLocator("//input[@placeholder='Введите наименование продукции или код ТН ВЭД']").inputValue("кофе").clickByLocator("//*[contains(text(), ' --- кофе')]")
                    .inField("Тип продукции").selectValue(P.getProperty("Добавление продукции.Тип продукции")).assertNoControl().assertValue()
                    .inField("Дополнительная информация о продукции. Например, страна производства (произрастания) продукции, сорт продукции и т.д.").inputValue(P.getProperty("Добавление продукции.Дополнительная информация о продукции")).assertNoControl().assertValue()
                    .inField("Вес груза (нетто), кг").inputValue(P.getProperty("Добавление продукции.Вес груза (нетто)")).assertNoControl().assertValue()
                    .inField("Особые единицы измерения").selectValue(P.getProperty("Добавление продукции.Особые единицы измерения")).assertNoControl().assertValue()
                    .inField("Количество в особых единицах измерения").inputValue(P.getProperty("Добавление продукции.Количество в особых единицах измерения")).assertNoControl().assertValue()
                    .inField("Описание упаковки").selectValue(P.getProperty("Добавление продукции.Описание упаковки")).assertNoControl().assertValue()
                    .inField("Размещение продукции").clickByLocator("//ancestor::div//span[contains(text(),'Навалом (наливом)')][last()]")
                    .inField("Наличие отличительных знаков (маркировки). Например, номера партий, серийные номера или названия торговых марок. ").setCheckboxON().assertCheckboxON()
                    .inField("Страна").selectValue(P.getProperty("Добавление продукции.Страна")).assertNoControl().assertValue()
                    .inField("Регион").selectValue(P.getProperty("Добавление продукции.Регион")).assertNoControl().assertValue()

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading();
    }

    @Step("Блок \"Договор на установление карантинного фитосанитарного состояния\"")
    public void step07() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 4 из 9']")

                .inContainer("Договор на установление карантинного  фитосанитарного состояния")
                    .inField("Договор").selectValue(P.getProperty("Договор.Договор")).assertNoControl().assertValue()
                    .inField("Орган инспекции").assertValue(P.getProperty("Договор.Орган инспекции")).assertNoControl()
                    .inField("Номер").assertValue(P.getProperty("Договор.Номер")).assertNoControl()
                    .inField("Дата").assertValue(P.getProperty("Договор.Дата")).assertNoControl()
                    .inField("Срок действия").assertValue(P.getProperty("Договор.Срок действия")).assertNoControl()

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading();
    }

    @Step("Блок \"Запрос отбора проб\"")
    public void step08() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 5 из 9']")

                .inContainer("Запрос отбора проб")
                    .inField("Территориальное управление Россельхознадзора").selectValue(P.getProperty("Запрос отбора проб.Территориальное управление Россельхознадзора")).assertNoControl().assertValue()
                    .inField("Планируемая дата отбора проб").inputValue(DateFunctions.dateToday("dd.MM.yyyy")).assertNoControl().assertValue()
                    .inField("Планируемое время отбора проб").inputValue(P.getProperty("Запрос отбора проб.Планируемое время отбора проб")).assertNoControl().assertValue()
                    .inField("Адрес места отбора проб").inputValue(P.getProperty("Запрос отбора проб.Адрес места отбора проб")).assertNoControl().assertValue()
                    .inField("Дополнительные требования к исследованиям ").inputValue(P.getProperty("Запрос отбора проб.Дополнительные требования к исследованиям")).assertNoControl().assertValue()

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Направить на проверку")
                    .waitForLoading();
    }

    @Step("Экран ознакомления с результатом проверки. Блок \"Форма заключения\"")
    public void step09() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 7 из 9']")

                .inContainer("Форма заключения")
                    .inField("Выберите требуемую форму заключения о карантинном фитосанитарном состоянии:").clickByLocator("//ancestor::div//span[contains(text(),'В электронной форме')][last()]");
    }

    @Step("Экран ознакомления с результатом проверки. Блок \"Уполномоченное лицо для получения заключения\"")
    public void step10() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 7 из 9']")

//                .inContainer("Уполномоченное лицо для получения заключения")
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
//                    .inField("ФИО").selectValue(P.getProperty("Форма заключения.ФИО"))
                    .inField("ФИО").selectValue("Фамилия Дополнительного контакта").assertNoControl().assertValue()
                    .inField("Телефон").assertValue(P.getProperty("Форма заключения.Телефон")).assertNoControl()
                    .inField("Email").assertValue(P.getProperty("Форма заключения.Email")).assertNoControl()

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading();
    }

    @Step("Экран ознакомления с результатом проверки. Блок \"Уполномоченное лицо для получения заключения\"")
    public void step11() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .waitForElementDisplayed("//div[text()='Шаг 8 из 9']")

                .inContainer("Договор с органом инспекции")
                   .inField("Я ознакомлен и согласен с условиями проекта договора").setCheckboxON().assertCheckboxON()

                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Подписать и отправить")
                    .waitForLoading();

//        docNum = $x("//div[contains (@class, 'FormHeader_title' )]//span[contains (@class, 'Typography_body' )]").getText().split("№")[1];
//        JupyterLabIntegration.uploadTextContent(docNum, WAY_TEST, "docNum.txt");
//        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST, "processID.txt");
//
//        CommonFunctions.wait(20);
//        String status = RESTFunctions.getOrderStatus(processID);
//        System.out.println(status);
//        Assert.assertEquals(status, "Проводится проверка");
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
