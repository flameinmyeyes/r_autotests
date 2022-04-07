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
import ru.exportcenter.test.HooksTEST;
import java.util.Properties;
import static com.codeborne.selenide.Selenide.*;

public class Test_02_08_02_3 extends HooksTEST{

    private String WAY_TEST = Ways.TEST.getWay() + "/patents/Test_02_08_02_3/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_02_08_02_3_properties.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Owner(value="Петрищев Руслан, Теребков Андрей")
    @Description("ТК 02 08 02.3 Заполнение сведений для регистрации/актуализации в ГИИС ЭБ. Подписание Заявки УКЭП. Загрузка Заявки на локальный компьютер пользователя.")
    @Link(name="Test_02_08_02_3", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123869661")

    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps(){
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
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    private void step01(){
        CommonFunctions.printStep();

        //Ввести логин и пароль demo_exporter/password
//        new GUIFunctions()
////                .authorization(PROPERTIES.getProperty("Авторизация.Email"),PROPERTIES.getProperty("Авторизация.Пароль"), "1234")
//                .authorization(PROPERTIES.getProperty("Авторизация.Email"),PROPERTIES.getProperty("Авторизация.Пароль"))
//                .waitForLoading();

        new GUIFunctions().inContainer("Вход в личный кабинет")
                .inField("Email").inputValue(PROPERTIES.getProperty("Авторизация.Email"))
                .inField("Пароль").inputValue(PROPERTIES.getProperty("Авторизация.Пароль"))
                .clickButton("Войти")
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    private void step02(){
        CommonFunctions.printStep();

        // кастыль
        open("https://lk.t.exportcenter.ru/ru/promo-service?key=ProcessPatent&serviceId=d3d7a7b0-934b-4ca9-b660-9940e9d8b1f2&next_query=true");
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
                .inField("Регион").selectValue("Республика Коми");

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
                .inField("Тип заявки").selectValue("Международная заявка")
                .inField("Подтип международной заявки").selectValue("Международная заявка на регистрацию промышленного образца в соответствии с Женевским актом Гаагского соглашения")
                .inField("Номер базовой заявки/свидетельства").inputValue("987654321")
                .inField("Дата подачи базовой заявки/свидетельства").inputValue("02.02.2022")
                .inField("Номер международной заявки").inputValue("987654321")
                .inField("Дата подачи международной заявки").inputValue("02.02.2022")
                .inField("Номер электронного дела").inputValue("987654321")
                .inField("Страны регистрации").selectValue("Абхазия")
                .inField("Наименование объекта").inputValue("Объект")
                .inField("Наименование заявителя (организации)").inputValue("Заявитель")
                .inField("Региональное патентное ведомство").selectValue("Европейское патентное ведомство (ЕПВ)");
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
                .inField("Цель правовой охраны за рубежом").selectValue("Продажа прав на технологию путем заключения лицензионного договора или договора отчуждения исключительного права")
                .inField("Описание конечного продукта/технологии, в том числе его конкурентные преимущества").inputValue("Значение")
                .inField("Описание потребителя конечного продукта (технологии)").inputValue("word")
                .inField("Описание целевых рынков для реализации продукции").inputValue("Значениеss")
                .inField("Описание бизнес-модели вывода продукции на внешние рынки").inputValue("ввод")
                .inField("Оценка вероятного экономического эффекта от введения за рубежом в гражданский оборот продукции, в состав которой будет входить предлагаемый объект интеллектуальной собственности").inputValue("Ocenka");

        //Выбрать приложенный файл с устройства формата pdf и нажать кнопку «Открыть»
        new GUIFunctions().uploadFile("Заявка на регистрацию ОИС","/share/" + WAY_TEST + "tmp.pdf");
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
                .inField("Наименование пошлины").inputValue("Пошлина")
                .inField("Объем затрат").inputValue("1230,00")
                .inField("Валюта").selectValue("Грузинский лари")
                .inField("Дата платежа").inputValue("23.02.2022")
                .inField("Способ получения услуги").selectValue("Напрямую организацией")
                .closeAllPopupWindows()
                .inField("Платёжное поручение").selectValue("tmp.txt");
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
                .inField("Наименование услуги по подготовке, подаче заявки и делопроизводству по ней").inputValue("Услуга")
                .inField("Объем затрат").inputValue("1230,00")
                .inField("Валюта").selectValue("Грузинский лари")
                .inField("Дата платежа").inputValue("23.02.2022")
                .inField("Способ получения услуги").selectValue("Напрямую организацией")
                .inField("Платёжное поручение").selectValue("tmp.txt");

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
                .inField("Код по ОКОПФ").inputValue("12247")
                .inField("Наименование по ОКОПФ").inputValue("Наименование")
                .inField("Код по ОКПО").inputValue("12345678")
                .inField("Код по ОКТМО").inputValue("12345678901")
                .inField("Почтовый индекс").inputValue("630000")
                .inField("Дата постановки на налоговый учёт").inputValue("02.02.2022");
    }

    @Step("Блок «Банковские реквизиты заявителя»")
    private void step10() {
        CommonFunctions.printStep();

        CommonFunctions.wait(5);
        new GUIFunctions().closeAllPopupWindows();

        //Заполнение полей
        new GUIFunctions().inContainer("Сведения для регистрации / актуализации в ГИИС \"Электронный бюджет\"")
                .inField("Номер банковского счета").inputValue("40702810000000000046")
                .inField("БИК банка").selectValue("200000614")
                .inField("Корреспондентский счет").inputValue("30101810100000000722");
    }

    @Step("Блок «Лицевые счета, открытые в территориальном органе Федерального казначейства (ТОФК)»")
    private void step11(){
        CommonFunctions.printStep();

        //Нажать кнопку «Добавить +»
        new GUIFunctions().clickButton("Добавить +");

        //Заполнение полей
        new GUIFunctions().inContainer("Лицевые счета, открытые в территориальном органе Федерального казначейства (ТОФК)")
                .inField("Полное наименование ТОФК").selectValue("Отдел № 1 Управления Федерального казначейства по Республике Башкортостан")
                .inField("Код ТОФК открытия по сводному реестру").inputValue("6000")
                .inField("Код ОРФК обслуживания").selectValue("0100")
                .inField("Код типа лицевого счёта").selectValue("05")
                .inField("Номер лицевого счёта").inputValue("05601863340")
                .inField("Дата открытия").inputValue("02.02.2022")
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
                .inField("Фамилия").inputValue("Летов")
                .inField("Имя").inputValue("Игорь")
                .inField("Отчество").inputValue("Федорович")
                .inField("Должность").inputValue("поэт")
                .inField("Реквизиты документа, на основании которого действует руководитель / уполномоченное лицо").inputValue("доверенность")
                .inField("ИНН").inputValue("1234567890")
                .inField("СНИЛС").inputValue("12345678901")
                .inField("E-mail").inputValue("wor_ld@mai-l.ru")
                .inField("Телефон").inputValue("1234567891");
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
                .inField("Фамилия").inputValue("Летов")
                .inField("Имя").inputValue("Игорь")
                .inField("Отчество").inputValue("Федорович")
                .inField("Телефон").inputValue("1234567891")
                .inField("E-mail").inputValue("wor_ld@mai-l.ru");
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

//    public class MyThread extends Thread {
//        @Override
//        public void run() {
//            int i = 1;
//            GUIFunctions GUIF = new GUIFunctions();
//            while(i>0) {
//                System.out.println("closeAllPopupWindows()");
//                GUIF.closeAllPopupWindows();
//                CommonFunctions.wait(5);
//            }
//        }
//    }

}