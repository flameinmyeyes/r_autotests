package ru.exportcenter.dev.patents;

import framework.RunTestAgain;
import functions.gui.GUIFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;

import functions.common.CommonFunctions;
import org.testng.annotations.Test;
import ru.exportcenter.dev.HooksDEV;

import static com.codeborne.selenide.Selenide.*;

public class Test_02_08_01 extends HooksDEV {

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
//        step13();
    }

    @Step("Авторизация")
    private void step01(){
        CommonFunctions.printStep();

        //Ввести логин и пароль demo_exporter/password
        new GUIFunctions().inContainer("Вход в личный кабинет")
                .inField("Email").inputValue("demo_exporter")
                .inField("Пароль").inputValue("password")
                .clickButton("Войти")
                .waitForURL("http://uidm.uidm-dev.d.exportcenter.ru/ru/main");
    }

    @Step("Навигация")
    private void step02(){
        CommonFunctions.printStep();

        //Перейти во вкладку «Сервисы»
        new GUIFunctions().selectTab("Сервисы")
                .waitForURL("http://master-portal-dev.d.exportcenter.ru/services/business");

        //Выбрать сервис «Компенсация части затрат на регистрацию ОИС за рубежом» и нажать кнопку «Оформить»
        new GUIFunctions().waitForElementDisplayed("//input[@placeholder='Поиск по разделу']")
                .inputInSearchField("Поиск по разделу", "Компенсация части затрат на регистрацию ОИС за рубежом").
                clickByLocator("//div[text()='Государственные']")
                .openSearchResult("Компенсация части затрат на регистрацию ОИС", "Оформить");

        //Нажать на кнопку «Продолжить»
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
                .inField("Регион").selectValue("Республика Коми");

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
                .inField("Способ оказания услуг").selectValue("Напрямую организацией");

        //Выбрать приложенный файл с устройства формата txt и нажать кнопку «Открыть»
        new GUIFunctions().uploadFile("Прикрепить платежное поручение","C:\\auto-tests\\tmp.txt");
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
                .inField("Тип заявки").selectValue("Международная заявка").assertValue().assertNoControl()
                .inField("Подтип международной заявки").selectValue("Международная заявка на регистрацию изобретения в соответствии с договором о патентной кооперации РСТ").assertValue().assertNoControl()
                .inField("Номер базовой заявки/свидетельства").inputValue("987654321").assertValue().assertNoControl()
                .inField("Дата подачи базовой заявки/свидетельства").inputValue("02.02.2022").assertValue().assertNoControl()
                .inField("Номер международной заявки").inputValue("987654321").assertValue().assertNoControl()
                .inField("Дата подачи международной заявки").inputValue("03.02.2022").assertValue().assertNoControl()
                .inField("Наименование объекта").inputValue("Объект").assertValue().assertNoControl()
                .inField("Наименование заявителя (организации)").inputValue("Заявитель").assertValue().assertNoControl();
    }

    @Step("Блок «Цель затрат и описание»")
    private void step06(){
        CommonFunctions.printStep();

        //В выпадающем списке «Цель правовой охраны за рубежом» выбрать значение «Создание собственного производства за рубежом»
        new GUIFunctions().inContainer("Цель затрат и описание")
                .inField("Цель правовой охраны за рубежом").selectValue("Создание собственного производства за рубежом").assertValue().assertNoControl()
                .inField("Описание конечного продукта/технологии, в том числе его конкурентные преимущества").inputValue("Значение").assertValue().assertNoControl()
                .inField("Описание потребителя конечного продукта (технологии)").inputValue("word").assertValue().assertNoControl()
                .inField("Описание целевых рынков для реализации продукции").inputValue("Значениеss").assertValue().assertNoControl()
                .inField("Описание бизнес-модели вывода продукции на внешние рынки").inputValue("ввод").assertValue().assertNoControl()
                .inField("Оценка вероятного экономического эффекта от введения за рубежом в гражданский оборот продукции, в состав которой будет входить предлагаемый объект интеллектуальной собственности").inputValue("Ocenka").assertValue().assertNoControl();

        //Нажать на кнопку «Загрузите документ»
        new GUIFunctions().uploadFile("Заявка на регистрацию ОИС","C:\\auto-tests\\tmp.pdf");
        CommonFunctions.wait(5);

        //Нажать на кнопку «Медиа-файл объекта»
        new GUIFunctions()
                .clickByLocator("//span[@class= 'Checkbox_label__3B7Mj'][text()='Медиа-файл объекта']");

        //Выбрать приложенный файл с устройства формата pdf и нажать кнопку «Открыть»
        new GUIFunctions().uploadFile("Прикрепить медиа-файл объекта","C:\\auto-tests\\tmp.pdf");
        CommonFunctions.wait(5);
    }

    @Step("Блок «Затраты на оплату пошлины»")
    private void step07(){
        CommonFunctions.printStep();

        //Нажать на кнопку «Добавить затрату на оплату пошлины»
        new GUIFunctions().clickButton("Добавить затрату на оплату пошлины");

        //Заполнение полей
        new GUIFunctions().inContainer("Затраты на оплату пошлины")
                .inField("Наименование пошлины").inputValue("Пошлина").assertValue().assertNoControl()
                .inField("Объем затрат").inputValue("1230,00").assertValue().assertNoControl()
                .inField("Валюта").selectValue("ЮЖНОСУДАНСКИЙ ФУНТ").assertValue().assertNoControl()
                .inField("Дата платежа").inputValue("02.02.2022").assertValue().assertNoControl()
                .inField("НДС").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Налоговая ставка, %").inputValue("13,00").assertValue().assertNoControl()
                .inField("Способ получения услуги").selectValue("Напрямую организацией").assertValue().assertNoControl()
                .inField("Платёжное поручение").selectValue("tmp.txt").assertValue().assertNoControl();
    }

    @Step("Блок «Затраты на оплату услуг»")
    private void step08(){
        CommonFunctions.printStep();

        //Нажать на кнопку «Добавить затрату на оплату услуги»
        new GUIFunctions().clickButton("Добавить затрату на оплату услуги");

        //Заполнение полей
        new GUIFunctions().inContainer("Затраты на оплату услуги")
                .inField("Наименование услуги по подготовке, подаче заявки и делопроизводству по ней").inputValue("Услуга").assertValue().assertNoControl()
                .inField("Объем затрат").inputValue("1230,00").assertValue().assertNoControl()
                .inField("Валюта").selectValue("ЮЖНОСУДАНСКИЙ ФУНТ").assertValue().assertNoControl()
                .inField("Дата платежа").inputValue("02.02.2022").assertValue().assertNoControl()
                .inField("НДС").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Налоговая ставка, %").inputValue("13,00").assertValue().assertNoControl()
                .inField("Способ получения услуги").selectValue("Напрямую организацией").assertValue().assertNoControl()
                .inField("Платёжное поручение").selectValue("tmp.txt").assertValue().assertNoControl();

        //Нажать на кнопку «Далее»
        new GUIFunctions().clickButton("Далее").clickButton("Далее");
    }

    @Step("Блок «Сведения для регистрации / актуализации в ГИИС Электронный бюджет»")
    private void step09(){
        CommonFunctions.printStep();

        //Заполнение полей в блоке «Сведения для регистрации / актуализации в ГИИС "Электронный бюджет"»
        new GUIFunctions().inContainer("Сведения для регистрации / актуализации в ГИИС Электронный бюджет")
                .inField("Код по ОКОПФ").inputValue("12247").assertValue().assertNoControl()
                .inField("Наименование по ОКОПФ").inputValue("Наименование").assertValue().assertNoControl()
                .inField("Код по ОКПО").inputValue("12345678").assertValue().assertNoControl()
                .inField("Код по ОКТМО").inputValue("12345678901").assertValue().assertNoControl()
                .inField("Почтовый индекс").inputValue("630000").assertValue().assertNoControl()
                .inField("Дата постановки на налоговый учёт").inputValue("02.02.2022").assertValue().assertNoControl()
                .inField("Номер банковского счета").inputValue("40702810000000000046").assertValue().assertNoControl()
                .inField("БИК банка").selectValue("044525050").assertValue().assertNoControl()
                .inField("Корреспондентский счет").inputValue("30101810100000000722").assertValue().assertNoControl();
    }

    @Step("Блок «Данные о руководителе / уполномоченном лице компании»")
    private void step10(){
        CommonFunctions.printStep();

        //Заполнение полей в блоке "Данные о руководителе / уполномоченном лице компании"
        new GUIFunctions().inContainer("Данные о руководителе / уполномоченном лице компании")
                .inField("Реквизиты документа, на основании которого действует руководитель / уполномоченное лицо").inputValue("Устав").assertValue().assertNoControl()
                .inField("ИНН").inputValue("1234567890").assertValue().assertNoControl()
                .inField("СНИЛС").inputValue("12345678901").assertValue().assertNoControl()
                .inField("E-mail").inputValue("wor_ld@mai-l.ru").assertValue().assertNoControl()
                .inField("Телефон").inputValue("+1234567890").assertValue().assertNoControl();
    }

    @Step("Блок «Контактные данные лица, ответственного за работу в ГИИС Электронный бюджет»")
    private void step11(){
        CommonFunctions.printStep();

        //Заполнение полей в блоке «Контактные данные лица, ответственного за работу в ГИИС "Электронный бюджет"»
        new GUIFunctions().inContainer("Контактные данные лица, ответственного за работу в ГИИС Электронный бюджет")
                .inField("ФИО контактного лица").selectValue("Иванов Иван Иванович").assertValue().assertNoControl()
                .inField("Телефон").inputValue("+7(123)456-78-90").assertValue().assertNoControl()
                .inField("E-mail").inputValue("wor_ld@mai-l.ru").assertValue().assertNoControl();
    }

    @Step("Блок «Подтверждение сведений Заявителем»")
    private void step12(){
        CommonFunctions.printStep();

        //Проставление чекбоксов в блоке «Подтверждение сведений Заявителем»
        new GUIFunctions().inContainer("Подтверждение сведений Заявителем")
                .inField("Подтверждение сведений Заявителем\", \"Организация не находится в процессе реорганизации (за исключением реорганизации в форме присоединения к организации другого юридического лица), ликвидации, в отношении нее не введена процедура банкротства, деятельность организации не приостановлена в порядке, предусмотренном законодательством Российской Федерации.").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("У организации отсутствует просроченная задолженность по возврату в федеральный бюджет субсидий, бюджетных инвестиций, предоставленных в том числе в соответствии с иными правовыми актами, и иная просроченная (неурегулированная) задолженность перед федеральным бюджетом, в том числе задолженность по денежным обязательствам перед Российской Федерацией, определенным статьей 93.4 Бюджетного кодекса Российской Федерации.").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Подтверждение сведений Заявителем\", \"Организация не получала субсидии из федерального бюджета на возмещение одних и тех же затрат, связанных с регистрацией на внешних рынках одних и тех же объектов интеллектуальной собственности, на основании иных нормативных правовых актов, в том числе Правил предоставления субсидий российским производителям в целях компенсации части затрат, связанных с регистрацией на внешних рынках объектов интеллектуальной собственности.").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Подтверждение сведений Заявителем\", \"Обременения прав на объект интеллектуальной собственности отсутствуют").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Подтверждение сведений Заявителем\", \"Согласие на публикацию в  сети «Интернет» информации об организации как участнике отбора").setCheckboxON().assertCheckboxON().assertNoControl()
                .inField("Подтверждение сведений Заявителем\", \"Согласие на получение центром сведений из заявок, заявлений, свидетельств и патентов о регистрации прав на объекты интеллектуальной собственности в Российской Федерации, а также международных заявок, поданных через Федеральную службу по интеллектуальной собственности.").setCheckboxON().assertCheckboxON().assertNoControl();

        //Нажать на кнопку «Далее»
        new GUIFunctions().clickButton("Далее").clickButton("Далее");
    }

    @Step("Блок \"Подписание Заявки электронной подписью\"")
    private void step13(){
        CommonFunctions.printStep();

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