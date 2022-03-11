package jiraIntegration.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Deprecated
@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptMetaData {
    boolean productionReady();
}
