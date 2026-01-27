package edu.nixan.ask.tests.students.registration.base;

import edu.nixan.ask.tests.base.SpecificationBuilder;

public abstract class BaseNegativeRegistrationTest extends BaseRegistrationTest {

    protected final static String ERROR_STATUS = "error";

    public BaseNegativeRegistrationTest() {
        super(SpecificationBuilder.responseSpecError400());
    }
}
