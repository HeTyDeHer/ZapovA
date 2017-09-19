package ru.job4j.calculator;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест задачи 2.3 /001 [#185].
 * @author ZapovA
 * @since 14.9.17
*/

public class CalculatorTest {

/**
 * Сложение, 1+1=0.
*/
@Test
    public void whenAddOnePlusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.add(1D, 1D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }
/**
 * Вычитание, 1-1=0.
*/
	@Test
    public void whenAddOneMinusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.subtraction(1D, 1D);
        double result = calc.getResult();
        double expected = 0D;
        assertThat(result, is(expected));
    }
/**
 * Умножение, 2*2=4.
*/
	@Test
    public void whenTwoMultTwo() {
        Calculator calc = new Calculator();
        calc.multiplication(2D, 2D);
        double result = calc.getResult();
        double expected = 4D;
        assertThat(result, is(expected));
    }
/**
 * Деление, 4/2=2.
*/
	@Test
    public void whenFourDivTwo() {
        Calculator calc = new Calculator();
        calc.division(4D, 2D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }
}