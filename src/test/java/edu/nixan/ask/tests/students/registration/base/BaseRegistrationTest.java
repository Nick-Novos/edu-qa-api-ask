package edu.nixan.ask.tests.students.registration.base;

import edu.nixan.ask.model.Signup;
import edu.nixan.ask.model.StatusResponse;
import edu.nixan.ask.tests.base.BaseTest;
import edu.nixan.ask.tests.base.SpecificationBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.AfterEach;

public abstract class BaseRegistrationTest extends BaseTest {

    protected static final String END_POINT = "/sign-up";

    protected final RequestSpecification requestSpec;
    protected final ResponseSpecification responseSpec;

    protected Signup request;

    protected BaseRegistrationTest(ResponseSpecification responseSpec) {
        this.requestSpec = SpecificationBuilder.requestSpec(BASE_URL);
        this.responseSpec = responseSpec;
    }

    @AfterEach
    public void clean() {
//        userRepository.deleteStudent(request.getEmail());
    }

    protected StatusResponse register(Object request) {
        return post(END_POINT, request, StatusResponse.class, requestSpec, responseSpec);
    }
}
