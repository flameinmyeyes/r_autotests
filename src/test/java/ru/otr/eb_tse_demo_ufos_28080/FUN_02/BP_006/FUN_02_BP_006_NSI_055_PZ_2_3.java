package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.Ways;
import functional.CommonFunctions;
import functional.LoginPage;
import functional.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class FUN_02_BP_006_NSI_055_PZ_2_3 extends HooksTSE_DEMO_28080 {

    /**
     * https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3238
     * NSI_055. ПЗ п. 2.3. Реквизитный состав спр-ка
     */

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay()
            + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_055_PZ_2_3\\";

    @Owner(value = "Якубов Алексей")
    @Description("NSI_055. ПЗ п. 2.3. Реквизитный состав спр-ка")
    @Link(name="TSE-T3238", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3238")

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    //@Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    @Test(groups = {"all_tests_tse", "yakubov_aleksei", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
        step05();
    }

    @Step("TSE-T3883 (1.0) Авторизация в ЛК Клиента с ролью 605_01_02 ПУР КС. Ввод и адмнистрирование локальных НСИ")
    public void step01() {
        CommonFunctions.printStep();
        //new LoginPage().authorization("Loboda.KI", "Oracle33");
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
        new MainPage().waitForLoading(120);
        CommonFunctions.waitForElementDisplayed(By.xpath("//span[text()='Корневая навигация']"), 30);
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='tree-holder z-center']"), 60);
    }

    @Step("TSE-T4953 (1.0) Переход по дереву навигации к справочнику «Реестр приостанавливаемых операций»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Реестр приостанавливаемых операций").waitForLoading(30);
        new MainPage().resetUserSettings();
    }

    @Step("Выбрать запись, где Номер ЛС 01951000510")
    public void step03() {
        CommonFunctions.printStep();
        new MainPage().filterColumnInList("Номер лицевого счета", "01951000510");
        new MainPage().waitForLoading(30);
        new MainPage().clickRowsInList(1).waitForLoading(30);
    }

    @Step("Нажать кнопку \"Просмотреть\"")
    public void step04() {
        CommonFunctions.printStep();
        new MainPage().clickWebElement("//button[@title='Открыть документ на просмотр'][not(@disabled)]").waitForLoading(10);
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[@name='efl_dict_inf_typelock']"), 30);
    }

    @Step("Проверить реквизитов")
    public void step05() {
        CommonFunctions.printStep();
        //Вид блокировки = Блокировка по ИД и РНО, тип 1
        checkField("Вид блокировки", "efl_dict_inf_typelock", "Блокировка по ИД и РНО, тип 1");
        //Документ - основание блокировки = Сведения о бюджетном обязательстве
        checkField("Документ - основание блокировки", "tf_dict_inf_docbase", "Сведения о бюджетном обязательстве");
        //Реквизиты документа - основания блокировки= 65346 27.05.2020
        checkField("Реквизиты документа - основания блокировки", "tf_dict_inf_numdocbase", "65346");
        checkField("Реквизиты документа - основания блокировки", "dtf_dict_inf_docbasedate", "27.05.2020");



        //Вид приостанавливаемой операции = Кассовые выплаты
        checkField("Вид приостанавливаемой операции", "", "Кассовые выплаты");
        //Похоже тут баг

        //Номер лицевого счета = 01951000510
        checkField("Номер лицевого счета", "tf_dict_inf_persaccountnum", "01951000510");
        //КБК = 51000000000000000000
        checkField("КБК", "tf_dict_inf_kbk", "51000000000000000000");
        //Код цели/Аналитический код = -
        checkField("Код цели/Аналитический код", "tf_dict_inf_codecs", "-");
        //ФАИП = 9676456
        checkField("ФАИП", "tf_dict_inf_faip", "9676456");
        //Статус записи = Актуальная
        checkField("Статус записи", "tf_dict_recordstatus", "Актуальная");
    }




    /**
     * Проверяю поле на содержимое
     */
    public void checkField(String label, String name, String value) {
        CommonFunctions.waitForElementDisplayed(By.xpath("//*[@name='" + name + "']"), 20);

        if (value.equals("") || value.equals("[NBSP]") || value.equals(" ")) {
            if (!$(By.xpath("//*[@name='" + name + "']")).getText().equals("")
                    || !$(By.xpath("//*[@name='" + name + "']")).getValue().equals("")
                    || !$(By.xpath("//*[@name='" + name + "']")).getAttribute("title").equals("") )
            {
                System.out.println("Ожидалось, что поле '" + label + "' будет не заполнено.");
                org.testng.Assert.fail();
            }else{
                System.out.println("Поле '" + label + "' не заполнено, как и ожидалось.");
            }
        }else{
            if ($(By.xpath("//*[@name='" + name + "']")).getText().equals(value)
                    || $(By.xpath("//*[@name='" + name + "']")).getValue().equals(value)
                    || $(By.xpath("//*[@name='" + name + "']")).getAttribute("title").equals(value) )
            {
                System.out.println("Данные в поле '" + label + "' совпали с ожидаемыми.");
            }else{
                System.out.println("Ожидалось, что в поле '" + label + "' будет значение '" + value + "'");
                System.out.println("\\x1b[1m\\x1b[31mАктуальный результат:\\x1b[0m");
                System.out.println("\\x1b[31mValue: " + $(By.xpath("//*[@name='" + name + "']")).getValue() + "\\x1b[0m");
                System.out.println("\\x1b[31mTitle: " + $(By.xpath("//*[@name='" + name + "']")).getAttribute("title") + "\\x1b[0m");
                System.out.println("\\x1b[31mText: " + $(By.xpath("//*[@name='" + name + "']")).getText() + "\\x1b[0m");
                org.testng.Assert.fail();
            }
        }
    }

}
