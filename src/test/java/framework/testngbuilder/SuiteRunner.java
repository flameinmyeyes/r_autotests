package framework.testngbuilder;

import org.testng.TestNG;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.xml.XmlSuite;

import java.util.*;

import static org.testng.xml.XmlSuite.ParallelMode.TESTS;

@Deprecated
public class SuiteRunner {

    public static String SUITE_PATH = null;
    public static String RUN_MODE = "docker";

    @Parameters({"suitePath"})
    @Test
    public static void main(String suitePath) {
        SUITE_PATH = suitePath;
        System.out.println("Выполняется запуск тест-сьюта: " + SUITE_PATH);

        TestNG testng = new TestNG();

        //читаем запускаемый сьют
        XmlSuite suite = new XmlSuite();
        suite.setSuiteFiles(Arrays.asList(SUITE_PATH));
        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        testng.setXmlSuites(suites);

        //назначаем тип запуска в запускаемом сьюте
        Map<String, String> parameters = new HashMap<>();
        parameters.put("runMode", RUN_MODE);
        suite.setParameters(parameters);

        //запускаем сьют
        testng.run();
    }

}
