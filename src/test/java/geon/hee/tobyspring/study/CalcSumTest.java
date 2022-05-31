package geon.hee.tobyspring.study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class CalcSumTest {

    private Calculator calculator;
    private String numFilepath;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
        numFilepath = Objects.requireNonNull(getClass().getResource("/numbers.txt")).getPath();
    }

    @Test
    void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(numFilepath)).isEqualTo(10);
    }

    @Test
    void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiply(numFilepath)).isEqualTo(24);
    }
}
