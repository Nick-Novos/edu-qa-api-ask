package edu.nixan.ask.tests.registration.negative;

import edu.nixan.ask.model.Signup;
import edu.nixan.ask.model.StatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class StudentPasswordNegativeTest extends BaseNegativeTest {

    @BeforeEach
    public void createRequest() {
        request = Signup.builder()
                .email("test%s@test.com".formatted(System.currentTimeMillis()))
                .name("Test Test")
                .group("test")
                .build();
    }

    @Test
    @DisplayName("Should fail to register when 'password' is missing")
    public void student_shouldFailToRegister_whenPasswordIsMissing() {
        StatusResponse response = given()
                .log().all()
                .body(request)
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Missing body parameter: password";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail to register when 'password' is null")
    public void student_shouldFailToRegister_whenPasswordIsNull() {
        String requestBody = """
                {
                    "email": "john%s@doe.com",
                    "name": "Test Test",
                    "password": null,
                    "group": "test"
                }
                """.formatted(System.currentTimeMillis());

        StatusResponse response = given()
                .log().all()
                .body(requestBody)
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Missing body parameter: password";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail to register when 'password' is empty")
    public void student_shouldFailToRegister_whenPasswordIsEmpty() {
        StatusResponse response = given()
                .log().all()
                .body(request.setPassword(""))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Missing body parameter: password";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "        "})
    @DisplayName("Should fail to register when 'password' contains only spaces")
    public void student_shouldFailToRegister_whenPasswordContainsOnlySpaces(String blankPassword) {
        StatusResponse response = given()
                .log().all()
                .body(request.setPassword(blankPassword))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Password cannot contain white spaces";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"pass word", " password", "password "})
    @DisplayName("Should fail to register when 'password' contains white spaces")
    public void student_shouldFailToRegister_whenPasswordContainsWhiteSpaces(String password) {
        StatusResponse response = given()
                .log().all()
                .body(request.setPassword(password))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Password cannot contain white spaces";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail to register when 'password' is shorter than 5 characters")
    public void student_shouldFailToRegister_whenPasswordIsShorterThan5Characters() {
        StatusResponse response = given()
                .log().all()
                .body(request.setPassword("pass"))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Data too short for column 'password'";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail to register when 'password' is longer than 32 characters")
    public void student_shouldFailToRegister_whenPasswordIsLongerThan256Characters() {
        StatusResponse response = given()
                .log().all()
                .body(request.setPassword("passwordpasswordpasswordpasswordp"))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Data too long for column 'password'";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail to register when 'password' contains only numbers as not a string")
    public void student_shouldFailToRegister_whenPasswordContainsOnlyNumbersAsNotAString() {
        String requestBody = """
                {
                    "email": "john%s@doe.com",
                    "name": "Test Test",
                    "password": 12345678,
                    "group": "test"
                }
                """.formatted(System.currentTimeMillis());

        StatusResponse response = given()
                .log().all()
                .body(requestBody)
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Data too long for column 'password'";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }
}
