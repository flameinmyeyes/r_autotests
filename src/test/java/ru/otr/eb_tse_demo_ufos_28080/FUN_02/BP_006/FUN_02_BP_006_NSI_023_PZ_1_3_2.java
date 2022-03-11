package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import com.codeborne.selenide.Condition;
import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.refresh;


public class FUN_02_BP_006_NSI_023_PZ_1_3_2 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_1_3_2\\";
    public String WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_2 = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_023_PZ_1_2\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_023. ПЗ п. 1.3.2. Требования к контролям справочника (контроль на уникальность)")
    @Link(name="TSE-T4820", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T4820")

    //после https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3199

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
        WAY_TEST = setWay(WAY_TEST);
        preconditions();
        step01();
        step02();
        step03();
        step04();
        step05();
        step06();
        step07();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    public void preconditions() throws Exception {
        CommonFunctions.printStep();
        String docGuid = FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_2 + "docGuid.txt");
        String request = "select ds.name from docstate ds join dict on ds.docstateid = dict.docstateid where dict.globaldocid = '" + docGuid + "'";
        String docStatus = APIFunctions.SQLRequest("src/test/java/ru/otr/eb_tse_demo_ufos/DBConfigTSE_DEMO.properties", request);

        if(!docStatus.equals("Актуальная")) {
            System.out.println((char) 27 + "[41m" + "Статус: " + docStatus + (char) 27 + "[0m");
            System.out.println((char) 27 + "[41m" + "Требуется выполнение предварительных условий" + (char) 27 + "[0m");
            //выполнить прогон теста
            FUN_02_BP_006_NSI_023_PZ_1_2 fun_02_bp_006_nsi_023_pz_1_2 = new FUN_02_BP_006_NSI_023_PZ_1_2();
            fun_02_bp_006_nsi_023_pz_1_2.steps();
            refresh();
            CommonFunctions.wait(3);
        } else {
            System.out.println((char) 27 + "[42m" + "Статус: " + docStatus + (char) 27 + "[0m");
            System.out.println((char) 27 + "[42m" + "Выполнение предварительных условий не требуется" + (char) 27 + "[0m");
        }

    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    public void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea"); //Рябова Анна Викторовна
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Причины отклонения документов»")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Причины отклонения документов");
    }

    @Step("Нажать кнопку «Создать» на панели инструментов")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .clickButtonTitle("Создать новый документ (Alt+N)");
    }

    @Step("Заполнить поля:\n" +
            "\"Код\"\n" +
            "\"Виды причин отказа\"\n" +
            "\"Дата действия с\"\n" +
            "\"Дата действия по\"")
    public void step04() {
        CommonFunctions.printStep();
        //"Код" = 40
        //"Виды причин отказа" = тестовый сценарий
        //"Дата действия с" = сегодняшняя дата
        //"Дата действия по" = сегодняшняя дата + 10 дней
        new DocPage()
                .inputValueInField("Код:", "40")
                .inputValueInField("Виды причин отказа:", FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_2 + "\\reject_reason.txt"))
                .inputValueInField("Дата действия с:", CommonFunctions.dateToday("dd.MM.yyyy"))
                .inputValueInField("Дата действия по:", CommonFunctions.dateShift(CommonFunctions.dateToday("dd.MM.yyyy"), "dd.MM.yyyy", +10));

        //поля заполнены:
        //"Код" = 40
        Assert.assertEquals($x("//input[@name='tf_dict_code']").getAttribute("value"), "40");
        //"Виды причин отказа" = тестовый сценарий
        Assert.assertEquals($x("//textarea[@name='tf_dict_name']").getAttribute("value"), FileFunctions.readValueFile(WAY_TEST_FUN_02_BP_006_NSI_023_PZ_1_2 + "\\reject_reason.txt"));
        //"Дата действия с" = сегодняшняя дата
        Assert.assertEquals($x("//input[@name='dtf_dict_startdate']").getAttribute("value"), CommonFunctions.dateToday("dd.MM.yyyy"));
        //"Дата действия по" = сегодняшняя дата + 10 дней
        Assert.assertEquals($x("//input[@name='dtf_dict_enddate']").getAttribute("value"), CommonFunctions.dateShift("dd.MM.yyyy", +10));
    }

    @Step("Ничего не заполняя нажать на кнопку \"Сохранить\"")
    private void step05() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Сохранить изменения (Alt+A)")
                .waitForLoading(300);

        //Выходит контроль:
        //Реквизит «Код» неуникален. Актуальная запись с указанным полем «Код» уже существует
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='dialog z-window z-window-noborder z-window-highlighted z-window-shadow']" +
                        "//span[contains(text(), \" Реквизит «Код» неуникален. Актуальная запись с указанным полем «Код» уже существует.\")]"), 60);
    }

    @Step("нажать на кнопку \"Сохранить\"")
    private void step06() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonInBlock("Результаты проверки", "Сохранить")
                .waitForLoading(120);
    }

    @Step("Нажать кнопку \"Актуализировать\"")
    private void step07() {
        CommonFunctions.printStep();
        new DocPage()
                .clickButtonTitle("Актуализировать")
                .waitForLoading(120);

        new DocPage().clickWebElement("//div[@class='processResultDialog z-window z-window-noborder z-window-highlighted z-window-shadow']" +
                "//div[text()='Документарный контроль выполнен с ошибками или предупреждениями']");

        new DocPage().clickWebElement("//div[@class='processResultDialog z-window z-window-noborder z-window-highlighted z-window-shadow']" +
                "//span[text()='Док. контроль']");

        //Выходит контроль:
        //Реквизит «Код» неуникален. Актуальная запись с указанным полем «Код» уже существует
        CommonFunctions.waitForElementDisplayed(By.xpath("//div[@class='processResultDialog z-window z-window-noborder z-window-highlighted z-window-shadow']" +
                "//span[contains(text(), 'Реквизит «Код» неуникален. Актуальная запись с указанным полем «Код» уже существует.')]"), 60);
    }

}
