package edu.nixan.ask.tests.base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Allure;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static io.restassured.RestAssured.given;

public abstract class BaseTest {

    protected static final String BASE_URL = "http://ask-qa.portnov.com/api/v1";

    protected final ObjectMapper objectMapper;
    protected final UserRepository userRepository;

    private ByteArrayOutputStream requestLogs;
    private ByteArrayOutputStream responseLogs;
    private RequestLoggingFilter requestLoggingFilter;
    private ResponseLoggingFilter responseLoggingFilter;

    protected BaseTest() {
        objectMapper = new ObjectMapper();
        userRepository = new UserRepository();
    }

    protected abstract void prepareRequest();

    @BeforeEach
    void setupFilters() {
        requestLogs = new ByteArrayOutputStream();
        responseLogs = new ByteArrayOutputStream();
        requestLoggingFilter = new RequestLoggingFilter(new PrintStream(requestLogs));
        responseLoggingFilter = new ResponseLoggingFilter(new PrintStream(responseLogs));
    }

    @AfterEach
    public void attachLogs() {
        Allure.addAttachment("Request Logs", requestLogs.toString());
        Allure.addAttachment("Response Logs", responseLogs.toString());
    }

    protected Map<String, Object> convertToMap(Object object) {
        return objectMapper.convertValue(object, new TypeReference<>() {
        });
    }

    protected <T> T post(String path, Object body, Class<T> response,
                         RequestSpecification requestSpec, ResponseSpecification responseSpec) {
        return given()
                .log().all()
                .filter(requestLoggingFilter)
                .filter(responseLoggingFilter)
                .spec(requestSpec)
                .body(body)
                .when()
                .post(path)
                .then()
                .log().all()
                .spec(responseSpec)
                .extract().as(response);
    }
}
