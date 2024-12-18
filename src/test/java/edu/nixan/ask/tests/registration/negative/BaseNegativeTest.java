package edu.nixan.ask.tests.registration.negative;

import edu.nixan.ask.model.Signup;
import edu.nixan.ask.spec.Specification;
import edu.nixan.ask.tests.registration.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseNegativeTest implements BaseTest {

    protected final static String ERROR_STATUS = "error";

    protected Signup request;

    @BeforeAll
    public static void init() {
        Specification.installSpecifications(
                Specification.requestSpec(BASE_URL), Specification.responseSpecError400());
    }

    @AfterEach
    public void sleep() {
        sleep(1000);
    }
}
