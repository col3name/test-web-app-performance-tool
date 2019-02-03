package com.java.course.project.core.entity;

import com.java.course.project.ExceptionTest;
import com.java.course.project.core.InvalidEntityException;
import org.junit.Test;

//import static org.junit.Assert.*;

public class ResponseTest extends ExceptionTest {
    @Test
    public void responseCodeLessMinResponseCode() throws InvalidEntityException {
        exceptException(InvalidEntityException.class, "Invalid response code '99'");
        new Response(99, 122, 12L);
    }
}