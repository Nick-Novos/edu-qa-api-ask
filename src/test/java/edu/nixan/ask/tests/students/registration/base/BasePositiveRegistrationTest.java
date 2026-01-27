package edu.nixan.ask.tests.students.registration.base;

import edu.nixan.ask.tests.base.SpecificationBuilder;

public abstract class BasePositiveRegistrationTest extends BaseRegistrationTest {

    protected final static String STATUS = "success";
    protected final static String MESSAGE = "User was created";

    protected BasePositiveRegistrationTest() {
        super(SpecificationBuilder.responseSpecOK200());
    }
}
