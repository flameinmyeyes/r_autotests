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

public class FUN_02_BP_006_NSI_031_PZ_1_7 extends HooksTSE_DEMO_28080 {

    public String WAY_TEST = Ways.TSE_DEMO_28080.getWay() + "\\FUN_02\\BP_006\\FUN_02_BP_006_NSI_031_PZ_1_7\\";

    @Owner(value="Балашов Илья")
    @Description("NSI_031. ПЗ п. 1.7. Требования к СФ. Требования к отоб-нию реквизитов в списковой форме спр-ка")
    @Link(name="TSE-T3230", url="https://job-jira.otr.ru/secure/Tests.jspa#/testCase/TSE-T3230")

    @Test(retryAnalyzer = RunTestAgain.class, groups = {"all_tests_tse", "balashov_ilya", "FUN_02", "BP_006"})
    public void steps() throws Exception {
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

    @Step("Открыть дерево навигации: «Управление расходами (казначейское сопровождение)» - «Справочники» - «Настройки доступа ТОФК по месту обращения».")
    public void step02() {
        CommonFunctions.printStep();
        new MainPage().openNavigation(" Управление расходами (казначейское сопровождение)>" +
                        " Справочники>" +
                        " Настройки доступа ТОФК по месту обращения");
    }

    @Step("Проверить отображение колонок СФ")
    private void step03() {
        CommonFunctions.printStep();
        new MainPage().resetUserSettings();

        Map<String, Boolean> map = new HashMap<>();

        //Есть колонки
        //"Код ТОФК",
        map.put("Код ТОФК", true);
        //"Код СВР ТОФК",
        map.put("Код СВР ТОФК", true);
        //"Наименование ТОФК",
        map.put("Наименование ТОФК", true);
        //"ИНН Клиента", "Код СВР/ИП и КФХ Клиента", "Полное наименование Клиента",
        map.put("ИНН Клиента", true);
        map.put("Код СВР / ИП и КФХ Клиента", true);
        map.put("Полное наименование Клиента", true);
        //"Дата начала действия записи" ,
        map.put("Дата начала действия записи", true);
        //"Дата окончания действия записи",
        map.put("Дата окончания действия записи", true);
        //"Статус",
        map.put("Статус", true);
        //"ФИО сотрудника, добавившего запись",
        map.put("ФИО сотрудника, добавившего запись", true);
        //"Дата создания записи",
        map.put("Дата создания записи", true);
        //"Дата последнего изменения записи",
        map.put("Дата последнего изменения записи", true);
        //"ФИО сотрудника, который перевел запись в архив"
        map.put("ФИО сотрудника, который перевел запись в архив", true);

        new MainPage().checkColumnVisibility(map);
    }

}
