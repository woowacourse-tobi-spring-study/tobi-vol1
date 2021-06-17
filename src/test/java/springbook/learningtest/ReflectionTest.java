package springbook.learningtest;

import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectionTest {

    @Test
    void invokeMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = String.class.getMethod("length");
        Integer length = (Integer) method.invoke("abc");

        assertThat(length).isEqualTo(3);
    }
}
