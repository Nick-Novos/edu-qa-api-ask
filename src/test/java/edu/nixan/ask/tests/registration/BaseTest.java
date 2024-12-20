package edu.nixan.ask.tests.registration;

import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public abstract class BaseTest {

    protected static final String BASE_URL = "http://ask-qa.portnov.com/api/v1";

    private final ByteArrayOutputStream requestLogs = new ByteArrayOutputStream();
    private final ByteArrayOutputStream responseLogs = new ByteArrayOutputStream();

    @BeforeEach
    public void beforeEach() {
        RestAssured.filters(
                new RequestLoggingFilter(new PrintStream(requestLogs)),
                new ResponseLoggingFilter(new PrintStream(responseLogs))
        );
    }

    @AfterEach
    public void attachLogs() {
        Allure.addAttachment("Request Logs", requestLogs.toString());
        Allure.addAttachment("Response Logs", responseLogs.toString());
    }

    @AfterEach
    protected void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract void createRequest();
}
