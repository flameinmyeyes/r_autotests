package framework.integration.zephyrScaleIntegration;

import io.qameta.allure.Link;
import io.qameta.allure.Owner;

import java.lang.reflect.Method;

public class AnnotationReader {
    
    public static String readNameFromLink(Class testClass) {
        String str = null;
        Method[] methods = testClass.getMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Link.class)) {
                Link ta = m.getAnnotation(Link.class);
                str = ta.name();
//                System.out.println("annotation value: " + value);
            }
        }
        return str;
    }

    public static String readValueFromOwner(Class testClass) {
        String str = null;
        Method[] methods = testClass.getMethods();
        for (Method m : methods) {
            if (m.isAnnotationPresent(Owner.class)) {
                Owner ta = m.getAnnotation(Owner.class);
                str = ta.value();
//                System.out.println("annotation value: " + value);
            }
        }
        return str;
    }

}
