package edu.nixan.ask.tests.registration.positive;

import edu.nixan.ask.model.Signup;
import edu.nixan.ask.model.StatusResponse;
import edu.nixan.ask.spec.Specification;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class StudentGroupTest {

    private final static String BASE_URL = "http://ask-qa.portnov.com/api/v1";
    private final static String STATUS = "success";
    private final static String MESSAGE = "User was created";

    private Signup request;

    @BeforeAll
    public static void init() {
        Specification.installSpecifications(
                Specification.requestSpec(BASE_URL), Specification.responseSpecOK200());
    }

    @BeforeEach
    public void createRequest() {
        request = Signup.builder()
                .email("test%s@test.com".formatted(System.currentTimeMillis()))
                .name("John Doe")
                .password("ABC123")
                .build();
    }

    @AfterEach
    public void deleteStudent() {
        // TODO
    }

    @Test
    @DisplayName("Should register successfully when 'group' contains only numbers as a string")
    public void student_shouldRegisterSuccessfully_whenGroupContainsOnlyNumbersAsAString() {
        StatusResponse response = given()
                .log().all()
                .body(request.setGroup("12345"))
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
    @DisplayName("Should register successfully when 'group' contains only numbers")
    public void student_shouldRegisterSuccessfully_whenGroupContainsOnlyNumbers() {
        String requestBody = """
                    {
                        "email": "john%s@doe.com",
                        "name": Test Test,
                        "password": "ABC123",
                        "group": 12345
                    }
                """.formatted(System.currentTimeMillis());

        StatusResponse response = given()
                .log().all()
                .body(requestBody)
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
    public void student_shouldRegisterSuccessfully_whenGroupContainsOnlySpecialCharacters() {
        StatusResponse response = given()
                .log().all()
                .body(request.setGroup("!@#$"))
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
    @ValueSource(strings = {"Test", "test", "TEST"})
    @DisplayName("Should register successfully when 'group' contains only alphabetic characters")
    public void student_shouldRegisterSuccessfully_whenGroupContainsOnlyAlphabeticCharacters(String group) {
        StatusResponse response = given()
                .log().all()
                .body(request.setGroup(group))
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
    @DisplayName("Should register successfully when 'group' contains a combination of alphabetic, numeric, and special characters")
    public void student_shouldRegisterSuccessfully_whenGroupContainsACombinationOfAlphabeticAndNumericAndSpecialCharacters() {
        StatusResponse response = given()
                .log().all()
                .body(request.setGroup("abc123!@#"))
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
    @DisplayName("Should register successfully when 'group' contains minimum of 1 character")
    public void student_shouldRegisterSuccessfully_whenGroupContainsMinimumOf1Character() {
        StatusResponse response = given()
                .log().all()
                .body(request.setGroup("T"))
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
    @DisplayName("Should register successfully when 'group' contains maximum of 10 characters")
    public void student_shouldRegisterSuccessfully_whenGroupContainsMaximumOf10Characters() {
        StatusResponse response = given()
                .log().all()
                .body(request.setGroup("abcdefghig"))
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
