package edu.nixan.ask.tests.students.registration.negative;

import edu.nixan.ask.model.Signup;
import edu.nixan.ask.model.StatusResponse;
import edu.nixan.ask.tests.students.registration.base.BaseNegativeRegistrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Tag("negative")
public class StudentPasswordNegativeRegistrationTest extends BaseNegativeRegistrationTest {

    @BeforeEach
    public void prepareRequest() {
        request = Signup.builder()
                .email("test%s@test.com".formatted(System.currentTimeMillis()))
                .name("Test Test")
                .group("test")
                .build();
    }

    @Test
    @DisplayName("Should fail to register when 'password' is missing")
    void student_shouldFailToRegister_whenPasswordIsMissing() {
        StatusResponse response = register(request);

        final String errorMessage = "Missing body parameter: password";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail to register when 'password' is null")
    void student_shouldFailToRegister_whenPasswordIsNull() {
        Map<String, Object> requestBody = convertToMap(request);
        requestBody.put("password", null);
        StatusResponse response = register(requestBody);

        final String errorMessage = "Missing body parameter: password";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail to register when 'password' is empty")
    void student_shouldFailToRegister_whenPasswordIsEmpty() {
        StatusResponse response = register(request.setPassword(""));

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
    void student_shouldFailToRegister_whenPasswordContainsOnlySpaces(String blankPassword) {
        StatusResponse response = register(request.setPassword(blankPassword));

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
    void student_shouldFailToRegister_whenPasswordContainsWhiteSpaces(String password) {
        StatusResponse response = register(request.setPassword(password));

        final String errorMessage = "Password cannot contain white spaces";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail to register when 'password' is shorter than 5 characters")
    void student_shouldFailToRegister_whenPasswordIsShorterThan5Characters() {
        StatusResponse response = register(request.setPassword("pass"));

        final String errorMessage = "Data too short for column 'password'";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail to register when 'password' is longer than 32 characters")
    void student_shouldFailToRegister_whenPasswordIsLongerThan256Characters() {
        StatusResponse response = register(request.setPassword("passwordpasswordpasswordpasswordp"));

        final String errorMessage = "Data too long for column 'password'";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail to register when 'password' contains only numbers as not a string")
    void student_shouldFailToRegister_whenPasswordContainsOnlyNumbersAsNotAString() {
        Map<String, Object> requestBody = convertToMap(request);
        requestBody.put("password", 12_345_678);
        StatusResponse response = register(requestBody);

        final String errorMessage = "Data too long for column 'password'";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }
}
