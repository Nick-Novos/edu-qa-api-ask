package edu.nixan.ask.tests.students.registration.positive;

import edu.nixan.ask.model.Signup;
import edu.nixan.ask.model.StatusResponse;
import edu.nixan.ask.tests.students.registration.base.BasePositiveRegistrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@Tag("positive")
public class StudentPasswordPositiveRegistrationTest extends BasePositiveRegistrationTest {

    @BeforeEach
    public void prepareRequest() {
        request = Signup.builder()
                .email("test%s@test.com".formatted(System.currentTimeMillis()))
                .name("John Doe")
                .group("test")
                .build();
    }

    @Test
    @DisplayName("Should register successfully when 'password' contains only numbers")
    void student_shouldRegisterSuccessfully_whenPasswordContainsOnlyNumbers() {
        StatusResponse response = register(request.setPassword("123456"));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @Test
    @DisplayName("Should register successfully when 'group' contains only special characters")
    void student_shouldRegisterSuccessfully_whenPasswordContainsOnlySpecialCharacters() {
        StatusResponse response = register(request.setPassword("!@#$%^"));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"Password", "password", "PASSWORD"})
    @DisplayName("Should register successfully when 'password' contains only alphabetic characters")
    void student_shouldRegisterSuccessfully_whenPasswordContainsOnlyAlphabeticCharacters(String password) {
        StatusResponse response = register(request.setPassword(password));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @Test
    @DisplayName("Should register successfully when 'password' contains a combination of alphabetic, numeric, and special characters")
    void student_shouldRegisterSuccessfully_whenPasswordContainsACombinationOfAlphabeticAndNumericAndSpecialCharacters() {
        StatusResponse response = register(request.setPassword("abc123!@#"));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @Test
    @DisplayName("Should register successfully when 'password' contains minimum of 5 character")
    void student_shouldRegisterSuccessfully_whenPasswordContainsMinimumOf1Character() {
        StatusResponse response = register(request.setPassword("passw"));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @Test
    @DisplayName("Should register successfully when 'password' contains maximum of 32 characters")
    void student_shouldRegisterSuccessfully_whenPasswordContainsMaximumOf32Characters() {
        StatusResponse response = register(request.setPassword("passwordpasswordpasswordpassword"));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }
}
