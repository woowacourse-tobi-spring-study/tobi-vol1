package template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {
    private Calculator calculator;
    private String path;

    @BeforeEach
    public void setUp() {
        calculator = new Calculator();
        path = getClass().getClassLoader().getResource("numbers.txt").getPath();
    }

    @Test
    public void sum() throws IOException {
        int sum = calculator.calcSum(path);
        assertThat(sum).isEqualTo(10);
    }

    @Test
    public void multiply() throws IOException {
        int multiply = calculator.calcMultiply(path);
        assertThat(multiply).isEqualTo(24);
    }
}
