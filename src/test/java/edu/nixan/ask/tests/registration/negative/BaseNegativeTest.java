package edu.nixan.ask.tests.registration.negative;

import edu.nixan.ask.model.Signup;
import edu.nixan.ask.spec.Specification;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseNegativeTest {

    static String BASE_URL = "http://ask-qa.portnov.com/api/v1";

    protected final static String ERROR_STATUS = "error";

    protected Signup request;

    @BeforeAll
    public static void init() {
        Specification.installSpecifications(
                Specification.requestSpec(BASE_URL), Specification.responseSpecError400());
    }
}
