package com.java.course.project;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

public abstract class ExceptionTest {
    @Rule
    public final ExpectedException expectedEx = ExpectedException.none();

    protected ExceptionTest() {
    }

    protected void exceptException(Class<? extends Throwable> type, String message) {
        expectedEx.expect(type);
        expectedEx.expectMessage(message);
    }
}