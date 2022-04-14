package ru.exportcenter.test.patents;

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

import java.awt.*;
import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

public class Test_02_08_02_3 extends Hooks {

    private String WAY_TEST = Ways.TEST.getWay() + "/patents/Test_02_08_02_3/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_02_08_02_3_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Петрищев Руслан, Теребков Андрей")
    @Description("ТК 02 08 02.3 Заполнение сведений для регистрации/актуализации в ГИИС ЭБ. Подписание Заявки УКЭП. Загрузка Заявки на локальный компьютер пользователя.")
    @Link(name="Test_02_08_02_3", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123869661")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() throws AWTException{
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
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    private void step01(){
        CommonFunctions.printStep();
        open(PROPERTIES.getProperty("start_URL"));

        new GUIFunctions()
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    //PROPERTIES.getProperty("Авторизация.Код")

    @Step("Навигация")
    private void step02(){
        CommonFunctions.printStep();

        // кастыль
        open(PROPERTIES.getProperty("direct_URL"));
        new GUIFunctions().waitForURL("https://lk.t.exportcenter.ru/ru/promo-service?key=ProcessPatent&serviceId=d3d7a7b0-934b-4ca9-b660-9940e9d8b1f2&next_query=true");
        refreshTab("//*[contains(text(), 'Продолжить')]", 60);

        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Заполнить область «Информация о компании»")
    public void step03() {
        CommonFunctions.printStep();

        CommonFunctions.wait(5);
        new GUIFunctions().closeAllPopupWindows();

        new GUIFunctions().inContainer("Сведения о заявителе")
                .inField("Регион").selectValue(PROPERTIES.getProperty("Сведения о заявителе.Регион"));

        new GUIFunctions().clickButton("Далее").clickButton("Далее")
                .waitForElementDisplayed("//form[@class='Form_form__Vhvzn']");
    }

    @Step("Заполнить область «Информация о компании»")
    public void step04() {
        CommonFunctions.printStep();

        CommonFunctions.wait(5);
        new GUIFunctions().closeAllPopupWindows();

        //Нажать на кнопку «Добавить способ получения услуги»
        new GUIFunctions().clickButton("Добавить способ получения услуг");

        //В поле «Укажите способ оказания услуг» выбрать значение «Напрямую организацией»
        new GUIFunctions()
                .inContainer("Выберите способ получения услуги")
                .inField("Способ оказания услуг")
                .selectValue("Напрямую организацией");

        //Выбрать приложенный файл с устройства формата txt и нажать кнопку «Открыть»
        new GUIFunctions().uploadFile("Прикрепить платежное поручение", "/share/" + WAY_TEST + "tmp.txt");
//        new GUIFunctions().uploadFile("Прикрепить платежное поручение", "C:\\auto-tests\\tmp.txt");
        CommonFunctions.wait(5);

        //Нажать кнопку «Далее»
        new GUIFunctions().clickButton("Далее");
    }

    @Step("Блок «Заявка»")
    private void step05(){
        CommonFunctions.printStep();

        CommonFunctions.wait(5);
        new GUIFunctions().closeAllPopupWindows();


        //Нажать кнопку «Добавить тип заявки»
        new GUIFunctions().clickButton("Добавить тип заявки");

        //Заполнение полей
        new GUIFunctions()
                .closeAllPopupWindows()
                .inContainer("Заявка")
                .closeAllPopupWindows()
                .inField("Тип заявки").selectValue(PROPERTIES.getProperty("Заявка.Тип заявки"))
                .inField("Подтип международной заявки").selectValue(PROPERTIES.getProperty("Заявка.Подтип международной заявки"))
                .inField("Номер базовой заявки/свидетельства").inputValue(PROPERTIES.getProperty("Заявка.Номер базовой заявки/свидетельства"))
                .inField("Дата подачи базовой заявки/свидетельства").inputValue(PROPERTIES.getProperty("Заявка.Дата подачи базовой заявки/свидетельства"))
                .inField("Номер международной заявки").inputValue(PROPERTIES.getProperty("Заявка.Номер международной заявки"))
                .inField("Дата подачи международной заявки").inputValue(PROPERTIES.getProperty("Заявка.Дата подачи международной заявки"))
                .inField("Номер электронного дела").inputValue(PROPERTIES.getProperty("Заявка.Номер электронного дела"))
                .inField("Страны регистрации").selectValue(PROPERTIES.getProperty("Заявка.Страны регистрации"))
                .inField("Наименование объекта").inputValue(PROPERTIES.getProperty("Заявка.Наименование объекта"))
                .inField("Наименование заявителя (организации)").inputValue(PROPERTIES.getProperty("Наименование заявителя (организации)"))
                .inField("Региональное патентное ведомство").selectValue(PROPERTIES.getProperty("Заявка.Региональное патентное ведомство"));
    }

    @Step("Блок «Цель затрат и описание»")
    private void step06(){
        CommonFunctions.printStep();

        CommonFunctions.wait(5);
        new GUIFunctions().closeAllPopupWindows();

        //Заполнение полей
        new GUIFunctions()
                .closeAllPopupWindows()
                .inContainer("Заявка")
                .inField("Цель правовой охраны за рубежом").selectValue(PROPERTIES.getProperty("Цель правовой охраны за рубежом"))
                .inField("Описание конечного продукта/технологии, в том числе его конкурентные преимущества").inputValue(PROPERTIES.getProperty("Описание конечного продукта/технологии"))
                .inField("Описание потребителя конечного продукта (технологии)").inputValue(PROPERTIES.getProperty("Описание потребителя конечного продукта"))
                .inField("Описание целевых рынков для реализации продукции").inputValue(PROPERTIES.getProperty("Описание целевых рынков для реализации продукции"))
                .inField("Описание бизнес-модели вывода продукции на внешние рынки").inputValue(PROPERTIES.getProperty("Описание бизнес-модели вывода продукции на внешние рынки"))
                .inField("Оценка вероятного экономического эффекта от введения за рубежом в гражданский оборот продукции, в состав которой будет входить предлагаемый объект интеллектуальной собственности").inputValue(PROPERTIES.getProperty("Оценка вероятного экономического эффекта от введения за рубежом в гражданский оборот продукции"));

        //Выбрать приложенный файл с устройства формата pdf и нажать кнопку «Открыть»
      new GUIFunctions().uploadFile("Заявка на регистрацию ОИС","/share/" + WAY_TEST + "tmp.pdf");
//        new GUIFunctions().uploadFile("Заявка на регистрацию ОИС","C:\\auto-tests\\tmp.pdf");
        CommonFunctions.wait(5);
    }

    @Step("Блок «Затраты на оплату пошлины»")
    private void step07(){
        CommonFunctions.printStep();

        CommonFunctions.wait(5);
        new GUIFunctions().closeAllPopupWindows();

        //Нажать на кнопку «Добавить затрату на оплату пошлины»
        new GUIFunctions().clickButton("Добавить затрату на оплату пошлины");

        //Заполнение полей
        new GUIFunctions().inContainer("Затраты на оплату пошлины")
                .inField("Наименование пошлины").inputValue(PROPERTIES.getProperty("Наименование пошлины"))
                .inField("Объем затрат").inputValue(PROPERTIES.getProperty("Объем затрат"))
                .inField("Валюта").selectValue(PROPERTIES.getProperty("Валюта"))
                .inField("Дата платежа").inputValue(PROPERTIES.getProperty("Дата платежа"))
                .inField("Способ получения услуги").selectValue(PROPERTIES.getProperty("Способ получения услуги"))
                .closeAllPopupWindows()
                .inField("Платёжное поручение").selectValue(PROPERTIES.getProperty("Платёжное поручение"));
    }

    @Step("Блок «Добавить затрату на оплату услуги»")
    private void step08(){
        CommonFunctions.printStep();

        CommonFunctions.wait(5);
        new GUIFunctions().closeAllPopupWindows();

        //Нажать на кнопку «Добавить затрату на оплату услуги»
        new GUIFunctions().clickButton("Добавить затрату на оплату услуги");

        //Заполнение полей
        new GUIFunctions().inContainer("Затраты на оплату услуги")
                .inField("Наименование услуги по подготовке, подаче заявки и делопроизводству по ней").inputValue(PROPERTIES.getProperty("Наименование услуги по подготовке"))
                .inField("Объем затрат").inputValue(PROPERTIES.getProperty("Объем затрат"))
                .inField("Валюта").selectValue(PROPERTIES.getProperty("Валюта"))
                .inField("Дата платежа").inputValue(PROPERTIES.getProperty("Дата платежа"))
                .inField("Способ получения услуги").selectValue(PROPERTIES.getProperty("Способ получения услуги"))
                .inField("Платёжное поручение").selectValue(PROPERTIES.getProperty("Платёжное поручение"));

        //Нажать на кнопку «Далее»
        new GUIFunctions().clickButton("Далее");
    }

    @Step("Блок «Сведения для регистрации / актуализации в ГИИС Электронный бюджет»")
    private void step09() {
        CommonFunctions.printStep();

        CommonFunctions.wait(5);
        new GUIFunctions().closeAllPopupWindows();

        new GUIFunctions().clickButton("Сведения для регистрации / актуализации в ГИИС \"Электронный бюджет\"");

        //Заполнение полей
        new GUIFunctions().inContainer("Сведения для регистрации / актуализации в ГИИС \"Электронный бюджет\"")
                .inField("Компания зарегистрирована в ГИИС \"Электронный бюджет\"").setCheckboxOFF()
                .inField("Код по ОКОПФ").inputValue(PROPERTIES.getProperty("Код по ОКОПФ"))
                .inField("Наименование по ОКОПФ").inputValue(PROPERTIES.getProperty("Наименование по ОКОПФ"))
                .inField("Код по ОКПО").inputValue(PROPERTIES.getProperty("Код по ОКПО"))
                .inField("Код по ОКТМО").inputValue(PROPERTIES.getProperty("Код по ОКТМО"))
                .inField("Почтовый индекс").inputValue(PROPERTIES.getProperty("Почтовый индекс"))
                .inField("Дата постановки на налоговый учёт").inputValue(PROPERTIES.getProperty("Дата постановки на налоговый учёт"));
    }

    @Step("Блок «Банковские реквизиты заявителя»")
    private void step10() {
        CommonFunctions.printStep();

        CommonFunctions.wait(5);
        new GUIFunctions().closeAllPopupWindows();

        //Заполнение полей
        new GUIFunctions().inContainer("Сведения для регистрации / актуализации в ГИИС \"Электронный бюджет\"")
                .inField("Номер банковского счета").inputValue(PROPERTIES.getProperty("Номер банковского счета"))
                .inField("БИК банка").selectValue(PROPERTIES.getProperty("БИК банка"))
                .inField("Корреспондентский счет").inputValue(PROPERTIES.getProperty("Корреспондентский счет"));
    }

    @Step("Блок «Лицевые счета, открытые в территориальном органе Федерального казначейства (ТОФК)»")
    private void step11(){
        CommonFunctions.printStep();

        //Нажать кнопку «Добавить +»
        new GUIFunctions().clickButton("Добавить +");

        //Заполнение полей
        new GUIFunctions().inContainer("Лицевые счета, открытые в территориальном органе Федерального казначейства (ТОФК)")
                .inField("Полное наименование ТОФК").selectValue(PROPERTIES.getProperty("Полное наименование ТОФК"))
                .inField("Код ТОФК открытия по сводному реестру").inputValue(PROPERTIES.getProperty("Код ТОФК открытия по сводному реестру"))
                .inField("Код ОРФК обслуживания").selectValue(PROPERTIES.getProperty("Код ОРФК обслуживания"))
                .inField("Код типа лицевого счёта").selectValue(PROPERTIES.getProperty("Код типа лицевого счёта"))
                .inField("Номер лицевого счёта").inputValue(PROPERTIES.getProperty("Номер лицевого счёта"))
                .inField("Дата открытия").inputValue(PROPERTIES.getProperty("Дата открытия"))
                .clickButton("Сохранить");
    }
    @Step("Блок «Данные о руководителе / уполномоченном лице компании»")
    private void step12(){
        CommonFunctions.printStep();

        CommonFunctions.wait(5);
        new GUIFunctions().closeAllPopupWindows();

        //Выбрать чекбокс «Уполномоченное лицо»
        new GUIFunctions().inContainer("Данные о руководителе / уполномоченном лице компании")
                .inField("Уполномоченное лицо").setCheckboxON();

        //Заполнение полей
        new GUIFunctions().inContainer("Данные о руководителе / уполномоченном лице компании")
                .inField("Фамилия").inputValue(PROPERTIES.getProperty("Фамилия"))
                .inField("Имя").inputValue(PROPERTIES.getProperty("Имя"))
                .inField("Отчество").inputValue(PROPERTIES.getProperty("Отчество"))
                .inField("Должность").inputValue(PROPERTIES.getProperty("Должность"))
                .inField("Реквизиты документа, на основании которого действует руководитель / уполномоченное лицо").inputValue(PROPERTIES.getProperty("Реквизиты документа"))
                .inField("ИНН").inputValue(PROPERTIES.getProperty("ИНН"))
                .inField("СНИЛС").inputValue(PROPERTIES.getProperty("СНИЛС"))
                .inField("E-mail").inputValue(PROPERTIES.getProperty("E-mail"))
                .inField("Телефон").inputValue(PROPERTIES.getProperty("Телефон"));
    }

    @Step("Блок «Контактные данные лица, ответственного за работу в ГИИС Электронный бюджет»")
    private void step13(){
        CommonFunctions.printStep();

        CommonFunctions.wait(5);
        new GUIFunctions().closeAllPopupWindows();

        //Выбрать чекбокс «Уполномоченное лицо»
        new GUIFunctions().inContainer("Контактные данные лица, ответственного за работу в ГИИС \"Электронный бюджет\"")
                .inField("Добавить ответственного за работу в ГИИС \"ЭБ\"").setCheckboxON();

        //Заполнение полей
        new GUIFunctions().inContainer("Контактные данные лица, ответственного за работу в ГИИС \"Электронный бюджет\"")
                .inField("Фамилия").inputValue(PROPERTIES.getProperty("Фамилия"))
                .inField("Имя").inputValue(PROPERTIES.getProperty("Имя"))
                .inField("Отчество").inputValue(PROPERTIES.getProperty("Отчество"))
                .inField("Телефон").inputValue(PROPERTIES.getProperty("Телефон"))
                .inField("E-mail").inputValue(PROPERTIES.getProperty("E-mail"));
    }

    @Step("Блок «Подтверждение сведений Заявителем»")
    private void step14(){
        CommonFunctions.printStep();

        CommonFunctions.wait(5);
        new GUIFunctions().closeAllPopupWindows();

        //Выбрать чекбокс «Уполномоченное лицо»
        new GUIFunctions().inContainer("Подтверждение сведений Заявителем")
                .clickByLocator("//*[text() = 'Подтверждение сведений Заявителем']")
                .inField("Организация не находится в процессе реорганизации (за исключением реорганизации в форме присоединения к организации другого юридического лица), ликвидации, в отношении нее не введена процедура банкротства, деятельность организации не приостановлена в порядке, предусмотренном законодательством Российской Федерации.").setCheckboxON()
                .inField("У организации отсутствует просроченная задолженность по возврату в федеральный бюджет субсидий, бюджетных инвестиций, предоставленных в том числе в соответствии с иными правовыми актами, и иная просроченная (неурегулированная) задолженность перед федеральным бюджетом, в том числе задолженность по денежным обязательствам перед Российской Федерацией, определенным статьей 93.4 Бюджетного кодекса Российской Федерации.").setCheckboxON()
                .inField("Организация не получала субсидии из федерального бюджета на возмещение одних и тех же затрат, связанных с регистрацией на внешних рынках одних и тех же объектов интеллектуальной собственности, на основании иных нормативных правовых актов, в том числе Правил предоставления субсидий российским производителям в целях компенсации части затрат, связанных с регистрацией на внешних рынках объектов интеллектуальной собственности.").setCheckboxON()
                .inField("Обременения прав на объект интеллектуальной собственности отсутствуют").setCheckboxON()
                .inField("Согласие на публикацию в  сети «Интернет» информации об организации как участнике отбора").setCheckboxON()
                .inField("Согласие на получение центром сведений из заявок, заявлений, свидетельств и патентов о регистрации прав на объекты интеллектуальной собственности в Российской Федерации, а также международных заявок, поданных через Федеральную службу по интеллектуальной собственности.").setCheckboxON();

        //Нажать на кнопку «Далее»
        new GUIFunctions().clickButton("Далее");
    }

    @Step("Блок «Загрузка Заявки на локальный компьютер пользователя»")
    private void step15() {
        CommonFunctions.printStep();

        // Проверка сохранения, печати и просмотра документа (проверка кнопок)
        String downloadXPath = "//button[contains(@class,'downloadButton')][not(@disabled)][*[@viewBox='0 0 16 16']]";
        String printXPath = "//button[contains(@class,'printButton')][not(@disabled)][*[@viewBox='0 0 22 22']]";
        String viewXPath = "//button[contains(@class,'printButton')][not(@disabled)][*[@viewBox='0 0 16 10']]";

        new GUIFunctions().waitForElementDisplayed(downloadXPath)
                .waitForElementDisplayed(printXPath)
                .waitForElementDisplayed(viewXPath);
    }

    @Step("Блок «Подписание Заявки электронной подписью»")
    private void step16() {
        CommonFunctions.printStep();

        //Нажать на кнопку "Подписать электронной подписью"
        new GUIFunctions().clickButton("Подписать электронной подписью");

        //Из выпадающего списка выбрать сертификат "Ермухамбетова Балсикер Бисеньевна от 18.01.2022"
        //Нажать на кнопку "Подписать"

         new GUIFunctions().inContainer("Электронная подпись")
                .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022")
                .clickButton("Подписать");

        ////Нажать на кнопку «Далее»
        new GUIFunctions().clickButton("Далее");

    }

    private void refreshTab(String expectedXpath, int times) {
        for (int i = 0; i < times; i++) {
            new GUIFunctions().waitForLoading();
            if($x(expectedXpath).isDisplayed()) {
                break;
            }
            refresh();
            System.out.println("refresh()");
            CommonFunctions.wait(1);
        }
    }
}
