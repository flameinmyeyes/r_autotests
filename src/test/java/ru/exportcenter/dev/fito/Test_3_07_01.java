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
    private final String WAY_FILES = Ways.DEV.getWay() + "/fito/Test_3_07_01/"; //переменная нужна, т.к. значение WAY_TEST будет меняться при вызове текущего теста из других тестов
    private final String WAY_TEMP_FILE = "src/test/java/ru/exportcenter/dev/fito/";
    private final String FILE_NAME_BC_1 = "ResponseSuccess1_new.xml";
    private final String FILE_NAME_BC_2 = "1ResponseSuccessBC2.xml";
    private final String FILE_NAME_BC_3_1 = "1ResponseSuccessBC3_1.xml";
    private final String FILE_NAME_BC_3_2 = "1ResponseSuccessBC3_2.xml";
    private final String FILE_NAME_BC_3_3 = "1ResponseSuccessBC3_3.xml";
    private final String FILE_NAME_BC_3_4 = "1ResponseSuccessBC3_4.xml";

    public String WAY_TO_PROPERTIES = Ways.DEV.getWay() + "/fito/Test_3_07_01/" + "Test_3_07_01_properties.xml";
    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    private String token;

    private String BASE_URI = "http://bpmn-api-service.bpms-dev.d.exportcenter.ru";
    private String processID;

    private String docNum;
    private String guid;
    private String zayavlenieRegistrationNumber;
    private String aktNumber;
    private String zKFSNumber;

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
        step13();
        step14();
        step15();
        step16();
        step17();
        step18();
        step19();
        step20();
        step21();
        step22();
        step23();
        step24();
        step25();
        step26();
        step27();
        step28();
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
                .waitForURL(P.getProperty("Авторизация.URL") + "/ru/main");
    }

    @Step("Шаг 2. Выбор сервиса")
    public void step02() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .clickButton("Заказать услугу")
                .switchPageTo(1)
                .waitForLoading()
                .clickByLocator("//div[@data-history-code='/services/state'][normalize-space(text()='Государственные')]")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/state")

                .waitForElementDisplayed("//div[@class='js-tabs__block open']//input[@placeholder='Поиск по разделу']")
                .inputInSearchField("Поиск по разделу", "ФИТО")
                .closeAllPopupWindows()

                .openSearchResult("Запрос заключения о карантинном фитосанитарном состоянии подкарантинной продукции", "Оформить")
                .switchPageTo(2)
                .waitForLoading()
