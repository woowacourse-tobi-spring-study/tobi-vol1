package springbook.learningtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {

    private Calculator calculator;
    private String path;

    @BeforeEach
    void setUp() {
        this.calculator = new Calculator();
        this.path = this.getClass().getClassLoader().getResource("numbers.txt").getPath();
    }

    @Test
    void sumOfNumbers() throws IOException {
        int result = calculator.calcSum(path);

        assertThat(result).isEqualTo(10);
    }

    @Test
    void multipleOfNumbers() throws IOException {
        int result = calculator.calcMultiple(path);

        assertThat(result).isEqualTo(24);
    }

    @Test
    void concat() throws IOException {
        String result = calculator.concat(path);

        assertThat(result).isEqualTo("1234");
    }
}
