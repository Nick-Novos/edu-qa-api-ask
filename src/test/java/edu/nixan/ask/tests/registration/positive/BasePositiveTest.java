package edu.nixan.ask.tests.registration.positive;

import edu.nixan.ask.model.Login;
import edu.nixan.ask.model.Signup;
import edu.nixan.ask.spec.Specification;
import edu.nixan.ask.util.Jdbc;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public abstract class BasePositiveTest {


    static String BASE_URL = "http://ask-qa.portnov.com/api/v1";
    protected final static String STATUS = "success";
    protected final static String MESSAGE = "User was created";

    protected Signup request;

    @BeforeAll
    public static void init() {
        Specification.installSpecifications(
                Specification.requestSpec(BASE_URL), Specification.responseSpecOK200());
    }

    @AfterEach
    public void deleteStudent() {
        Integer studentId = Jdbc.fetchUserId(request.getEmail());
        String activationCode = Jdbc.fetchUserActivationCode(request.getEmail());

        activateStudentAccount(studentId, activationCode);
        deleteStudentAccount(studentId);
        request = null;
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
                .statusCode(200);
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
}
