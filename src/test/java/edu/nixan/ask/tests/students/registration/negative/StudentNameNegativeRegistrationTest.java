package edu.nixan.ask.tests.students.registration.negative;

import edu.nixan.ask.model.Signup;
import edu.nixan.ask.model.StatusResponse;
import edu.nixan.ask.tests.students.registration.base.BaseNegativeRegistrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Tag("negative")
public class StudentNameNegativeRegistrationTest extends BaseNegativeRegistrationTest {

    @BeforeEach
    public void prepareRequest() {
        request = Signup.builder()
                .email("test%s@test.com".formatted(System.currentTimeMillis()))
                .password("ABC123")
                .group("test")
                .build();
    }

    @Test
    @DisplayName("Should fail registration when 'name' is missing")
    void student_shouldFailToRegister_whenNameIsMissing() {
        StatusResponse response = register(request);

        final String errorMessage = "Missing body parameter: name";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail to register when 'name' is null")
    void student_shouldFailToRegister_whenNameIsNull() {
        Map<String, Object> requestBody = convertToMap(request);
        requestBody.put("name", null);
        StatusResponse response = register(requestBody);

        final String errorMessage = "Missing body parameter: name";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail to register when 'name' is empty")
    void student_shouldFailToRegister_whenNameIsEmpty() {
        StatusResponse response = register(request.setName(""));

        final String errorMessage = "Missing body parameter: name";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "     "})
    @DisplayName("Should fail to register when 'name' contains only spaces")
    void student_shouldFailToRegister_whenNameContainsOnlySpaces(String blankName) {
        StatusResponse response = register(request.setName(blankName));

        final String errorMessage = "Name cannot contain spaces";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"John   Doe", " John Doe", "John Doe "})
    @DisplayName("Should fail to register when 'name' contains extra spaces")
    void student_shouldFailToRegister_whenNameContainsExtraSpaces(String name) {
        StatusResponse response = register(request.setName(name));

        final String errorMessage = "Name cannot contain spaces";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @ParameterizedTest
    @CsvSource({
            "john, Missing last name",
            "JohnDoe, Missing last name",
            "' JohnDoe', Name cannot contain spaces",
            "'JohnDoe ', Name cannot contain spaces"
    })
    @DisplayName("Should fail to register when 'name' does not contain white space between first and last name")
    void student_shouldFailToRegister_whenNameDoesNotContainWhiteSpaceBetweenFirstAndLastName(String name, String expectedErrorMessage) {
        StatusResponse response = register(request.setName(name));

        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(expectedErrorMessage, response.getMessage(), "Error message should be '%s'".formatted(expectedErrorMessage))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"J", "JD", "J ", " D"})
    @DisplayName("Should fail to register when 'name' is shorter than 3 characters")
    void student_shouldFailToRegister_whenNameIsShorterThan3Characters(String name) {
        StatusResponse response = register(request.setName(name));

        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmoihfynspyaudxouewqexpwjczdqsicdccsutmedeforoctanhjwroqnuwbgqnidbtvcdgjkjmxdzaxlfphddwfqztknjvfbbxqedvsbseidkcclngkzkuofnpsunpaucfq D",
            "J rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmoihfynspyaudxouewqexpwjczdqsicdccsutmedeforoctanhjwroqnuwbgqnidbtvcdgjkjmxdzaxlfphddwfqztknjvfbbxqedvsbseidkcclngkzkuofnpsunpaucfq",
            "rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmoihfynspyaudxouewqexpwjczdqsicdccsutmedeforoctanhjwroqnuwbgqnidbtvcdgjkjmxdzaxlfphddwfqztknjvfbbxqedvsbseidkcclngkzkuofnpsunpaucf Do",
            "Jo rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmoihfynspyaudxouewqexpwjczdqsicdccsutmedeforoctanhjwroqnuwbgqnidbtvcdgjkjmxdzaxlfphddwfqztknjvfbbxqedvsbseidkcclngkzkuofnpsunpaucf",
            "rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmows rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmows"
    })
    @DisplayName("Should fail to register when 'name' is longer than 256 characters")
    void student_shouldFailToRegister_whenNameIsLongerThan256Characters(String name) {
        StatusResponse response = register(request.setName(name));

        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS))
        );
    }
}
