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
public class StudentNamePositiveRegistrationTest extends BasePositiveRegistrationTest {

    private Signup request;

    @BeforeEach
    public void prepareRequest() {
        request = Signup.builder()
                .email("test%s@test.com".formatted(System.currentTimeMillis()))
                .password("ABC123")
                .group("test")
                .build();
    }

    @Test
    @DisplayName("Should register successfully when 'name' contains only numbers")
    void student_shouldRegisterSuccessfully_whenNameContainsOnlyNumbers() {
        StatusResponse response = register(request.setName("1234 4567"));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @Test
    @DisplayName("Should register successfully when 'name' contains only special characters")
    void student_shouldRegisterSuccessfully_whenNameContainsOnlySpecialCharacters() {
        StatusResponse response = register(request.setName("1@! #%"));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"John Doe", "john doe", "JOHN DOE"})
    @DisplayName("Should register successfully when 'name' contains only alphabetic characters")
    void student_shouldRegisterSuccessfully_whenNameContainsOnlyAlphabeticCharacters(String name) {
        StatusResponse response = register(request.setName(name));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @Test
    @DisplayName("Should register successfully when 'name' contains minimum of 3 characters")
    void student_shouldRegisterSuccessfully_whenNameContainsMinimumOf3Characters() {
        StatusResponse response = register(request.setName("J D"));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmoihfynspyaudxouewqexpwjczdqsicdccsutmedeforoctanhjwroqnuwbgqnidbtvcdgjkjmxdzaxlfphddwfqztknjvfbbxqedvsbseidkcclngkzkuofnpsunpaucf D",
            "J rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmoihfynspyaudxouewqexpwjczdqsicdccsutmedeforoctanhjwroqnuwbgqnidbtvcdgjkjmxdzaxlfphddwfqztknjvfbbxqedvsbseidkcclngkzkuofnpsunpaucf",
            "rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmow rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmows",
            "rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmows rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmow"
    })
    @DisplayName("Should register successfully when 'name' contains maximum of 256 characters")
    void student_shouldRegisterSuccessfully_whenNameContainsMaximumOf256Characters(String name) {
        StatusResponse response = register(request.setName(name));

        assertAll("Success response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(STATUS, response.getStatus(), "Response status should be '%s'".formatted(STATUS)),
                () -> assertEquals(MESSAGE, response.getMessage(), "Successful message should be '%s'".formatted(MESSAGE))
        );
    }
}
