package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.*;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.util.UUID;

public class FUN_02_BP_006_NSI_069_MT_8_KP_2_2 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_069_MT_8_KP_2_2\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_069. МТ8. КП2.2. Реализован справ-к \"Перечень КБК, допущенных к проведению операций по кас выплатам в период приос-ия операций на ЛС\" (Архивная)")
    @Link(name="TSE-T5955", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T5955")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
        step04();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Зайти в SoapUI.\n" +
            "Отправить подготовленный XML-запрос документа.")
    private void step01() {
        CommonFunctions.printStep();

        //1. Подготовлен XML-запрос, содержащего информацию по экземпляру формуляра/документу.
        //2. Для повторной загрузки документа требуется изменить поля:
        //GUID = guid уникальные значения реквизиты
        //NmbLS уникальные значения реквизиты
        //DtChng на год меньше текущей даты
        //StrtDt на год меньше текущей даты
        //EndDt текущей даты
        //В XML-запросе экземпляра формуляра изменены реквизиты .

        String sampleFilePath = WAY_TEST + "EXP_ListKBKCachPay.xml";
        String filePath = WAY_TEST + "EXP_ListKBKCachPay.xml";

        //гуид
        String docGUID = String.valueOf(UUID.randomUUID());
        FileFunctions.writeValueFile(WAY_TEST + "docGUID.txt", docGUID);
        FileFunctions.replaceParameterFile(sampleFilePath, filePath,
                "parameter_1", FileFunctions.readValueFile(WAY_TEST + "docGUID.txt"),"UTF-8");
        FileFunctions.updateXML(filePath, "GUID", FileFunctions.readValueFile(WAY_TEST + "docGUID.txt"));

        //NmbLS
        String accountNum = "04" + CommonFunctions.randomNumber(100000000, 900000000);
        FileFunctions.writeValueFile(WAY_TEST + "accountNum.txt", accountNum);
        FileFunctions.updateXML(filePath, "NmbLS", FileFunctions.readValueFile(WAY_TEST + "accountNum.txt"));

        //DtChng
        FileFunctions.writeValueFile(WAY_TEST + "changeDate.txt", CommonFunctions.dateShift("dd.MM.yyyy", -366));
        FileFunctions.updateXML(filePath, "DtChng", CommonFunctions.dateShift("yyyy-MM-dd", -366));

        //StrtDt
        FileFunctions.writeValueFile(WAY_TEST + "startDate.txt", CommonFunctions.dateShift("dd.MM.yyyy", -366));
        FileFunctions.updateXML(filePath, "StrtDt", CommonFunctions.dateShift("yyyy-MM-dd", -366));

        //EndDt
        FileFunctions.writeValueFile(WAY_TEST + "endDate.txt", CommonFunctions.dateToday("dd.MM.yyyy"));
        FileFunctions.updateXML(filePath, "EndDt", CommonFunctions.dateToday("yyyy-MM-dd"));

        //
        APIFunctions.sendSOAPRequest("http://eb-tse-demo-ufos.otr.ru:18080/web-services/documentLifeCycleService_v1_8", filePath);
        CommonFunctions.wait(10);
    }

    @Step("Авторизоваться в ЛК ЦС Обслуживания ПУР КС на стенде http://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step02() {
        CommonFunctions.printStep();
        new LoginPage().authorization("73faa96c-44d0-411f-9d16-85115f03f958");
    }

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» -" +
            " «Перечень КБК, допущенных к проведению операций по кассовым выплатам».")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Перечень КБК, допущенных к проведению операций по кассовым выплатам в период приостановления операций на лицевом счете");
    }

    @Step("Проверить что запись появился в справочнике и заполнено в соответствии с xml")
    private void step04() {
        CommonFunctions.printStep();
        new MainPage()
                .resetUserSettings()
                .filterColumnInList("Номер л/с", FileFunctions.readValueFile(WAY_TEST + "accountNum.txt"))
                .filterColumnInList("Дата действия с", FileFunctions.readValueFile(WAY_TEST + "startDate.txt"))
                .filterColumnInList("Дата действия по", FileFunctions.readValueFile(WAY_TEST + "endDate.txt"));

        //Запись появилась и данные заполнено в соответствии с xml
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][@title='" + FileFunctions.readValueFile(WAY_TEST + "accountNum.txt") + "']"), 60);
        CommonFunctions.waitForElementDisplayed(By.xpath("//td[@class='z-listcell'][@title='Архивная']"), 60);
    }

}
