package duikt.java.com;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculatorTest {

    private Calculator calculator;

    @Mock
    private Calculator mockedCalculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Addition test: 2 + 3 = 5")
    void testAdd() {
        double result = calculator.add(2.0, 3.0);
        assertEquals(5.0, result, 0.0001, "Addition should work correctly");
    }

    @Test
    @DisplayName("Subtraction test: 5 - 2 = 3")
    void testSubtract() {
        double result = calculator.subtract(5.0, 2.0);
        assertEquals(3.0, result, 0.0001);
    }

    @Test
    @DisplayName("Multiplication test: 4 * 2.5 = 10")
    void testMultiply() {
        double result = calculator.multiply(4.0, 2.5);
        assertEquals(10.0, result, 0.0001);
    }

    @Test
    @DisplayName("Division test: 10 / 2 = 5")
    void testDivide() {
        double result = calculator.divide(10.0, 2.0);
        assertEquals(5.0, result, 0.0001);
    }

    @Test
    @DisplayName("Test for division by zero (should throw ArithmeticException)")
    void testDivideByZero() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            calculator.divide(10.0, 0.0);
        });

        assertEquals("Error: Division by zero is not possible.", exception.getMessage());
    }

    @Test
    @DisplayName("Root test: sqrt(16) = 4")
    void testSqrt() {
        double result = calculator.sqrt(16.0);
        assertEquals(4.0, result, 0.0001);
    }

    @Test
    @DisplayName("Negative root test (InvalidInputException)")
    void testSqrtNegative() {
        Exception exception = assertThrows(InvalidInputException.class, () -> {
            calculator.sqrt(-25.0);
        });

        assertTrue(exception.getMessage().contains("-25.0"));
    }


    @Test
    @DisplayName("Mockito Demo: Modifying the Add Method's Behavior")
    void testMockitoExample() {
        when(mockedCalculator.add(10.0, 10.0)).thenReturn(100.0);

        double result = mockedCalculator.add(10.0, 10.0);

        assertEquals(100.0, result);

        Mockito.verify(mockedCalculator).add(10.0, 10.0);
    }
}