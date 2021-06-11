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
        int sum = calculator.calcSum(filePath);
        assertThat(sum).isEqualTo(10);
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        int multiple = calculator.calcMultiply(filePath);
        assertThat(multiple).isEqualTo(24);
    }
}
