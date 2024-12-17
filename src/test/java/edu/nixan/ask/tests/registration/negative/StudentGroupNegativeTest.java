package edu.nixan.ask.tests.registration.negative;

import edu.nixan.ask.model.Signup;
import edu.nixan.ask.model.StatusResponse;
import edu.nixan.ask.spec.Specification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class StudentGroupTest {

    private final static String BASE_URL = "http://ask-qa.portnov.com/api/v1";
    private final static String ERROR_STATUS = "error";

    private Signup request;

    @BeforeAll
    public static void init() {
        Specification.installSpecifications(
                Specification.requestSpec(BASE_URL), Specification.responseSpecError400());
    }

    @BeforeEach
    public void createRequest() {
        request = Signup.builder()
                .email("test%s@test.com".formatted(System.currentTimeMillis()))
                .name("Test Test")
                .password("ABC123")
                .build();
    }

    @Test
    @DisplayName("Should fail registration when 'group' is missing")
    public void student_shouldFailRegistration_whenGroupIsMissing() {
        StatusResponse response = given()
                .log().all()
                .body(request)
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Missing body parameter: group";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail registration when 'group' is null")
    public void student_shouldFailRegistration_whenGroupIsNull() {
        String requestBody = """
                    {
                        "email": "john%s@doe.com",
                        "name": Test Test,
                        "password": "ABC123",
                        "group": null
                    }
                """.formatted(System.currentTimeMillis());

        StatusResponse response = given()
                .log().all()
                .body(requestBody)
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Missing body parameter: group";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @Test
    @DisplayName("Should fail registration when 'group' is empty")
    public void student_shouldFailRegistration_whenGroupIsEmpty() {
        StatusResponse response = given()
                .log().all()
                .body(request.setGroup(""))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Missing body parameter: group";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "     "})
    @DisplayName("Should fail registration when 'group' contains only spaces")
    public void student_shouldFailRegistration_whenGroupContainsOnlySpaces(String blankGroup) {
        StatusResponse response = given()
                .log().all()
                .body(request.setGroup(blankGroup))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Missing body parameter: group";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST ", " TEST", "TE ST"})
    @DisplayName("Should fail registration when 'group' contains white spaces")
    public void student_shouldFailRegistration_whenGroupContainsWhiteSpaces() {
        StatusResponse response = given()
                .log().all()
                .body(request.setGroup("   TEST"))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Group cannot contain spaces";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "abcdefghigh",
            "rmvpekrfkdkoaykqqmshqhayjyqyvuixclzncbzijmtemkbibrobocejxqypbsbrcoqmqbyqpjdiwrqefsjmonlqhrcjehystrzdjrvtxbuajmccqgrkmjbooktsmoihfynspyaudxouewqexpwjczdqsicdccsutmedeforoctanhjwroqnuwbgqnidbtvcdgjkjmxdzaxlfphddwfqztknjvfbbxqedvsbseidkcclngkzkuofnpsunpaucfqpy"
    })
    @DisplayName("Should fail registration when 'group' is longer than 10 characters")
    public void student_shouldFailRegistration_whenGroupIsLongerThan256Characters(String group) {
        StatusResponse response = given()
                .log().all()
                .body(request.setGroup(group))
                .when()
                .post("/sign-up")
                .then().log().all()
                .extract().as(StatusResponse.class);

        final String errorMessage = "Data too long for column 'group'";
        assertAll("Error response validation",
                () -> assertNotNull(response, "Response should not be null"),
                () -> assertEquals(ERROR_STATUS, response.getStatus(), "Response status should be '%s'".formatted(ERROR_STATUS)),
                () -> assertEquals(errorMessage, response.getMessage(), "Error message should be '%s'".formatted(errorMessage))
        );
    }
}
