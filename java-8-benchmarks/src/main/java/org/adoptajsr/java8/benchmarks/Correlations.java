/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8.benchmarks;

import com.google.caliper.SimpleBenchmark;
import java.util.Arrays;
import java.util.stream.Streams;
import static java.util.stream.Streams.intRange;
import static java.util.stream.Streams.zip;

/**
 * @see http://en.wikipedia.org/wiki/Autocorrelation
 * 
 * @author richard
 */
public class Correlations extends SimpleBenchmark {

    private double[] values;

    @Override
    protected void setUp() throws Exception {
        setValues(Streams.generateDouble(Math::random)
                         .limit(10_000)
                         .toArray());
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public double[] timeImperativeAutoCorrelation(int reps) {
        double[] result = null;
        for (int i = 0; i < reps; i++) {
            int length = values.length;
            result = new double[length];
            for (int window = 0; window < length; window++) {
                result[window] = imperativeCorrelationAtIndex(length, window, values);
            }
        }
        return result;
    }

    private double imperativeCorrelationAtIndex(int length, int window, double[] values) {
        double sum = 0;
        for (int j = 0; j < length - window; j++) {
            sum += values[j] * values[j + window];
        }
        return sum;
    }

    public double[] timeLambdaAutoCorrelation(int reps) {
        double[] result = null;
        for (int i = 0; i < reps; i++) {
            int length = values.length;
            result = intRange(0, length)
                    .mapToDouble(window -> lambdaCorrelationAtIndex(values, length, window))
                    .toArray();
        }
        return result;
    }

    private Double lambdaCorrelationAtIndex(double[] values, int length, int window) {
        return zip(Arrays.stream(values, 0, length - window).boxed(),
                   Arrays.stream(values, window, length).boxed(),
                          (x, y) -> x * y)
              .reduce(0D, Double::sum);
    }

}
