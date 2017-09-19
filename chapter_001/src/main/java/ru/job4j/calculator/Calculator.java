package ru.job4j.calculator;
/**
 * Решение задачи 2.3 /001 [#185].
 * @author ZapovA
 * @since 14.9.17
*/

public class Calculator {
/**
* Переменная result.
*/
	private double result;
/**
* Method add.
* @param first - первый.
* @param second - второй.
*/
	public void add(double first, double second) {
	this.result = first + second;
	}
/**
* Method subtraction.
* @param first - первый.
* @param second - второй.
*/
	public void subtraction(double first, double second) {
	this.result = first - second;
	}
/**
* Method division.
* @param first - первый.
* @param second - второй.
*/
	public void division(double first, double second) {
	this.result = first / second;
	}
/**
* Method multiplication.
* @param first - первый.
* @param second - второй.
*/
	public void multiplication(double first, double second) {
	this.result = first * second;
	}
/**
* Method getResult.
* @return result.
*/
	public double getResult() {
	return this.result;
	}
}