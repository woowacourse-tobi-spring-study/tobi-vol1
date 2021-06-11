package springbook.learningtest.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {

    private Calculator calculator;
    private String filePath;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        filePath = Objects.requireNonNull(getClass().getResource("/numbers.txt")).getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(filePath)).isEqualTo(10);
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiply(filePath)).isEqualTo(24);
    }

    @Test
    public void concatenateStrings() throws IOException {
        assertThat(calculator.concatenate(filePath)).isEqualTo("1234");
    }
}
