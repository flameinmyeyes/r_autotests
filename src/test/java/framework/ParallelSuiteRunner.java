package framework;

import org.testng.ITestNGListener;
import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

@Deprecated
public class ParallelSuiteRunner {

    public static String SUITE_OF_SUITES_PATH = "testng.xml";

    private static String[] suiteFiles = {
            "smoke_tests.xml",
            "smoke_tests_2.xml"
//            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Balashov.xml",
//            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Kamaev.xml",
//            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Kaverina.xml",
//            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Maksimova.xml",
//            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Osterov.xml",
//            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Vlasovets.xml",
//            "test_suites/eb_tse_demo_ufos_28080/TSE_all_tests_Yakubov.xml"
    };

    private static int suiteThreadPoolSize;//

//    public static void main(String[] args) {
//        suiteThreadPoolSize = suiteFiles.length;
//        System.out.println("Число тест-сьютов: " + suiteThreadPoolSize);
//
//        TestNG testng = new TestNG();
//
//        //читаем общий сьют
//        XmlSuite suiteOfSuites = new XmlSuite();
//        suiteOfSuites.setSuiteFiles(Arrays.asList("test_suites/TSE/TSE_all_tests_Balashov.xml"));
////        System.out.println("log1: " + suiteOfSuites.getName());
//
//        //читаем запускаемый сьют
//        //1. через setTestSuites
////        List<String> suites = new ArrayList<String>();
////        for (String suiteFile:suiteFiles) {
////            suites.add(suiteFile);
////        }
////        testng.setTestSuites(suites);
//
//        //2. через setXmlSuites
//        XmlSuite suite = new XmlSuite();
//        suite.setSuiteFiles(Arrays.asList(suiteFiles));
//        List<XmlSuite> suites = new ArrayList<XmlSuite>();
//        suites.add(suite);
////        suite.setParallel(TESTS); //хз
//        testng.setXmlSuites(suites);
//
//        //читаем содержимое
////        suite.getTest();
////        System.out.println("info: " + suite.getChildSuites());
//
//
//        //программно составляем общий тест сьют
//
//        //ставим число потоков
//        testng.setSuiteThreadPoolSize(suiteThreadPoolSize);
////        testng.setThreadCount(suiteThreadPoolSize);
//
//        //запускаем
//        testng.run();
//    }

    public static void main(String[] args) {
        TestNG testng = new TestNG();

        ITestNGListener listener = new ITestNGListener() {
        };
        testng.addListener(listener);
//        TestListenerAdapter adapter = new TestListenerAdapter();
//        testng.addListener(adapter);

        List<String> suites = new ArrayList<String>();
        for (String suiteFile:suiteFiles) {
            suites.add(suiteFile);
        }
//        suites.add("testng.xml");

        suiteThreadPoolSize = suiteFiles.length;
        System.out.println("Число тест-сьютов: " + suiteThreadPoolSize);

        testng.setTestSuites(suites);
        testng.setParallel("parallel");
        testng.setSuiteThreadPoolSize(suiteThreadPoolSize);
        testng.setOutputDirectory(System.getProperty("user.dir")+"//target");
        testng.run();
    }

}
