package ru.exportcenter.test.patents;

import framework.RunTestAgain;
import framework.Ways;
import functions.file.PropertiesHandler;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;

import functions.common.CommonFunctions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.exportcenter.test.HooksTEST;

import java.util.Properties;

import static com.codeborne.selenide.Selenide.*;

//АРХИВ


@Deprecated()
public class Test_02_08_01 extends HooksTEST {

    private String WAY_TEST = Ways.TEST.getWay() + "/patents/Test_02_08_01/";
    public String WAY_TO_PROPERTIES = WAY_TEST + "Test_02_08_01.xml";
    public Properties PROPERTIES = PropertiesHandler.parseProperties(WAY_TO_PROPERTIES);

    @Description("ТК 02 08 01")
    @Owner(value="Теребков Андрей, Петрищев Руслан")
    @Link(name="Test_02_08_01", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=123869730")

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
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST);
    }

    @Step("Авторизация")
    private void step01(){
        CommonFunctions.printStep();

        //Ввести логин и пароль test-otr@yandex.ru/Password1!
        new GUIFunctions().inContainer("Вход в личный кабинет")
                .authorization(PROPERTIES.getProperty("Авторизация.Email"), PROPERTIES.getProperty("Авторизация.Пароль"))
                .waitForURL("https://lk.t.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    private void step02(){
        CommonFunctions.printStep();

        //Перейти во вкладку «Сервисы»
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("https://master-portal.t.exportcenter.ru/services/business");

        //Выбрать сервис «Компенсация части затрат на регистрацию ОИС за рубежом» и нажать кнопку «Оформить»
        new GUIFunctions().waitForElementDisplayed("//input[@placeholder='Поиск по разделу']")
                .inputInSearchField("Поиск по разделу", "Компенсация части затрат на регистрацию ОИС за рубежом")
                .clickByLocator("//div[text()='Государственные']")
                .openSearchResult("Компенсация части затрат на регистрацию ОИС", "Оформить");

        //На жать на кнопку «Продолжить»
        switchTo().window(1);
        new GUIFunctions().waitForElementDisplayed("//div[@class='Steps_stepsWrapper__2dJpS']");

        //Перезагрузить страницу
        refreshTab("//span[text()='Продолжить']", 30);

        //Нажать кнопку «Продолжить»
        new GUIFunctions().clickButton("Продолжить");
    }

    @Step("Блок «Заявитель»")
    private void step03(){
        CommonFunctions.printStep();

        //В поле «Регион» из выпадающего списка выбрать значение «Республика Коми»
        new GUIFunctions().inContainer("Сведения о заявителе")
                .inField("Регион").selectValue(PROPERTIES.getProperty("Заявитель.Регион"));


        //Нажать на кнопку «Далее»
        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Добавить способ получения услуг']");
    }

    @Step("Блок «Способ получения услуги»")
    private void step04() {
        CommonFunctions.printStep();

        //Нажать на кнопку «Добавить способ получения услуги»
        new GUIFunctions().clickButton("Добавить способ получения услуг");

        //В поле «Укажите способ оказания услуг» выбрать значение «Напрямую организацией»
        new GUIFunctions().inContainer("Выберите способ получения услуги")
                .inField("Способ оказания услуг").selectValue(PROPERTIES.getProperty("Способ получения услуги.Способ оказания услуг"));

        //Выбрать приложенный файл с устройства формата txt и нажать кнопку «Открыть»
        new GUIFunctions().uploadFile("Прикрепить платежное поручение","C:\\auto-tests\\tmp.txt");
//        new GUIFunctions().uploadFile("Прикрепить платежное поручение",WAY_TEST + "tmp.txt");
        CommonFunctions.wait(5);

        //Нажать кнопку «Далее»
        $x("//*[text()='Далее']").click();
    }

    @Step("Блок «Заявка»")
    private void step05(){
        CommonFunctions.printStep();

        //Нажать кнопку «Добавить тип заявки»
        new GUIFunctions().clickButton("Добавить тип заявки");

        //Заполнение полей в блоке «Заявка»
        new GUIFunctions().inContainer("Заявка")
                .inField("Тип заявки").selectValue(PROPERTIES.getProperty("Заявка.Тип заявки")).assertNoControl()
                .inField("Подтип международной заявки").selectValue(PROPERTIES.getProperty("Заявка.Подтип международной заявки")).assertNoControl()
                .inField("Номер базовой заявки/свидетельства").inputValue(PROPERTIES.getProperty("Заявка.Номер базовой заявки/свидетельства")).assertNoControl()
                .inField("Дата подачи базовой заявки/свидетельства").inputValue(PROPERTIES.getProperty("Заявка.Дата подачи базовой заявки/свидетельства")).assertNoControl()
                .inField("Номер международной заявки").inputValue(PROPERTIES.getProperty("Заявка.Номер международной заявки")).assertNoControl()
                .inField("Дата подачи международной заявки").inputValue(PROPERTIES.getProperty("Заявка.Дата подачи международной заявки")).assertNoControl()
                .inField("Наименование объекта").inputValue(PROPERTIES.getProperty("Заявка.Наименование объекта")).assertNoControl()
                .inField("Наименование заявителя (организации)").inputValue(PROPERTIES.getProperty("Заявка.Наименование заявителя (организации)")).assertNoControl()
                .closeAllPopupWindows();
    }

    @Step("Блок «Цель затрат и описание»")
    private void step06(){
        CommonFunctions.printStep();

        //В выпадающем списке «Цель правовой охраны за рубежом» выбрать значение «Создание собственного производства за рубежом»
        new GUIFunctions().inContainer("Цель затрат и описание")
                .inField("Цель правовой охраны за рубежом").selectValue(PROPERTIES.getProperty("Цель затрат и описание.Цель правовой охраны за рубежом")).assertNoControl()
                .inField("Описание конечного продукта/технологии, в том числе его конкурентные преимущества").inputValue(PROPERTIES.getProperty("Цель затрат и описание.Описание конечного продукта/технологии")).assertNoControl()
                .inField("Описание потребителя конечного продукта (технологии)").inputValue(PROPERTIES.getProperty("Цель затрат и описание.Описание потребителя конечного продукта (технологии)")).assertNoControl()
                .inField("Описание целевых рынков для реализации продукции").inputValue(PROPERTIES.getProperty("Цель затрат и описание.Описание целевых рынков для реализации продукции")).assertNoControl()
                .inField("Описание бизнес-модели вывода продукции на внешние рынки").inputValue(PROPERTIES.getProperty("Цель затрат и описание.Описание бизнес-модели вывода продукции на внешние рынки")).assertNoControl()
                .inField("Оценка вероятного экономического эффекта от введения за рубежом в гражданский оборот продукции, в состав которой будет входить предлагаемый объект интеллектуальной собственности").inputValue(PROPERTIES.getProperty("Цель затрат и описание.Оценка вероятного экономического эффекта")).assertNoControl()
                .closeAllPopupWindows();

        //Нажать на кнопку «Загрузите документ»
        new GUIFunctions().uploadFile("Заявка на регистрацию ОИС","C:\\auto-tests\\tmp.pdf");
//        new GUIFunctions().uploadFile("Заявка на регистрацию ОИС",WAY_TEST + "tmp.pdf");
        CommonFunctions.wait(5);

        //Нажать на кнопку «Медиа-файл объекта»
        new GUIFunctions().clickByLocator("//span[@class= 'Checkbox_label__3B7Mj'][text()='Медиа-файл объекта']")
                .closeAllPopupWindows();

        //Выбрать приложенный файл с устройства формата pdf и нажать кнопку «Открыть»
        new GUIFunctions().uploadFile("Прикрепить медиа-файл объекта","C:\\auto-tests\\tmp.pdf");
//        new GUIFunctions().uploadFile("Прикрепить медиа-файл объекта",WAY_TEST + "tmp.pdf");
        CommonFunctions.wait(5);
    }

    @Step("Блок «Затраты на оплату пошлины»")
    private void step07(){
        CommonFunctions.printStep();

        //Нажать на кнопку «Добавить затрату на оплату пошлины»
        new GUIFunctions().clickButton("Добавить затрату на оплату пошлины");

        //Заполнение полей
        new GUIFunctions().inContainer("Затраты на оплату пошлины")
                .inField("Наименование пошлины").inputValue(PROPERTIES.getProperty("Затраты на оплату пошлины.Наименование пошлины")).assertNoControl()
                .inField("Объем затрат").inputValue(PROPERTIES.getProperty("Затраты на оплату пошлины.Объем затрат")).assertNoControl()
//                .inField("Валюта").selectValue("ЮЖНОСУДАНСКИЙ ФУНТ").assertNoControl()
                .inField("Валюта").selectValue(PROPERTIES.getProperty("Затраты на оплату пошлины.Валюта")).assertNoControl()
                .inField("Дата платежа").inputValue(PROPERTIES.getProperty("Затраты на оплату пошлины.Дата платежа")).assertNoControl()
                .inField("НДС").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Налоговая ставка, %").inputValue(PROPERTIES.getProperty("Затраты на оплату пошлины.Налоговая ставка")).assertNoControl()
                .closeAllPopupWindows()
                .inField("Способ получения услуги").selectValue(PROPERTIES.getProperty("Затраты на оплату пошлины.Способ получения услуги")).assertNoControl()
                .inField("Платёжное поручение").selectValue("tmp.txt");
    }

    @Step("Блок «Затраты на оплату услуг»")
    private void step08(){
        CommonFunctions.printStep();

        //Нажать на кнопку «Добавить затрату на оплату услуги»
        new GUIFunctions().clickButton("Добавить затрату на оплату услуги");

        //Заполнение полей
        new GUIFunctions().inContainer("Затраты на оплату услуги")
                .inField("Наименование услуги по подготовке, подаче заявки и делопроизводству по ней").inputValue(PROPERTIES.getProperty("Затраты на оплату услуг.Наименование услуги по подготовке")).assertNoControl()
                .inField("Объем затрат").inputValue(PROPERTIES.getProperty("Затраты на оплату услуг.Объем затрат")).assertNoControl()
//                .inField("Валюта").selectValue("ЮЖНОСУДАНСКИЙ ФУНТ").assertNoControl()
                .inField("Валюта").selectValue(PROPERTIES.getProperty("Затраты на оплату услуг.Валюта")).assertNoControl()
                .inField("Дата платежа").inputValue(PROPERTIES.getProperty("Затраты на оплату услуг.Дата платежа")).assertNoControl()
                .inField("НДС").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Налоговая ставка, %").inputValue(PROPERTIES.getProperty("Затраты на оплату услуг.Налоговая ставка")).assertNoControl()
                .closeAllPopupWindows()
                .inField("Способ получения услуги").selectValue(PROPERTIES.getProperty("Затраты на оплату услуг.Способ получения услуги")).assertNoControl()
                .inField("Платёжное поручение").selectValue("tmp.txt");

        //Нажать на кнопку «Далее»
        new GUIFunctions().clickButton("Далее")
                .waitForElementDisplayed("//*[text()='Сводная информация и подтверждение сведений Заявителя']");
    }

    @Step("Блок «Сведения для регистрации / актуализации в ГИИС Электронный бюджет»")
    private void step09(){
        CommonFunctions.printStep();

        //Заполнение полей в блоке «Сведения для регистрации / актуализации в ГИИС "Электронный бюджет"»
        new GUIFunctions().clickButton("Сведения для регистрации / актуализации в ГИИС \"Электронный бюджет\"")
                .inContainer("Сведения для регистрации / актуализации в ГИИС \"Электронный бюджет\"")
                .inField("Компания зарегистрирована в ГИИС \"Электронный бюджет\"").setCheckboxOFF().assertNoControl()
                .inField("Код по ОКОПФ").inputValue(PROPERTIES.getProperty("Сведения для регистрации.Код по ОКОПФ")).assertNoControl()
                .inField("Наименование по ОКОПФ").inputValue(PROPERTIES.getProperty("Сведения для регистрации.Наименование по ОКОПФ")).assertNoControl()
                .inField("Код по ОКПО").inputValue(PROPERTIES.getProperty("Сведения для регистрации.Код по ОКПО")).assertNoControl()
                .inField("Код по ОКТМО").inputValue(PROPERTIES.getProperty("Сведения для регистрации.Код по ОКТМО")).assertNoControl()
                .inField("Почтовый индекс").inputValue(PROPERTIES.getProperty("Сведения для регистрации.Почтовый индекс")).assertNoControl()
                .inField("Дата постановки на налоговый учёт").inputValue(PROPERTIES.getProperty("Сведения для регистрации.Дата постановки на налоговый учёт")).assertNoControl()
                .inField("Номер банковского счета").inputValue(PROPERTIES.getProperty("Сведения для регистрации.Номер банковского счета")).assertNoControl()
                .inField("БИК банка").selectValue(PROPERTIES.getProperty("Сведения для регистрации.БИК банка")).assertNoControl()
                .inField("Корреспондентский счет").inputValue(PROPERTIES.getProperty("Сведения для регистрации.Корреспондентский счет")).assertNoControl();
    }

    @Step("Блок «Данные о руководителе / уполномоченном лице компании»")
    private void step10(){
        CommonFunctions.printStep();

        //Заполнение полей в блоке "Данные о руководителе / уполномоченном лице компании"
        new GUIFunctions().inContainer("Данные о руководителе / уполномоченном лице компании")
                .inField("Реквизиты документа, на основании которого действует руководитель / уполномоченное лицо").inputValue(PROPERTIES.getProperty("Данные о руководителе.Реквизиты документа")).assertNoControl()
                .inField("ИНН").inputValue(PROPERTIES.getProperty("Данные о руководителе.ИНН")).assertNoControl()
                .inField("СНИЛС").inputValue(PROPERTIES.getProperty("Данные о руководителе.СНИЛС")).assertNoControl()
                .inField("E-mail").inputValue(PROPERTIES.getProperty("Данные о руководителе.E-mail")).assertNoControl()
                .inField("Телефон").inputValue(PROPERTIES.getProperty("Данные о руководителе.Телефон")).assertNoControl();
    }

    @Step("Блок «Контактные данные лица, ответственного за работу в ГИИС Электронный бюджет»")
    private void step11(){
        CommonFunctions.printStep();

        //Заполнение полей в блоке «Контактные данные лица, ответственного за работу в ГИИС "Электронный бюджет"»
        new GUIFunctions().inContainer("Контактные данные лица, ответственного за работу в ГИИС \"Электронный бюджет\"")
                .inField("ФИО").selectValue(PROPERTIES.getProperty("Контактные данные.ФИО контактного лица")).assertNoControl()
                .inField("Телефон").inputValue(PROPERTIES.getProperty("Контактные данные.Телефон")).assertNoControl()
                .inField("E-mail").inputValue(PROPERTIES.getProperty("Контактные данные.E-mail")).assertNoControl();
    }

    @Step("Блок «Подтверждение сведений Заявителем»")
    private void step12(){
        CommonFunctions.printStep();

        //Проставление чекбоксов в блоке «Подтверждение сведений Заявителем»
        new GUIFunctions().clickButton("Подтверждение сведений Заявителем")
                .inContainer("Подтверждение сведений Заявителем")
                .inField("Организация не находится в процессе реорганизации (за исключением реорганизации в форме присоединения к организации другого юридического лица), ликвидации, в отношении нее не введена процедура банкротства, деятельность организации не приостановлена в порядке, предусмотренном законодательством Российской Федерации.").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("У организации отсутствует просроченная задолженность по возврату в федеральный бюджет субсидий, бюджетных инвестиций, предоставленных в том числе в соответствии с иными правовыми актами, и иная просроченная (неурегулированная) задолженность перед федеральным бюджетом, в том числе задолженность по денежным обязательствам перед Российской Федерацией, определенным статьей 93.4 Бюджетного кодекса Российской Федерации.").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Организация не получала субсидии из федерального бюджета на возмещение одних и тех же затрат, связанных с регистрацией на внешних рынках одних и тех же объектов интеллектуальной собственности, на основании иных нормативных правовых актов, в том числе Правил предоставления субсидий российским производителям в целях компенсации части затрат, связанных с регистрацией на внешних рынках объектов интеллектуальной собственности.").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Обременения прав на объект интеллектуальной собственности отсутствуют").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Согласие на публикацию в  сети «Интернет» информации об организации как участнике отбора").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Согласие на получение центром сведений из заявок, заявлений, свидетельств и патентов о регистрации прав на объекты интеллектуальной собственности в Российской Федерации, а также международных заявок, поданных через Федеральную службу по интеллектуальной собственности.").setCheckboxON().assertCheckboxON().assertNoControl();

        //Нажать на кнопку «Далее»
        new GUIFunctions().clickButton("Далее");
    }

    @Step("Блок «Подписание Заявки электронной подписью»")
    private void step13(){
        CommonFunctions.printStep();

        //Нажать на кнопку "Подписать электронной подписью"
        new GUIFunctions().clickButton("Подписать электронной подписью");

        //Выбрать сертификат
        new GUIFunctions().inContainer("Электронная подпись")
                .inField("Выберите сертификат").selectValue("Ермухамбетова Балсикер Бисеньевна от 18.01.2022");

        //Нажать на кнопку "Подписать"
        new GUIFunctions().clickButton("Подписать");

        //Нажать на кнопку «Далее»
        new GUIFunctions().clickButton("Далее");
    }

    private void refreshTab(String expectedXpath, int times) {
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