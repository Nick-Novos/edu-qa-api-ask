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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Tag("positive")
public class StudentGroupPositiveRegistrationTest extends BasePositiveRegistrationTest {

    @BeforeEach
    public void prepareRequest() {
        request = Signup.builder()
                .email("test%s@test.com".formatted(System.currentTimeMillis()))
                .name("John Doe")
                .password("ABC123")
                .build();
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345"})
    @DisplayName("Should register successfully when 'group' contains only numbers as a string")
    void student_shouldRegisterSuccessfully_whenGroupContainsOnlyNumbersAsAString(String group) {
        StatusResponse response = register(request.setGroup(group));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 12345, Long.MAX_VALUE, 9_999_999_999L})
    @DisplayName("Should register successfully when 'group' contains only digits")
    void student_shouldRegisterSuccessfully_whenGroupContainsOnlyDigits(long digits) {
        Map<String, Object> requestBody = convertToMap(request);
        requestBody.put("group", digits);
        StatusResponse response = register(requestBody);

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"!@#$", "&", "!@#$%^&*()"})
    @DisplayName("Should register successfully when 'group' contains only special characters")
    void student_shouldRegisterSuccessfully_whenGroupContainsOnlySpecialCharacters(String group) {
        StatusResponse response = register(request.setGroup(group));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"Test", "test", "TEST", "tEsT"})
    @DisplayName("Should register successfully when 'group' contains only alphabetic characters")
    void student_shouldRegisterSuccessfully_whenGroupContainsOnlyAlphabeticCharacters(String group) {
        StatusResponse response = register(request.setGroup(group));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"aBc123!@#", "123!@#", "aBc!@#", "aBc123", "!@#123aBc"})
    @DisplayName("Should register successfully when 'group' contains a combination of alphabetic, numeric, and special characters")
    void student_shouldRegisterSuccessfully_whenGroupContainsACombinationOfAlphabeticAndNumericAndSpecialCharacters(String group) {
        StatusResponse response = register(request.setGroup(group));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"T"})
    @DisplayName("Should register successfully when 'group' contains minimum of 1 character")
    void student_shouldRegisterSuccessfully_whenGroupContainsMinimumOf1Character(String group) {
        StatusResponse response = register(request.setGroup(group));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @Test
    @DisplayName("Should register successfully when 'group' contains maximum of 10 characters")
    void student_shouldRegisterSuccessfully_whenGroupContainsMaximumOf10Characters() {
        StatusResponse response = register(request.setGroup("aBc1234!@#"));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }
}
