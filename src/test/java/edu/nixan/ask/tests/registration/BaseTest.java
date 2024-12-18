package edu.nixan.ask.tests.registration;

public interface BaseTest {

    String BASE_URL = "http://ask-qa.portnov.com/api/v1";

    void createRequest();

    default void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