//                .waitForElementDisplayed("//*[contains(text(),'Запрос заключения о карантинном фитосанитарном состоянии')]"); //если страница с номером заявки проскакивает
                .waitForElementDisplayed("//div[text()='Номер заявки']/parent::div/div[contains(@class, 'description')]");
    }

    @Step("Шаг 3. Начальный экран")
    public void step03() {
        CommonFunctions.printStep();

        //если страница с номером заявки проскакивает
//        clickRequestLinkIfRequestNumberPageIsSkipped("Запрос заключения о карантинном фитосанитарном состоянии");

        //сохранить processID в файл
        processID = CommonFunctions.getProcessIDFromURL();
        System.out.println("processID: " + processID);
        JupyterLabIntegration.uploadTextContent(processID, WAY_TEST, "processID.txt");

        //сохранить номер документа в файл
        docNum = $x("//div[text()='Номер заявки']/parent::div/div[contains(@class, 'description')]").getText();
        System.out.println("docNum: " + docNum);
        JupyterLabIntegration.uploadTextContent(docNum, WAY_TEST, "docNum.txt");

        //На "Начальном экране" формирования запроса нажать "Продолжить"
        refreshTabUntilElementIsDisplayed("//*[contains(text(), 'Продолжить')]", 60);
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
        String fileContent = JupyterLabIntegration.getFileContent(WAY_FILES + FILE_NAME_BC_1);

        //создаем временный XML файл и записываем туда содержимое XML
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_1;
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
        token = RESTFunctions.getAccessToken(P.getProperty("Авторизация.URL"), "bpmn_admin");
        System.out.println("token: " + token);

        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_1;
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "AccOrgContrRequestMessage";

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, BASE_URI, processID, new File(wayFile), messageName);

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
                    .inField("Договор").selectValue(P.getProperty("Договор.Договор")).waitForLoading().assertNoControl().assertValue()
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
                    .inField("Планируемая дата отбора проб").inputValue(DateFunctions.dateShift("dd.MM.yyyy", +1)).assertNoControl().assertValue()
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
        String fileContent = JupyterLabIntegration.getFileContent(WAY_FILES + FILE_NAME_BC_2);

        //создаем временный XML файл и записываем туда содержимое XML
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_2;
        deleteFileIfExists(new File(wayFile)); //удаляем временный файл, если он есть
        FileFunctions.writeValueToFile(wayFile, fileContent);

        //обновляем XML файл
        XMLHandler.updateXML(wayFile, "common:GUID", guid);
        XMLHandler.updateXML(wayFile, "common:SendDateTime", DateFunctions.dateToday("yyyy-MM-dd'T'HH:mm:ss")); //2022-10-04T12:49:27
        XMLHandler.updateXML(wayFile, "common:ProbeDate", DateFunctions.dateShift("yyyy-MM-dd", +1)); //2022-10-04
        XMLHandler.updateXML(wayFile, "common:ProbeTime", DateFunctions.dateToday("yyyy-MM-dd'T'") + P.getProperty("Запрос отбора проб.Планируемое время отбора проб") + ":00"); //2022-10-04T14:00:00
    }

    @Step("Шаг 12. Загрузка XML файла через сваггер, запуск процесса (использовать значения для ВС 2)")
    public void step12() {
        CommonFunctions.printStep();
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_2;
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "CheckAppInfRequestMessage";

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, BASE_URI, processID, new File(wayFile), messageName);

        deleteFileIfExists(new File(wayFile)); //удаляем временный файл
    }

    @Step("Шаг 13. Получение результата проверки сведений")
    public void step13() {
        CommonFunctions.printStep();
        //Нажать на ссылку с номером заявки
        String url = $x("//a[contains(text(), '" + docNum + "')]").getAttribute("href");
        System.out.println("url: " + url);
        open(url);
        webdriver().driver().switchTo().alert().accept();

        new GUIFunctions()
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Номер заявки']/parent::div/div[text()='" + docNum + "']");

        //На "Начальном экране" формирования запроса нажать "Продолжить"
        refreshTabUntilElementIsDisplayed("//*[contains(text(), 'Продолжить')]", 60);
        new GUIFunctions()
                .clickButton("Продолжить")
                .waitForElementDisplayed("//div[text()='Шаг 7 из 9']");
    }

    @Step("Шаг 14. Экран ознакомления с результатом проверки. Блок \"Форма заключения\"")
    public void step14() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Форма заключения")
                    .inField("Выберите требуемую форму заключения о карантинном фитосанитарном состоянии:").clickByLocator("//ancestor::div//span[contains(text(),'В электронной форме')][last()]");
    }

    @Step("Шаг 15. Экран ознакомления с результатом проверки. Блок \"Уполномоченное лицо для получения заключения\"")
    public void step15() {
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

    @Step("Шаг 16. Экран \"Шаг 8 из 9\"")
    public void step16() {
        CommonFunctions.printStep();
        new GUIFunctions()
                .inContainer("Запрос заключения о карантинном фитосанитарном состоянии")
                    .clickButton("Подписать и отправить")
                    .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022").assertValue()
                    .clickButton("Подписать")
                    .waitForLoading()
                    .waitForElementDisplayed("//div[text()='Шаг 9 из 9']");
    }

    @Step("Шаг 17. Редактирование XML ответа 1 для 3 ВС")
    public void step17() {
        CommonFunctions.printStep();
        //читаем содержимое XML с файла на юпитере
        String fileContent = JupyterLabIntegration.getFileContent(WAY_FILES + FILE_NAME_BC_3_1);

        //создаем временный XML файл и записываем туда содержимое XML
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_3_1;
        deleteFileIfExists(new File(wayFile)); //удаляем временный файл, если он есть
        FileFunctions.writeValueToFile(wayFile, fileContent);

        //генерируем и сохраняем номер заявления в файл
        zayavlenieRegistrationNumber = CommonFunctions.randomNumber(100, 999) + "-" + CommonFunctions.randomNumber(10, 99); //100-89
        System.out.println("zayavlenieRegistrationNumber: " + zayavlenieRegistrationNumber);
        JupyterLabIntegration.uploadTextContent(zayavlenieRegistrationNumber, WAY_TEST, "zayavlenieRegistrationNumber.txt");

        //обновляем XML файл
        XMLHandler.updateXML(wayFile, "common:GUID", guid);
        XMLHandler.updateXML(wayFile, "common:SendDateTime", DateFunctions.dateToday("yyyy-MM-dd'T'HH:mm:ss")); //2022-10-04T12:49:27
        XMLHandler.updateXML(wayFile, "common:ZayavlenieRegistrationDate", DateFunctions.dateToday("yyyy-MM-dd")); //2022-10-04
        XMLHandler.updateXML(wayFile, "common:ZayavlenieRegistrationNumber", zayavlenieRegistrationNumber);
    }

    @Step("Шаг 18. Загрузка XML файла через сваггер, запуск процесса (использовать значения для ВС 3)")
    public void step18() {
        CommonFunctions.printStep();
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_3_1;
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "SendAppInfRequestMessage";

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, BASE_URI, processID, new File(wayFile), messageName);

        deleteFileIfExists(new File(wayFile)); //удаляем временный файл
    }

    @Step("Шаг 19. Результат рассмотрения заявления")
    public void step19() {
        CommonFunctions.printStep();
        //Нажать на ссылку с номером заявки
        String url = $x("//a[contains(text(), '" + docNum + "')]").getAttribute("href");
        System.out.println("url: " + url);
        open(url);
        webdriver().driver().switchTo().alert().accept();

        new GUIFunctions()
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Номер заявки']/parent::div/div[text()='" + docNum + "']");

        //На "Начальном экране" формирования запроса нажать "Продолжить"
        refreshTabUntilElementIsDisplayed("//*[contains(text(), 'Продолжить')]", 60);
        new GUIFunctions()
                .clickButton("Продолжить")
                .waitForElementDisplayed("//div[text()='Шаг 9 из 9']");
    }

    @Step("Шаг 20. Редактирование XML ответа 2 для 3 ВС")
    public void step20() {
        CommonFunctions.printStep();
        //читаем содержимое XML с файла на юпитере
        String fileContent = JupyterLabIntegration.getFileContent(WAY_FILES + FILE_NAME_BC_3_2);

        //создаем временный XML файл и записываем туда содержимое XML
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_3_2;
        deleteFileIfExists(new File(wayFile)); //удаляем временный файл, если он есть
        FileFunctions.writeValueToFile(wayFile, fileContent);

        //генерируем и сохраняем номер акта в файл (не используется)
        aktNumber = CommonFunctions.randomNumber(100, 999) + "-" + CommonFunctions.randomNumber(100, 999);
        System.out.println("aktNumber: " + aktNumber);
        JupyterLabIntegration.uploadTextContent(aktNumber, WAY_TEST, "aktNumber.txt");

        //обновляем XML файл
        XMLHandler.updateXML(wayFile, "common:GUID", guid);
        XMLHandler.updateXML(wayFile, "common:SendDateTime", DateFunctions.dateToday("yyyy-MM-dd'T'HH:mm:ss")); //2022-10-04T12:49:27
        XMLHandler.updateXML(wayFile, "common:AktDate", DateFunctions.dateToday("yyyy-MM-dd")); //2022-10-04
        XMLHandler.updateXML(wayFile, "common:AktNumber", aktNumber); //100-100
    }

    @Step("Шаг 21. Загрузка XML файла через сваггер, запуск процесса (использовать значения для ВС 3)")
    public void step21() {
        CommonFunctions.printStep();
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_3_2;
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "SendAppInfRequestMessage";

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, BASE_URI, processID, new File(wayFile), messageName);

        deleteFileIfExists(new File(wayFile)); //удаляем временный файл
    }

    @Step("Шаг 22.  Ознакомление с информацией об отборе проб")
    public void step22() {
        CommonFunctions.printStep();
        //Кликнуть по номеру заявления из шага 17: тег <common:ZayavlenieRegistrationNumber> в XML
        String url = $x("//a[contains(text(), '" + zayavlenieRegistrationNumber + "')]").getAttribute("href");
        System.out.println("url: " + url);
        open(url);
        webdriver().driver().switchTo().alert().accept();

        new GUIFunctions()
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Номер заявки']/parent::div/div[text()='" + docNum + "']");

        //На "Начальном экране" формирования запроса нажать "Продолжить"
        refreshTabUntilElementIsDisplayed("//*[contains(text(), 'Продолжить')]", 60);
        new GUIFunctions()
                .clickButton("Продолжить")
                .waitForElementDisplayed("//div[text()='Шаг 9 из 9']");
    }

    @Step("Шаг 23. Редактирование XML ответа 3 для 3 ВС")
    public void step23() {
        CommonFunctions.printStep();
        //читаем содержимое XML с файла на юпитере
        String fileContent = JupyterLabIntegration.getFileContent(WAY_FILES + FILE_NAME_BC_3_3);

        //создаем временный XML файл и записываем туда содержимое XML
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_3_3;
        deleteFileIfExists(new File(wayFile)); //удаляем временный файл, если он есть
        FileFunctions.writeValueToFile(wayFile, fileContent);

        //обновляем XML файл
        XMLHandler.updateXML(wayFile, "common:GUID", guid);
        XMLHandler.updateXML(wayFile, "common:SendDateTime", DateFunctions.dateToday("yyyy-MM-dd'T'HH:mm:ss")); //2022-10-04T12:49:27
        XMLHandler.updateXML(wayFile, "common:ProbootborPlanDateEnd", DateFunctions.dateToday("yyyy-MM-dd")); //"2022-10-07
    }

    @Step("Шаг 24. Загрузка XML файла через сваггер, запуск процесса (использовать значения для ВС 3)")
    public void step24() {
        CommonFunctions.printStep();
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_3_3;
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "SendAppInfRequestMessage";

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, BASE_URI, processID, new File(wayFile), messageName);

        deleteFileIfExists(new File(wayFile)); //удаляем временный файл
    }

    @Step("Шаг 25.  Ознакомление с результатом отбора проб")
    public void step25() {
        CommonFunctions.printStep();
        //Нажать на ссылку с номером заявки
        String url = $x("//a[contains(text(), '" + zayavlenieRegistrationNumber + "')]").getAttribute("href");
        System.out.println("url: " + url);
        open(url);
        webdriver().driver().switchTo().alert().accept();

        new GUIFunctions()
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Номер заявки']/parent::div/div[text()='" + docNum + "']");

        //На "Начальном экране" формирования запроса нажать "Продолжить"
        refreshTabUntilElementIsDisplayed("//*[contains(text(), 'Продолжить')]", 60);
        new GUIFunctions()
                .clickButton("Продолжить")
                .waitForElementDisplayed("//div[text()='Шаг 9 из 9']");
    }

    @Step("Шаг 26. Редактирование XML ответа 4 для 3 ВС")
    public void step26() {
        CommonFunctions.printStep();
        //читаем содержимое XML с файла на юпитере
        String fileContent = JupyterLabIntegration.getFileContent(WAY_FILES + FILE_NAME_BC_3_4);

        //создаем временный XML файл и записываем туда содержимое XML
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_3_4;
        deleteFileIfExists(new File(wayFile)); //удаляем временный файл, если он есть
        FileFunctions.writeValueToFile(wayFile, fileContent);

        //генерируем и сохраняем номер ЗКФС в файл (не используется)
        zKFSNumber = CommonFunctions.randomNumber(10000, 99999); //12345
        System.out.println("zKFSNumber: " + zKFSNumber);
        JupyterLabIntegration.uploadTextContent(zKFSNumber, WAY_TEST, "zKFSNumber.txt");

        //обновляем XML файл
        XMLHandler.updateXML(wayFile, "common:GUID", guid);
        XMLHandler.updateXML(wayFile, "common:SendDateTime", DateFunctions.dateToday("yyyy-MM-dd'T'HH:mm:ss")); //2022-10-04T12:49:27
        XMLHandler.updateXML(wayFile, "common:ZKFSNumber", zKFSNumber);
        XMLHandler.updateXML(wayFile, "common:ZKFSDate", DateFunctions.dateToday("yyyy-MM-dd")); //2022-10-07
    }

    @Step("Шаг 27. Загрузка XML файла через сваггер, запуск процесса (использовать значения для ВС 3)")
    public void step27() {
        CommonFunctions.printStep();
        String wayFile = WAY_TEMP_FILE + FILE_NAME_BC_3_4;
        String fileContent = FileFunctions.readValueFromFile(wayFile);
        System.out.println("fileContent: " + fileContent);

        String messageName = "SendAppInfRequestMessage";

        //отправляем запрос
        RESTFunctions.sendAttachmentToProcess(token, BASE_URI, processID, new File(wayFile), messageName);

        deleteFileIfExists(new File(wayFile)); //удаляем временный файл
    }

    @Step("Шаг 28. Ознакомление с результатом предоставления услуги")
    public void step28() {
        CommonFunctions.printStep();
        //Нажать по ссылке  "Запрос заключения о карантинном фитосанитарном состоянии"
        new GUIFunctions().clickByLocator("//button[contains(text(),'Запрос заключения о карантинном фитосанитарном состоянии')]");
        webdriver().driver().switchTo().alert().accept();

        new GUIFunctions()
                .waitForLoading()
                .waitForElementDisplayed("//div[text()='Номер заявки']/parent::div/div[text()='" + docNum + "']");

        //На "Начальном экране" формирования запроса нажать "Продолжить"
        refreshTabUntilElementIsDisplayed("//*[contains(text(), 'Продолжить')]", 60);
        new GUIFunctions()
                .clickButton("Продолжить")
                .waitForElementDisplayed("//div[text()='Шаг 9 из 9']");
    }

