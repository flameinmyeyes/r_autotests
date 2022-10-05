package ru.exportcenter.dev.fito;

import framework.RunTestAgain;
import framework.Ways;
import framework.integration.JupyterLabIntegration;
import functions.api.RESTFunctions;
import functions.common.CommonFunctions;
import functions.common.DateFunctions;
import functions.file.FileFunctions;
import functions.file.PropertiesHandler;
import functions.file.XMLHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;

import java.io.File;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_3_07_01 extends Hooks {

    public String WAY_TEST = Ways.DEV.getWay() + "/fito/Test_3_07_01/";
    public String WAY_TEMP_FILE = "src/test/java/ru/exportcenter/dev/fito/";
    public String WAY_TO_PROPERTIES = Ways.DEV.getWay() + "/fito/Test_3_07_01/" + "Test_3_07_01_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String processID;
    private String docNum;
    private String token;
    private String baseURI = "http://bpmn-api-service.bpms-dev.d.exportcenter.ru/";
    private String guid;

    @Owner(value = "Балашов Илья")
    @Description("3.07.01 Сценарий получения услуги по ЗКФС (положительный результат)")
    @Link(name = "Test_3_07_01", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=183183514")
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
        step11();
        step12();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Шаг 1. Авторизация")
    public void step01() {
        CommonFunctions.printStep();
        //В браузере перейти по ссылке
        open(P.getProperty("Авторизация.URL"));

        //Ввести логин и пароль demo_exporter/password
        new GUIFunctions()
                .authorization(P.getProperty("Авторизация.Email"), P.getProperty("Авторизация.Пароль"))
                .waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");
    }

    @Step("Шаг 2. Выбор сервиса")
    public void step02() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .clickButton("Заказать услугу")
                .switchPageTo(1)
                .clickByLocator("//div[@data-history-code='/services/state'][normalize-space(text()='Государственные')]")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/state")

                .waitForElementDisplayed("//div[@class='js-tabs__block open']//input[@placeholder='Поиск по разделу']")
                .inputInSearchField("Поиск по разделу", "ФИТО")
                .closeAllPopupWindows()

                .openSearchResult("Запрос заключения о карантинном фитосанитарном состоянии", "Оформить")
                .switchPageTo(2)
                .waitForLoading();
    }

    @Step("Шаг 3. Начальный экран")
    public void step03() {
        CommonFunctions.printStep();
        //сохранить processID в файл
        processID = CommonFunctions.getProcessIDFromURL();
        System.out.println("processID: " + processID);
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST, "processID.txt");

        new GUIFunctions()
                .waitForLoading()
                .waitForElementDisplayed("//*[contains(text(),'Запрос заключения о карантинном фитосанитарном состоянии')]")
                .closeAllPopupWindows();

        //костыль
        if ($x("//button[contains(text(),'Запрос заключения о карантинном фитосанитарном состоянии')]").isDisplayed()){
            new GUIFunctions().clickByLocator("//button[contains(text(),'Запрос заключения о карантинном фитосанитарном состоянии')]");
            webdriver().driver().switchTo().alert().accept();
        }

        //сохранить номер документа в файл
        docNum = $x("//div[text()='Номер заявки']/parent::div/div[contains(@class, 'description')]").getText();
        System.out.println("docNum: " + docNum);
        JupyterLabIntegration.uploadTextContent(docNum, WAY_TEST, "docNum.txt");

        //На "Начальном экране" формирования запроса нажать "Продолжить"
        refreshTab("//*[contains(text(), 'Продолжить')]", 60);
        new GUIFunctions()
                .clickButton("Продолжить")
                .waitForElementDisplayed("//div[text()='Шаг 1 из 9']");
    }

    @Step("Шаг 4. Блок \"Заявитель/Получатель\"")
    public void step04() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Получатель")
                    .inField("Наименование получателя (на английском языке)").inputValue(P.getProperty("Получатель.Наименование получателя")).assertNoControl().assertValue()
                    .inField("Страна").selectValue(P.getProperty("Получатель.Страна")).assertNoControl().assertValue()
                    .inField("Юридический адрес на английском языке в формате: номер дома, улица, город, индекс").inputValue(P.getProperty("Получатель.Юридический адрес")).assertNoControl().assertValue()

                //нажать "Продолжить"
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading()
                    .waitForElementDisplayed("//div[text()='Шаг 2 из 9']");
    }

    @Step("Шаг 5. Блок  \"Условия поставки\"")
    public void step05() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Условия поставки")
                    //Условия транспортировки
                    .inField("Вид транспорта").selectValue(P.getProperty("Условия поставки.Вид транспорта")).assertNoControl().assertValue()
                    .inField("Страна назначения").selectValue(P.getProperty("Условия поставки.Страна назначения")).assertNoControl().assertValue()
                    .inField("Пункт ввоза в стране назначения").inputValue(P.getProperty("Условия поставки.Пункт ввоза в стране назначения")).assertNoControl().assertValue()
                    //Документы на груз
                    .inField("Тип документа о происхождении груза. Если тип документа в списке отсутствует — выберите «другое»").selectValue(P.getProperty("Условия поставки.Тип документа о происхождении груза. Если тип документа в списке отсутствует — выберите «другое»")).assertNoControl().assertValue()
                    .inField("Номер документа на груз").inputValue(P.getProperty("Условия поставки.Номер документа на груз")).assertNoControl().assertValue()
                    .inField("Дата").inputValue(DateFunctions.dateToday("dd.MM.yyyy")).assertNoControl().assertValue()

                //нажать "Продолжить"
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading()
                    .waitForElementDisplayed("//div[text()='Шаг 3 из 9']");
    }

    @Step("Шаг 6. Редактирование XML файла ответа при получении сведений из реестра договоров с аккредитованными организациями (ВС 1)")
    public void step06() {
        CommonFunctions.printStep();
        //читаем содержимое XML с файла на юпитере
        String fileContent = JupyterLabIntegration.getFileContent(WAY_TEST + "ResponseSuccess.xml");

        //создаем временный XML файл и записываем туда содержимое XML
        String wayFile = WAY_TEMP_FILE + "ResponseSuccess.xml";
        deleteFileIfExists(new File(wayFile)); //удаляем временный файл, если он есть
        FileFunctions.writeValueToFile(wayFile, fileContent);

        //обновляем XML файл
        guid = CommonFunctions.generateUUID();
        XMLHandler.updateXML(wayFile, "common:GUID", guid);
        XMLHandler.updateXML(wayFile, "common:SendDateTime", DateFunctions.dateToday("yyyy-MM-dd'T'HH:mm:ss")); //2022-10-04T12:49:27
    }

    @Step("Шаг 7. Загрузка XML файла через сваггер, запуск процесса (использовать значения для ВС 1)")
    public void step07() {
        CommonFunctions.printStep();

        token = RESTFunctions.getAccessToken("http://uidm.uidm-dev.d.exportcenter.ru", "bpmn_admin");
        System.out.println("token: " + token);

        String wayFile = WAY_TEMP_FILE + "ResponseSuccess.xml";
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "AccOrgContrRequestMessage";

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, baseURI, processID, new File(wayFile), messageName);

        deleteFileIfExists(new File(wayFile)); //удаляем временный файл
    }

    @Step("Шаг 8. Блок \"Добавление продукции\"")
    public void step08() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Добавление продукции")
                    .inField("Каталог продукции").selectValue(P.getProperty("Добавление продукции.Каталог продукции").split(" ")[0]).assertNoControl().assertValue(P.getProperty("Добавление продукции.Каталог продукции"))
                    .inField("Код ТН ВЭД").assertValue(P.getProperty("Добавление продукции.Код ТН ВЭД"))
                    .inField("Тип продукции").selectValue(P.getProperty("Добавление продукции.Тип продукции")).assertNoControl().assertValue()
                    .inField("Дополнительная информация о продукции. Например, страна производства (произрастания) продукции, сорт продукции и т.д.").inputValue(P.getProperty("Добавление продукции.Дополнительная информация о продукции")).assertNoControl().assertValue()
                    .inField("Вес груза (нетто), кг").inputValue(P.getProperty("Добавление продукции.Вес груза (нетто)")).assertNoControl().assertValue()
                    .inField("Особые единицы измерения").selectValue(P.getProperty("Добавление продукции.Особые единицы измерения")).assertNoControl().assertValue()
                    .inField("Количество в особых единицах измерения").inputValue(P.getProperty("Добавление продукции.Количество в особых единицах измерения")).assertNoControl().assertValue()
                    .inField("Описание упаковки").selectValue(P.getProperty("Добавление продукции.Описание упаковки")).assertNoControl().assertValue()
                    .inField("Размещение продукции").clickByLocator("//ancestor::div//span[contains(text(),'Навалом (наливом)')][last()]")
                    .inField("Наличие отличительных знаков (маркировки). Например, номера партий, серийные номера или названия торговых марок. ").setCheckboxON().assertCheckboxON()
                    .inField("Номер партии зерна (продуктов переработки зерна)").inputValue(P.getProperty("Добавление продукции.Номер партии зерна")).assertNoControl().assertValue()
                    //Место происхождения( произрастания) продукции
                    .inField("Страна").selectValue(P.getProperty("Добавление продукции.Страна")).assertNoControl().assertValue()
                    .inField("Регион").selectValue(P.getProperty("Добавление продукции.Регион")).assertNoControl().assertValue()

                //нажать "Продолжить"
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading()
                    .waitForElementDisplayed("//div[text()='Шаг 4 из 9']");
    }

    @Step("Шаг 9. Блок \"Договор на установление карантинного фитосанитарного состояния\"")
    public void step09() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Договор на установление карантинного  фитосанитарного состояния")
                    .inField("Договор").selectValue(P.getProperty("Договор.Договор")).assertNoControl().assertValue()
                    .inField("Орган инспекции").assertValue(P.getProperty("Договор.Орган инспекции")).assertNoControl()
                    .inField("Номер").assertValue(P.getProperty("Договор.Номер")).assertNoControl()
                    .inField("Дата").assertValue(P.getProperty("Договор.Дата")).assertNoControl()
                    .inField("Срок действия").assertValue(P.getProperty("Договор.Срок действия")).assertNoControl()

                //нажать "Продолжить"
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading()
                    .waitForElementDisplayed("//div[text()='Шаг 5 из 9']");
    }

    @Step("Шаг 10. Блок \"Запрос отбора проб\"")
    public void step10() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Запрос отбора проб")
                    .inField("Территориальное управление Россельхознадзора").selectValue(P.getProperty("Запрос отбора проб.Территориальное управление Россельхознадзора")).assertNoControl().assertValue()
                    .inField("Планируемая дата отбора проб").inputValue(DateFunctions.dateToday("dd.MM.yyyy")).assertNoControl().assertValue()
                    .inField("Планируемое время отбора проб").inputValue(P.getProperty("Запрос отбора проб.Планируемое время отбора проб")).assertNoControl().assertValue()
                    .inField("Адрес места отбора проб").inputValue(P.getProperty("Запрос отбора проб.Адрес места отбора проб")).assertNoControl().assertValue()
                    .inField("Дополнительные требования к исследованиям ").inputValue(P.getProperty("Запрос отбора проб.Дополнительные требования к исследованиям")).assertNoControl().assertValue()

                //нажать "Продолжить"
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Направить на проверку")
                    .waitForLoading()
                    .waitForElementDisplayed("//div[text()='Шаг 6 из 9']");
    }

    @Step("Шаг 11. Редактирование XML ответа проверки сведений из проекта заявления в Россельхознадзоре (ВС 2)")
    public void step11() {
        CommonFunctions.printStep();
        //читаем содержимое XML с файла на юпитере
        String fileContent = JupyterLabIntegration.getFileContent(WAY_TEST + "ResponseSuccessBC2.xml");

        //создаем временный XML файл и записываем туда содержимое XML
        String wayFile = WAY_TEMP_FILE + "ResponseSuccessBC2.xml";
        deleteFileIfExists(new File(wayFile)); //удаляем временный файл, если он есть
        FileFunctions.writeValueToFile(wayFile, fileContent);

        //обновляем XML файл
        XMLHandler.updateXML(wayFile, "common:GUID", guid);
        XMLHandler.updateXML(wayFile, "common:SendDateTime", DateFunctions.dateToday("yyyy-MM-dd'T'HH:mm:ss")); //2022-10-04T12:49:27
    }

    @Step("Шаг 12. Загрузка XML файла через сваггер, запуск процесса")
    public void step12() {
        CommonFunctions.printStep();

        String wayFile = WAY_TEMP_FILE + "ResponseSuccessBC2.xml";
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "CheckAppInfRequestMessage";

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, baseURI, processID, new File(wayFile), messageName);

        deleteFileIfExists(new File(wayFile)); //удаляем временный файл
    }

    @Step("Шаг 13. Получение результата проверки сведений")
    public void step13() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Форма заключения")
                    .inField("Выберите требуемую форму заключения о карантинном фитосанитарном состоянии:").clickByLocator("//ancestor::div//span[contains(text(),'В электронной форме')][last()]");
    }

    @Step("Экран ознакомления с результатом проверки. Блок \"Уполномоченное лицо для получения заключения\"")
    public void step14() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Уполномоченное лицо для получения заключения")
                    .inField("ФИО").clickByLocator("//input[@name='authorizedPersonFullName'][@placeholder='Не выбрано']").inputValue("Грибоедов").waitForLoading().clickByLocator("//*[contains(text(), 'Грибоедов Гриб Грибович')]")
                    .inField("Телефон").assertValue(P.getProperty("Форма заключения.Телефон")).assertNoControl()
                    .inField("Email").assertValue(P.getProperty("Форма заключения.Email")).assertNoControl()

                //нажать "Продолжить"
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Продолжить")
                    .waitForLoading()
                    .waitForElementDisplayed("//div[text()='Шаг 8 из 9']");
    }

    @Step("Шаг 11. Экран \"Шаг 8 из 9\"")
    public void step15() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Подписать и отправить")
                    .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022").assertValue()
                    .clickButton("Подписать")
                    .waitForLoading()
                    .waitForElementDisplayed("//div[text()='Шаг 9 из 9']");
    }

    @Step("Шаг 12. Экран \"Результат предоставления услуги\"")
    public void step16() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Оформить сертификат");
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

    private static void deleteFileIfExists(File file) {
        if(file.exists()) {
            System.out.print("Временный файл обнаружен");
            file.delete();
            System.out.println(" и успешно удален");
        } else {
            System.out.println("Временный файл не обнаружен, удаление не требуется");
        }
    }

}
