package org.androsovich.applications.watchers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.Optional;

@Slf4j
public class LoggingTestWatcher implements BeforeEachCallback, TestWatcher {

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        log.info("Test-case {} : ", extensionContext.getRequiredTestMethod().getName());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        TestWatcher.super.testDisabled(context, reason);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        log.info("Test Passed : {}", context.getRequiredTestMethod().getName());
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        TestWatcher.super.testAborted(context, cause);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        log.error("Test failed {} : with exception {}", context.getRequiredTestMethod().getName(), cause.getLocalizedMessage());
    }
}
