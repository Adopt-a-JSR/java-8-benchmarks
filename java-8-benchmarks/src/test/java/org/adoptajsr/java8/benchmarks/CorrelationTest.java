/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8.benchmarks;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.IntFunction;
import java.util.stream.DoubleStream;
import org.adoptajsr.java8.benchmarks.Correlations;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author richard
 */
@RunWith(value = Parameterized.class)
public class CorrelationTest {

    private static final Correlations correlations = new Correlations();

    // fscking lack of type aliases
    @FunctionalInterface
    public static interface Correlator extends IntFunction<double[]> {}

    @Parameters
    public static Collection<Object[]> data() {
      Object[][] data = new Object[][] { { (Correlator) correlations::timeImperativeAutoCorrelation },
                                         { (Correlator) correlations::timeLambdaAutoCorrelation },
      };
      return Arrays.asList(data);
    }

    private final Correlator correlator;

    public CorrelationTest(Correlator correlation) {
        this.correlator = correlation;
    }

    @Test
    public void autoCorrelation1() {
        correlations.setValues(sequence(1, 3));
        double[] expected = {3, 2, 1};
        assertEquals(expected, correlator.apply(1));
    }

    @Test
    public void autoCorrelation2() {
        correlations.setValues(new double[]{2, 3, 1});
        double[] expected = {14, 9, 2};
        assertEquals(expected, correlator.apply(1));
    }

    private double[] sequence(int value, int size) {
        return DoubleStream.iterate(value, x -> x)
                           .limit(size)
                           .toArray();
    }

    private void assertEquals(double[] expected, double[] result) {
        Assert.assertTrue(Arrays.toString(expected) + " but was " + Arrays.toString(result),
                          Arrays.equals(expected, result));
    }

}
