package edu.nixan.ask.tests.registration.positive;

import edu.nixan.ask.model.Login;
import edu.nixan.ask.model.Signup;
import edu.nixan.ask.model.StatusResponse;
import edu.nixan.ask.spec.Specification;
import edu.nixan.ask.util.Jdbc;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class StudentPasswordTest {

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
                .group("test")
                .build();
    }

    @AfterEach
    public void deleteStudent() {
        Integer studentId = Jdbc.fetchUserId(request.getEmail());
        String activationCode = Jdbc.fetchUserActivationCode(request.getEmail());

        
        activateStudentAccount(studentId, activationCode);
        deleteStudentAccount(studentId);
    }

    private void activateStudentAccount(Integer studentId, String activationCode) {
        given()
                .log().all()
                .when()
                .get("/activate/%s/%s".formatted(studentId, activationCode))
                .then().log().all();
    }

    private void deleteStudentAccount(Integer studentId) {
        String token = fetchTeacherToken();
        given()
                .auth().oauth2(token)
                .when()
                .delete(String.format("/users/%d", studentId))
                .then()
                .statusCode(200); // Ensure deletion is successful
    }

    private String fetchTeacherToken() {
        return given()
                .log().all()
                .body(Login.builder()
                        .email("qa1@test.com")
                        .password("ABC123").build())
                .when()
                .post("/sign-in")
                .then().log().all()
                .extract().path("token");
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
