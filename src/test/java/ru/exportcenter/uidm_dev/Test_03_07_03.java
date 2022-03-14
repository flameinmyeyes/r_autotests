package ru.exportcenter.uidm_dev;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
import functional.CommonFunctions;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

public class Test_03_07_03 extends Hooks_UIDM_DEV {

//    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_016\\FUN_02_BP_016_NSI_016_PZ_2_7_2_1\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_016. ПЗ п. 2.7.2. ТК №1 Проверка создания нового документа")
    @Link(name="Test_03_07_03", url="https://confluence.exportcenter.ru/pages/viewpage.action?pageId=117902506")
    @Test(retryAnalyzer = RunTestAgain.class)
    public void steps() {
//        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
    }

    @AfterMethod
//    public void screenShot() {
//        CommonFunctions.screenShot( WAY_TEST + "screen.png");
//    }

    @Step("1. Открыть браузер и перейти по ссылке http://uidm.uidm-dev.d.exportcenter.ru/ru/login\n" +
            "Ввести логин и пароль demo_exporter/password\n" +
            "2. Перейти во вкладку «Сервисы»\n" +
            "3. Выбрать сервис «Логистика. Доставка продукции «Агроэкспрессом»» и нажать кнопку «Оформить»\n" +
            "4. Нажать на кнопку «Продолжить»")
    public void step01() {
        CommonFunctions.printStep();
        //Ввести логин и пароль demo_exporter/password
        $(By.name("username")).sendKeys("demo_exporter");
        $(By.name("password")).sendKeys("password");
        $x("//button//*[text()='Войти']").click();

        webdriver().shouldHave(url("http://uidm.uidm-dev.d.exportcenter.ru/ru/main"), Duration.ofSeconds(30));

        //Перейти во вкладку «Сервисы»
        $x("//a[@class='header-menu-list__link '][text()='Сервисы']").click();
        webdriver().shouldHave(url("http://master-portal-dev.d.exportcenter.ru/services/business"), Duration.ofSeconds(30));

        //Выбрать сервис «Логистика. Доставка продукции «Агроэкспрессом»» и нажать кнопку «Оформить»
        $x("//input[@placeholder='Поиск по разделу']").sendKeys("Логистика\n");
        $x("//div[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']" +
                "/ancestor::div[@class='support-card support-card--active p-3']//*[text()='Оформить']").click();

        //Нажать на кнопку «Продолжить»
        switchTo().window(1);
        $x("//*[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']").shouldBe(Condition.visible, Duration.ofSeconds(30));
        refresh();
        $x("//*[text()='Логистика. Доставка продукции \"Агроэкспрессом\"']").shouldBe(Condition.visible, Duration.ofSeconds(30));
        $x("//button//*[text()='Продолжить']").click();
    }

    @Step("1. В поле «Почтовый адрес» вводим значение «Корнилаева 2»")
    public void step02() {
        //«Информация о компании»
        CommonFunctions.printStep();

    }

    @Step("1. Нажать на кнопку «Дополнительный контакт»\n" +
            "2. В поле «ФИО» вводим значение «Иванов Иван Иванович»\n" +
            "3. В поле «Телефон» вводим значение «+7(999)999-99-99»\n" +
            "4. В поле «Должность» вводим значение «Менеджер»" +
            "5. В поле «Email» вводим значение «word@mail.ru»")
    public void step03() {
        //«Информация о заявителе»
        CommonFunctions.printStep();
    }

    @Step("1. В поле «Город отправителя» выбрать значение из выпадающего списка.\n" +
            "Выбираем «Подольск»\n" +
            "2. В поле «Город назначения» выбрать значение из выпадающего списка.\n" +
            "Выбираем «Харбин»\n" +
            "3. Нажать на кнопку «Вывоз груза с адреса («Первая миля»)»\n" +
            "4. В поле «Адрес» вводим значение «Молодежная улица»\n" +
            "5. В поле «Предполагаемая дата отправления груза» вводим значение «22.10.2022»")
    public void step04() {
        //«Информация для оказания услуги»
        CommonFunctions.printStep();

    }

    @Step("1. Нажать на кнопку «Полный контейнер»\n" +
            "2. Нажать на кнопку «Добавить +»\n" +
            "3. Нажать на кнопку «Добавить новую»\n" +
            "4. В поле «Наименование продукции» вводим значение «Новая Продукция»\n" +
            "5. В поле «Код ТН ВЭД» выбрать значение из выпадающего списка.\n" +
            "Выбираем значение «Кофе»\n" +
            "6. В поле «Вес продукции, кг» вводим значение «16,000»\n" +
            "7. В поле «Упаковка» выбрать значение из выпадающего списка.\n" +
            "Выбираем «Барабаны»\n" +
            "8. Нажать на кнопку «Температурный режим на всю партию (от -30°C до 0°C или от 0°C до +30°C)»\n" +
            "9. В поле «От» ввести значение «-15»\n" +
            "10. В поле «До» ввести значение «+27»\n" +
            "11. В поле «Количество контейнеров» вводим значение «16»\n" +
            "12. В поле «Тип контейнера» выбрать значение из выпадающего списка.\n" +
            "Выбираем «Специализированный»\n" +
            "13. Нажать на кнопку «Сохранить»")
    public void step05() {
        //«Информация о грузе»
        CommonFunctions.printStep();

    }

    @Step("1. В поле «Наименование грузополучателя» вводим значение Ss-password\n" +
            "2. В поле «Страна» вводим значение «USA»\n" +
            "3. В поле «Город» вводим значение «Moscow»\n" +
            "4. В поле «Дом» вводим значение «Ff»\n" +
            "5. В поле «Регион» вводим значение «St-Peterburg»\n" +
            "6. В поле «Район» вводим значение «Raion»\n" +
            "7. В поле «Улица» вводим значение «Lenina street»\n" +
            "8. В поле «Регистрационный номер грузополучателя» вводим значение «223 22 44 2»\n" +
            "9. В поле «Телефон» вводим значение «+79999999999»\n" +
            "10. В поле «Представитель грузополучателя» вводим значение «Moscow disco rule»\n" +
            "11. В поле «Email» вводим значение «www@mail.ru»")
    public void step06() {
        //«Информация о грузополучателе»
        CommonFunctions.printStep();

    }


    @Step("1. Нажать на кнопку «Вывоз груза с адреса («Первая миля»)»\n" +
            "2. Нажать на кнопку «Таможенное оформление»\n" +
            "3. Выбрать одно из значений «РЭЦ» или «РЖД Логистика».\n" +
            "Выбираем «РЭЦ»\n" +
            "4. Нажать на кнопку «Оформление ветеринарного сертификата»\n" +
            "5. Выбрать одно из значений «РЭЦ» или «РЖД Логистика».\n" +
            "Выбираем «РЭЦ»\n" +
            "6. Нажать на кнопку «Оформление фитосанитарного сертификата»\n" +
            "7. Выбрать одно из значений «РЭЦ» или «РЖД Логистика».\n" +
            "Выбираем «РЭЦ»\n" +
            "8. Нажать на кнопку «Далее», для перехода на следующий шаг")
    public void step07() {
        //«Дополнительные услуги»
        CommonFunctions.printStep();

    }

}
