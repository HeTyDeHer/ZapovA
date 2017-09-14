package ru.job4j;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Test.
*
* @author ZapovA
* @version $Id$
* @since 0.1
*/

public class CalculateTest {

/**
* Test
*/ @Test

public void whenTakeNameThenTreeEchoPlusName() {
    String input = "Input";
    String expect = "Echo, echo, echo : Input"; 
    Calculate calc = new Calculate();
    String result = calc.echo(input);
    assertThat(result, is(expect));
}
 
}