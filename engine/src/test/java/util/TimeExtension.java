package util;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.logging.Logger;

/**
 * @author artrayme
 * @since 0.0.1
 */
public class TimeExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Logger logger = Logger.getLogger(TimeExtension.class.getName());

    private static final String START_TIME = "start time";
    private static final String START_REQUEST_COUNT = "start count";

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        getStore(context).put(
                START_TIME,
                System.currentTimeMillis());

    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        Method testMethod = context.getRequiredTestMethod();
        long startTime = getStore(context).remove(
                START_TIME,
                long.class);
        long duration = System.currentTimeMillis() - startTime;
        logger.info(() -> String.format("Method [%s]: time = %s ms",
                testMethod.getName(),
                duration
        ));
    }

    private ExtensionContext.Store getStore(ExtensionContext context) {
        return context.getStore(ExtensionContext.Namespace.create(
                getClass(),
                context.getRequiredTestMethod()));
    }

}