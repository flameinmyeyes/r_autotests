package framework.testngbuilder;

import framework.Ways;
import org.apache.commons.io.FileUtils;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.testng.xml.XmlSuite.ParallelMode.TESTS;

@Deprecated
public class SuiteBuilderAndRunner {

    public static List<XmlTest> testsList = new ArrayList<XmlTest>();
    public static int suiteThreadPoolSize;
    public static String[] suiteFiles = {
            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Balashov_1.xml",
            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Balashov_2.xml",
            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Kamaev_1.xml",
            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Kamaev_2.xml",
//            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Kaverina.xml",
            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Maksimova_1.xml",
            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Maksimova_2.xml",
//            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Osterov.xml",
            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Vlasovets_1.xml",
            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Vlasovets_2.xml",
            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Vorozhko_1.xml",
            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Vorozhko_2.xml",
            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Yakubov_1.xml",
            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Yakubov_2.xml"
    };

    public static void main(String[] args)  {
        suiteThreadPoolSize = suiteFiles.length;
        System.out.println("Число тест-сьютов: " + suiteThreadPoolSize);

        //1. Читаем пользовательские сьюты:

        for (String suiteFile:suiteFiles) {
            File file = new File(suiteFile);
            InputStream inputStream = null;
            List<XmlSuite> suites_old = null;
            try {
                inputStream = FileUtils.openInputStream(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parser parser = new Parser(inputStream);
            try {
                suites_old = parser.parseToList();
            } catch (IOException e) {
                e.printStackTrace();
            }

            XmlSuite suite_old = suites_old.get(0);

            //атрибуты сьюта
            suite_old.setParallel(TESTS);
            suite_old.setVerbose(1);
            suite_old.setPreserveOrder(true);
            suite_old.setThreadCount(suiteThreadPoolSize);
            Map<String, String> parameters_new = new HashMap<>();
            parameters_new.put("runMode", "local");
            suite_old.setParameters(parameters_new);

            List<XmlTest> tests_old = suite_old.getTests();
            XmlTest test_old = tests_old.get(0);
            testsList.add(test_old);
        }

        //2. Программно создаем новый общий сьют

        XmlSuite suite_new = new XmlSuite();
        suite_new.setName("TSE_all_tests");

        //атрибуты сьюта
        suite_new.setParallel(TESTS);
        suite_new.setVerbose(1);
        suite_new.setPreserveOrder(true);
        suite_new.setThreadCount(suiteThreadPoolSize);
        Map<String, String> parameters_new = new HashMap<>();
        parameters_new.put("runMode", "local");
        suite_new.setParameters(parameters_new);

        for (XmlTest test:testsList) {
            suite_new.addTest(test);
        }

        //классы
//        List<XmlClass> classes = new ArrayList<XmlClass>();
//        classes.add(new XmlClass("ru.otr.eb_tse_demo_ufos_28080.FUN_01.BP_002.FUN_01_BP_002_MT_1_KP_1"));
//        test.setXmlClasses(classes);

//        createXmlFile(suite_new, Ways.TSE_DEMO_28080.getWay() + "\\suite_new.xml");

        //3. Запускаем
        List<XmlSuite> suites_new = new ArrayList<XmlSuite>();
        suites_new.add(suite_new);

        TestNG testng = new TestNG();

        testng.setXmlSuites(suites_new);
        testng.setSuiteThreadPoolSize(suiteThreadPoolSize);
        testng.setThreadCount(suiteThreadPoolSize);
        testng.run();

    }

    //This method will create an Xml file based on the XmlSuite data
    public static void createXmlFile(XmlSuite mSuite, String wayToSaveFile) {
        FileWriter writer;
        try {
            writer = new FileWriter(new File(wayToSaveFile));
            writer.write(mSuite.toXml());
            writer.flush();
            writer.close();
            System.out.println(new File(wayToSaveFile).getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
