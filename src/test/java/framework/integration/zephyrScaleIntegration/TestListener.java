package framework.integration.zephyrScaleIntegration;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import java.util.ArrayList;
import java.util.List;

public class TestListener implements ITestListener {

    /*
    статусы:
    Не запускался - Not Executed
    В работе - In Progress
    Пройден - Pass
    Провален - Fail
    Блокирован - Blocked
    Не требуется выполнять - Не требуется выполнять
    Пройдено с замечаниями - Пройдено с замечаниями
     */

//    private static String runMode;
    private static String testrun;
    private static boolean rerunFailedTestsOnly;
    private static boolean skipSendingTestresult = false;
    private static List<String> actualIssuesList = new ArrayList<>();


    @Override
    public void onStart(ITestContext context) {
        //читаем параметры из тестсьюта
//        runMode = context.getSuite().getParameter("runMode");
        testrun = context.getSuite().getParameter("testrun");
        rerunFailedTestsOnly = Boolean.parseBoolean(context.getSuite().getParameter("rerunFailedTestsOnly"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        //получаем класс теста
        Class testClass = result.getInstance().getClass();
        //получаем номер тесткейса из аннотации @Link
        String testcase = AnnotationReader.readNameFromLink(testClass);

        /**
         * Проверка статуса тесткейса
         */
        //проверяем, что статус тесткейса - "Автоматизирован". если нет - пропускаем его
        String testcaseStatus = ZephyrScaleIntegration.getTestcaseStatus(testcase);
        if (!testcaseStatus.equals("Автоматизирован")) {
            skipSendingTestresult = true;
            System.out.println("Тесткейс " + testcase + " не на статусе \"Автоматизирован\". Его запуск не требуется.");
            throw new SkipException("");
        }

        /**
         * Если заполнен атрибут rerunFailedTestsOnly
         */
        if (rerunFailedTestsOnly) {
            //проверяем, присутствует ли тесткейс в тестране. если нет - пропускаем его
            boolean testcaseIsInTestrun = ZephyrScaleIntegration.isTestcaseInTestrun(testcase, testrun);
            if (!testcaseIsInTestrun) {
                skipSendingTestresult = true;
                System.out.println("Тесткейс " + testcase + " отсутствует в тестране " + testrun + ". Перезапуск не будет выполнен.");
                throw new SkipException("");
            }

            //проверяем результат выполнения тесткейса в тестране. если тесткейс на статусе "Пройден" - пропускаем его
            String testResult = ZephyrScaleIntegration.getTestresultFromTestrun(testcase, testrun);
            if (testResult.equals("Pass")) {
                skipSendingTestresult = true;
                System.out.println("Результат выполнения тесткейса " + testcase + ": " + testResult + ". Перезапуск не требуется.");
                throw new SkipException("");
            }
        }

        /**
         * Привязка незакрытых багов из последнего прогона
         */
        //проверяем, есть ли в последнем запуске тесткейса связанные таски
//        List<String> issuesList = ZephyrScaleIntegration.getIssuesFromLastTestcaseExecution(testcase);
//        for (String issue : issuesList) {
//            //проверяем тип таска. если тип "Ошибка" - проверяем его статус
//            String issueType = JiraIntegration.getIssueType(issue);
//            if (issueType.equals("Ошибка")) {
//                //проверяем статус таска. если статус "В работе" - добавляем его в список актуальных тасков
//                String issueStatus = JiraIntegration.getIssueStatus(issue);
//                if (issueStatus.equals("В работе")) {
//                    //добавляем
//                    actualIssuesList.add(issue);
//                }
//            }
//
//        }

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testStatus = "Pass";

        //получаем класс теста
        Class testClass = result.getInstance().getClass();
        //получаем номер тесткейса из аннотации @Link
        String testcase = AnnotationReader.readNameFromLink(testClass);
        //получаем владельца тесткейса из тесткейса
        String jiraUser = ZephyrScaleIntegration.getJiraUserFromTestcase(testcase);

        //строим JSON вида: src/test/java/framework.integration.jiraIntegration/testresult_sample.json
        JSONObject json = new JSONObject();
        //основное тело запроса
        json.put("status", testStatus);
        json.put("assignedTo", jiraUser);
        //связанные баги
        JSONArray issueLinks = new JSONArray();
        for(String actualUssue : actualIssuesList) {
            issueLinks.add(actualUssue);
        }
        json.put("issueLinks", issueLinks);

        //отправляем результат
//        System.out.println("Тест " + testClass.getSimpleName() + " успешно пройден!");
        ZephyrScaleIntegration.sendTestresultToTestrun(testcase, testrun, json);

        //записываем тесты с кривой аннотацией в файл
        ZephyrScaleIntegration.writeIncorrectTestsToFile(testClass, testcase, testStatus, jiraUser);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testStatus = "Fail"; //"Blocked"

        //получаем класс теста
        Class testClass = result.getInstance().getClass();
        //получаем номер тесткейса из аннотации @Link
        String testcase = AnnotationReader.readNameFromLink(testClass);
        //получаем владельца тесткейса из тесткейса
        String jiraUser = ZephyrScaleIntegration.getJiraUserFromTestcase(testcase);

        //строим JSON вида:
        //src/test/java/framework.integration.jiraIntegration.testresult_sample.json;
        /*
        {
            "status": "Fail",
                "environment": null,
                "comment": "Комментарий.",
                "assignedTo": "balashov.ilya",
                "executedBy": "balashov.ilya",
                "executionTime": 180000,
                "actualStartDate": "2021-11-26T12:22:00-0300",
                "actualEndDate": "2021-11-26T12:22:00-0300",
                "customFields": {
            "CI Server": null
        },
            "issueLinks": ["TSE-76925"]
        }
         */

        JSONObject json = new JSONObject();
        //основное тело запроса
        json.put("status", testStatus);
//        json.put("environment", "Firefox");
//        json.put("comment", "The test has failed on some automation tool procedure.");
        json.put("assignedTo", jiraUser);
//        json.put("executedBy", jiraUser);
//        json.put("executionTime", "180000");
//        json.put("actualStartDate", "2016-02-14T19:22:00-0300");
//        json.put("actualEndDate", "2016-02-15T19:22:00-0300");
        //связанные баги
//        JSONArray issueLinks = new JSONArray();
//        issueLinks.add("JQA-123");
//        issueLinks.add("JQA-456");
//        json.put("issueLinks", issueLinks);
        //шаги теста
//        JSONArray scriptResults = new JSONArray();
//        JSONObject scriptResults_obj = new JSONObject();
//        scriptResults_obj.put("index", 0);
//        scriptResults_obj.put("status", "Fail");
//        scriptResults_obj.put("comment", "This step has failed.");
//        scriptResults.add(scriptResults_obj);
//        json.put("scriptResults", scriptResults);

        //отправляем результат
//        System.out.println("Тест " + testClass.getSimpleName() + " провален!");
//        ZephyrScaleIntegration.sendTestresultToTestrun(testcase, testrun, params);
        ZephyrScaleIntegration.sendTestresultToTestrun(testcase, testrun, json);

        /**
         * собираем файл с ошибкой
         */
//        System.out.println("Ошибка: \n" + result.getThrowable());
//        FileFunctions.writeValueFile(HooksTSE_DEMO_28080.setWay(Ways.COMMON.getWay() + "\\throwable.txt"), String.valueOf(result.getThrowable()));
//        ZephyrScaleIntegration.sendAttachmentToTestresult(testcase, testrun, new File(HooksTSE_DEMO_28080.setWay(Ways.COMMON.getWay() + "\\throwable.txt")));

        //записываем тесты с кривой аннотацией в файл
        ZephyrScaleIntegration.writeIncorrectTestsToFile(testClass, testcase, testStatus, jiraUser);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        //при перезапуске упавших тестов не отправляем результат принудительно пропущенного теста
        if (skipSendingTestresult) {
            return;
        }

        String testStatus = "Not Executed";

        //получаем класс теста
        Class testClass = result.getInstance().getClass();
        //получаем номер тесткейса из аннотации @Link
        String testcase = AnnotationReader.readNameFromLink(testClass);
        //получаем владельца тесткейса из тесткейса
        String jiraUser = ZephyrScaleIntegration.getJiraUserFromTestcase(testcase);

        //строим JSON вида:
        //src/test/java/framework.integration.jiraIntegration.testresult_sample.json;
        /*
        {
            "status": "Fail",
                "environment": null,
                "comment": "Комментарий.",
                "assignedTo": "balashov.ilya",
                "executedBy": "balashov.ilya",
                "executionTime": 180000,
                "actualStartDate": "2021-11-26T12:22:00-0300",
                "actualEndDate": "2021-11-26T12:22:00-0300",
                "customFields": {
            "CI Server": null
        },
            "issueLinks": ["TSE-76925"]
        }
         */

        JSONObject json = new JSONObject();
        //основное тело запроса
        json.put("status", testStatus);
//        json.put("environment", "Firefox");
//        json.put("comment", "The test has failed on some automation tool procedure.");
        json.put("assignedTo", jiraUser);
//        json.put("executedBy", jiraUser);
//        json.put("executionTime", "180000");
//        json.put("actualStartDate", "2016-02-14T19:22:00-0300");
//        json.put("actualEndDate", "2016-02-15T19:22:00-0300");
        //связанные баги
//        JSONArray issueLinks = new JSONArray();
//        issueLinks.add("JQA-123");
//        issueLinks.add("JQA-456");
//        json.put("issueLinks", issueLinks);
        //шаги теста
//        JSONArray scriptResults = new JSONArray();
//        JSONObject scriptResults_obj = new JSONObject();
//        scriptResults_obj.put("index", 0);
//        scriptResults_obj.put("status", "Fail");
//        scriptResults_obj.put("comment", "This step has failed.");
//        scriptResults.add(scriptResults_obj);
//        json.put("scriptResults", scriptResults);

        //отправляем результат
//        System.out.println("Тест " + testClass.getSimpleName() + " пропущен!");
        ZephyrScaleIntegration.sendTestresultToTestrun(testcase, testrun, json);

        //записываем тесты с кривой аннотацией в файл
        ZephyrScaleIntegration.writeIncorrectTestsToFile(testClass, testcase, testStatus, jiraUser);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //
    }

    @Override
    public void onFinish(ITestContext context) {
        //
    }

}
