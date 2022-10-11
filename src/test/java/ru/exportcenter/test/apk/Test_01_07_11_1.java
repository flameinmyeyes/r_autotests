package ru.exportcenter.test.apk;

import framework.RunTestAgain;
import framework.Ways;
import functions.common.CommonFunctions;
import functions.common.DateFunctions;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.Hooks;
import java.awt.*;
import static com.codeborne.selenide.Selenide.*;

public class Test_01_07_11_1 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/apk/Test_01_07_11_1/";
//    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_01_07_11_1_properties.xml";
//    public Properties P = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);
    private String requestNumber;

    @Owner(value = "Петрищев Руслан")
    @Description("01 07 11.1 Заполнение Заявки на получение услуги, подписание Заявки УКЭП и автоматическая передача Заявки на верификацию")
    @Link(name = "Test_01_07_11_1", url = "https://confluence.exportcenter.ru/pages/viewpage.action?pageId=163299538")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException, InterruptedException {
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
    public void precondition() throws AWTException, InterruptedException  {
        CommonFunctions.printStep();

        Test_01_07_05_2 test_01_07_05_2 = new Test_01_07_05_2();
        test_01_07_05_2.steps();
        requestNumber = test_01_07_05_2.requestNumber;
    }

    @Step("Переход к заявке")
    public void step01() {
        CommonFunctions.printStep();

        //Перезагрузить страницу
        //Нажать кнопку «Продолжить»
        new GUIFunctions().refreshTab("Продолжить", 10)
                .clickButton("Продолжить")
                .waitForLoading()
                .waitForElementDisplayed("//*[contains(text(),'Сертификация продукции АПК')]");
    }

    @Step("Заполнение блока \"Информация о Заявителе\"")
    public void step02() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Далее»
        new GUIFunctions().closeAllPopupWindows()
                .clickButton("Далее")
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
                .inField("Вид затраты, связанной с сертификацией продукции").selectValue("1\u00a0Услуги компетентного органа или уполномоченной организации в стране экспорта по осуществлению процедур оценки соответствия продукции (регистрации, подтверждения соответствия, испытаний, сертификации и других форм оценки соответствия, установленных законодательством иностранного государства или являющихся условием внешнеэкономического контракта)")
                    .assertValue("1 Услуги компетентного органа или уполномоченной организации в стране экспорта по осуществлению процедур оценки соответствия продукции (регистрации, подтверждения соответствия, испытаний, сертификации и других форм оценки соответствия, установленных законодательством иностранного государства или являющихся условием внешнеэкономического контракта)")
                .waitForElementDisplayed("//*[text()='Шаблон к Затрате 1.xlsm']")
                .inField("Основание понесенных затрат").selectValue("Требование контракта")
                .scrollTo("Загрузить шаблон")
                .uploadFile("Загрузить шаблон", "/share/" + WAY_TEST + "Шаблон 1 - фаст (1).xlsm");
//                .uploadFile("Загрузить шаблон","C:\\auto-tests\\Шаблон 1 - фаст (1).xlsm");
    }

    @Step("Заполнение блока \"Загрузка подтверждающих документов\"")
    public void step04() {
        CommonFunctions.printStep();

        //Выбрать приложенный файл с устройства формата zip и нажать кнопку «Открыть»
        //Выбрать приложенный файл с устройства формата zip и нажать кнопку «Открыть»
        new GUIFunctions().inContainer("Загрузка подтверждающих документов")
                .scrollTo("Подтверждающие документы")
                .uploadFile("Подтверждающие документы", "/share/" + WAY_TEST + "rec.zip")
//                .uploadFile("Подтверждающие документы", "C:\\auto-tests\\rec.zip")
                .uploadFile("Платежное поручение", "/share/" + WAY_TEST + "payment 228.zip")
//                .uploadFile("Платежное поручение", "C:\\auto-tests\\payment 228.zip")
                .scrollTo("Далее");

        //Нажать на кнопку «Далее»
        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Сведения о затратах']");
    }

    @Step("Заполнение блока \"Подтверждение сведений заявителем\"")
    public void step05() {
        CommonFunctions.printStep();

        new GUIFunctions().clickByLocator("(//*[text()='Добавить +'])[1]")
                .inField("ОКВЭД2").selectValue("01.11\u00a0Выращивание зерновых (кроме риса), зернобобовых культур и семян масличных культур")
                    .assertValue("01.11 Выращивание зерновых (кроме риса), зернобобовых культур и семян масличных культур")
                .inField("Признак вида деятельности").assertValue("Основной")
                .inField("Код типа вида деятельности").inputValue("1221").assertValue();

        new GUIFunctions().clickButton("Сохранить");

        new GUIFunctions()
                .inField("Код по ОКОПФ").inputValue("12247").assertValue()
                .inField("Наименование по ОКОПФ").inputValue("Частные учреждения").assertValue()
                .inField("Код по ОКПО").inputValue("12345678").assertValue()
                .inField("Код по ОКТМО").inputValue("12345678901").assertValue()
                .inField("Почтовый индекс").inputValue("123456").assertValue()
                .inField("Дата постановки на налоговый учёт").inputValue("01.08.2022").assertValue()
                .inField("Наименование субъекта Российской Федерации").selectValue("Новгородская область").assertValue()
                .inField("Тип населенного пункта").inputValue("город").assertValue()
                .inField("Наименование населенного пункта").selectValue("Населенный пункт").assertValue()
                .inField("Тип элемента планировочной структуры").inputValue("улица").assertValue()
                .inField("Наименование элемента планировочной структуры").inputValue("Волкова").assertValue()
                .inField("Тип элемента улично-дорожной сети").inputValue("дом").assertValue()
                .inField("Наименование элемента улично-дорожной сети").selectValue("Улица").assertValue()
                .inField("Тип объекта адресации").inputValue("Телевизионная").assertValue()
                .inField("Тип помещения").inputValue("поле").assertValue()
                .inField("Номер помещения, расположенного в здании или сооружении").inputValue("10").assertValue()
                .inField("Цифровое или буквенно-цифровое обозначение объекта адресации").inputValue("108").assertValue()
                .inField("Номер банковского счета").inputValue("40702810000000000046").assertValue()
                .inField("БИК банка").selectValue("004525659").assertValue()
                .inField("Корреспондентский счет").inputValue("30101810100000000722").assertValue();

        new GUIFunctions().inContainer("Данные о руководителе / уполномоченном лице компании")
                .inField("ИНН").inputValue("123456789012").assertValue()
                .inField("СНИЛС").inputValue("12345678901").assertValue()
                .inField("Телефон").inputValue("71234567890").assertValue("+7(712)345-67-89")
                .inField("Добавочный номер ").inputValue("20").assertValue();

        new GUIFunctions().inContainer("Контактные данные лица, ответственного за работу в ГИИС \"Электронный бюджет\"")
                .inField("ФИО").selectValue("фва\u00a0ыва\u00a0ва").assertValue("фва ыва ва")
                .inField("Телефон").inputValue("71234567890").assertValue("+7(712)345-67-89");

        new GUIFunctions().inContainer("Направление заявки на регистрацию уполномоченных лиц участника системы")
                .inField("Ввод данных (ФИО)").selectValue("Антонов\u00a0Антон\u00a0Антонович").assertValue("Антонов Антон Антонович")
                .inField("Согласование (ФИО)").selectValue("Антонов\u00a0Антон\u00a0Антонович").assertValue("Антонов Антон Антонович")
                .inField("Утверждение (ФИО)").selectValue("Антонов\u00a0Антон\u00a0Антонович").assertValue("Антонов Антон Антонович")
                .inField("Просмотр (ФИО)").selectValue("Антонов\u00a0Антон\u00a0Антонович").assertValue("Антонов Антон Антонович");

        String currentTime = DateFunctions.dateToday("dd.MM.yyyy");

        new GUIFunctions().inContainer("Подтверждение сведений Заявителем")
                .inField("ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \"ОРГАНИЗАЦИОННО-ТЕХНОЛОГИЧЕСКИЕ РЕШЕНИЯ 2000\" подтверждает, что на "+currentTime+" не находится в статусе реорганизации").setCheckboxON()
                .inField("ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \"ОРГАНИЗАЦИОННО-ТЕХНОЛОГИЧЕСКИЕ РЕШЕНИЯ 2000\" подтверждает, что на "+currentTime+" не имеет задолженности перед Бюджетом в рамках субсидии").setCheckboxON()
                .inField("ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕТСТВЕННОСТЬЮ \"ОРГАНИЗАЦИОННО-ТЕХНОЛОГИЧЕСКИЕ РЕШЕНИЯ 2000\" подтверждает, что на  "+currentTime+" заявленные затраты осуществлены на получение необходимых документов о сертификации продукции агропромышленного комплекса на внешних рынках на соответствие требованиям, предъявляемым на внешних рынках, которые могут включать требования заказчика, содержащиеся в контракте").setCheckboxON();

        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Подписать электронной подписью']");
    }

    @Step("Блок \"Подписание Заявки электронной подписью\"")
    public void step06() {
        CommonFunctions.printStep();

        new GUIFunctions().clickButton("Подписать электронной подписью")
                .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022").assertValue()
                .clickButton("Подписать")
                .waitForElementDisplayed("//*[text()='Подписано']")
                .clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Ваша заявка №"+requestNumber+" принята и находится на проверке']")
                .clickByLocator("//*[text()='В реестр заявок']");
    }
}
