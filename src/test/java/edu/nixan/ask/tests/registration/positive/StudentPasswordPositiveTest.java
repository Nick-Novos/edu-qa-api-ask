package edu.nixan.ask.tests.registration.positive;

import edu.nixan.ask.model.Signup;
import edu.nixan.ask.model.StatusResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class StudentPasswordPositiveTest extends BasePositiveTest {

    @BeforeEach
    public void createRequest() {
        request = Signup.builder()
                .email("test%s@test.com".formatted(System.currentTimeMillis()))
                .name("John Doe")
                .group("test")
                .build();
    }

    @Test
    @DisplayName("Should register successfully when 'password' contains only numbers")
    public void student_shouldRegisterSuccessfully_whenPasswordContainsOnlyNumbers() {
        StatusResponse response = given()
                .log().all()
                .body(request.setPassword("123456"))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @Test
    @DisplayName("Should register successfully when 'group' contains only special characters")
    public void student_shouldRegisterSuccessfully_whenPasswordContainsOnlySpecialCharacters() {
        StatusResponse response = given()
                .log().all()
                .body(request.setPassword("!@#$%^"))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"Password", "password", "PASSWORD"})
    @DisplayName("Should register successfully when 'password' contains only alphabetic characters")
    public void student_shouldRegisterSuccessfully_whenPasswordContainsOnlyAlphabeticCharacters(String password) {
        StatusResponse response = given()
                .log().all()
                .body(request.setPassword(password))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @Test
    @DisplayName("Should register successfully when 'password' contains a combination of alphabetic, numeric, and special characters")
    public void student_shouldRegisterSuccessfully_whenPasswordContainsACombinationOfAlphabeticAndNumericAndSpecialCharacters() {
        StatusResponse response = given()
                .log().all()
                .body(request.setPassword("abc123!@#"))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @Test
    @DisplayName("Should register successfully when 'password' contains minimum of 5 character")
    public void student_shouldRegisterSuccessfully_whenPasswordContainsMinimumOf1Character() {
        StatusResponse response = given()
                .log().all()
                .body(request.setPassword("passw"))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @Test
    @DisplayName("Should register successfully when 'password' contains maximum of 32 characters")
    public void student_shouldRegisterSuccessfully_whenPasswordContainsMaximumOf32Characters() {
        StatusResponse response = given()
                .log().all()
                .body(request.setPassword("passwordpasswordpasswordpassword"))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }
}
