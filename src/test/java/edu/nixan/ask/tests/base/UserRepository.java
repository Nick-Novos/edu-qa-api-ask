package edu.nixan.ask.tests.base;

import edu.nixan.ask.model.Login;

import static io.restassured.RestAssured.given;

public class UserRepository {

    private final static String TEACHER_EMAIL = "qa1@test.com";
    private final static String TEACHER_PASSWORD = "ABC123";

    private final Login teacherLoginRequest;

    public UserRepository() {
        teacherLoginRequest = Login.builder()
                .email(TEACHER_EMAIL)
                .password(TEACHER_PASSWORD)
                .build();
    }

    public void deleteStudent(String email) {
        Integer studentId = Jdbc.fetchUserId(email);
        String activationCode = Jdbc.fetchUserActivationCode(email);
        if (studentId != null && activationCode != null) {
            activateStudentAccount(studentId, activationCode);
            deleteStudentAccount(studentId);
        } else {
            System.out.println("Delete student skipped: cannot retrieve studentId and activationCode from database");
        }
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
                .body(teacherLoginRequest)
                .when()
                .post("/sign-in")
                .then().log().all()
                .extract().path("token");
    }
}
