/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.runners;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Streams;
import org.adoptajsr.java8.Java8Correlation;
import org.adoptajsr.old.ImperativeCorrelation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author richard
 */
@RunWith(value = Parameterized.class)
public class CorrelationTest {

    @Parameters
    public static Collection<Object[]> data() {
      Object[][] data = new Object[][] { { new ImperativeCorrelation() },
                                         { new Java8Correlation() },
      };
      return Arrays.asList(data);
    }

    private final Correlation correlation;

    public CorrelationTest(Correlation correlation) {
        this.correlation = correlation;
    }

    @Test
    public void autoCorrelation1() {
        double[] data = sequence(1, 3);
        double[] expected = {3, 2, 1};
        assertEquals(expected, correlation.autoCorrelate(data));
    }

    @Test
    public void autoCorrelation2() {
        double[] data = {2, 3, 1};
        // Does this make sense?
        double[] expected = {14, 9, 2};
        assertEquals(expected, correlation.autoCorrelate(data));
    }

    private double[] sequence(int value, int size) {
        return Streams.iterateDouble(value, x -> x)
                      .limit(size)
                      .toArray();
    }

    private void assertEquals(double[] expected, double[] result) {
        Assert.assertTrue(Arrays.toString(expected) + " but was " + Arrays.toString(result),
                          Arrays.equals(expected, result));
    }

}
