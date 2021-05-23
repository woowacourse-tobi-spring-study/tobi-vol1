package template;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {
    @Test
    public void sum() throws IOException {
        Calculator calculator = new Calculator();
        final String path = getClass().getClassLoader().getResource("numbers.txt").getPath();
        int sum = calculator.calcSum(path);
        assertThat(sum).isEqualTo(10);
    }
}