//    public void refreshTab(String expectedXpath, int times) {
//        if (!$x(expectedXpath).isDisplayed()) {
//            System.out.println("Refreshing...");
//        }
//        for (int i = 0; i < times; i++) {
//            if ($x(expectedXpath).isDisplayed()) {
//                break;
//            }
//            refresh();
//            new GUIFunctions().waitForLoading();
//            CommonFunctions.wait(1);
//        }
//    }

    private static void deleteFileIfExists(File file) {
        if(file.exists()) {
            System.out.print("Временный файл обнаружен");
            file.delete();
            System.out.println(" и успешно удален");
        } else {
            System.out.println("Временный файл не обнаружен, удаление не требуется");
        }
    }

    /**
     * Костыль: обновлять страницу, пока не появится кнопка "Продолжить"
     */
    private static void refreshTabUntilElementIsDisplayed(String expectedXpath, int times) {
        if (!$x(expectedXpath).isDisplayed()) {
            System.out.println("Refreshing...");
        }
        for (int i = 0; i < times; i++) {
            if ($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            new GUIFunctions().waitForLoading();
            CommonFunctions.wait(1);
        }
    }

    /**
     * Костыль: если страница с номером заявки проскакивается - кликаем по ссылке с необходимым типом заявки
     */
    private static void clickRequestLinkIfRequestNumberPageIsSkipped(String requestName) {
        if ($x("//button[contains(text(),'" + requestName + "')]").isDisplayed()) {
            new GUIFunctions().clickByLocator("//button[contains(text(),'" + requestName + "')]");
        }
    }

}
