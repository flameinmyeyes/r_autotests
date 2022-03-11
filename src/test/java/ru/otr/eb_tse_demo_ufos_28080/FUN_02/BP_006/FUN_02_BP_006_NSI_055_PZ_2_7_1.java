package ru.otr.eb_tse_demo_ufos_28080.FUN_02.BP_006;

import framework.RunTestAgain;
import framework.Ways;
import functional.CommonFunctions;
import functional.LoginPage;
import functional.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import ru.otr.eb_tse_demo_ufos_28080.HooksTSE_DEMO_28080;

import java.util.HashMap;
import java.util.Map;

import static ru.otr.eb_tse_demo_ufos_28080.FUN_01.BP_001.ColumnSorting.column;

public class FUN_02_BP_006_NSI_055_PZ_2_7_1 extends HooksTSE_DEMO_28080 {

    private String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_055_PZ_2_7_1\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_055. ПЗ п. 2.7.1. Требования к СФ. Требования к отоб-нию реквизитов в списковой форме спр-ка")
    @Link(name="TSE-T3243", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3243")
    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() {
        WAY_TEST = setWay(WAY_TEST);
        step01();
        step02();
        step03();
    }

    @AfterMethod
    public void screenShot() {
        CommonFunctions.screenShot(WAY_TEST + "screen.png");
    }

    @Step("Авторизоваться в ЛК Клиента ПУР КС на стендеhttp://eb-tse-demo-ufos.otr.ru:28080/index.zul")
    private void step01() {
        CommonFunctions.printStep();
        new LoginPage().authorization("71822a6f-36b6-4ef8-b91f-34ba6c8b40ea");
    }

    @Step("Переход по дереву навигации к справочнику «Реестр приостанавливаемых операций»")
    private void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                " Справочники>" +
                " Реестр приостанавливаемых операций");
    }

    @Step("Проверить что в СФ есть колонки Вид блокировки,Документ-основание,Номер документа-основания," +
            "Дата документа-основания,Вид приостанавливаемой операции,Номер лицевого счета,КБК," +
            "Код цели/Аналитический код,ФАИП/КМИ,Дата с,Дата по,Статус")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage().resetUserSettings();

        //Есть колонки Вид блокировки,  Документ-основание, Номер документа-основания,
        // Дата документа-основания, Вид приостанавливаемой операции, Номер лицевого счета, КБК,
        // Код цели/Аналитический код, ФАИП/КМИ, Дата с, Дата по, Статус

        Map<String, Boolean> map = new HashMap<>();

        map.put("Вид блокировки", true);
        map.put("Документ-основание", true);
        map.put("Номер документа-основания", true);
        map.put("Дата документа-основания", true);
        map.put("Вид приостанавливаемой операции", true);
        map.put("Номер лицевого счета", true);
        map.put("КБК ", true);
        map.put("Код цели/Аналитический код", true);
        map.put("ФАИП/КМИ", true);
        map.put("Дата с", true);
        map.put("Дата по", true);
        map.put("Статус", true);

        new MainPage().checkColumnVisibility(map);
    }

}
