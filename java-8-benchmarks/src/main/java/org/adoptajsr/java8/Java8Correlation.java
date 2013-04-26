/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8;

import java.util.Arrays;
import static java.util.stream.Streams.intRange;
import static java.util.stream.Streams.zip;
import org.adoptajsr.runners.Correlation;

/**
 * @author richard
 */
public class Java8Correlation extends Correlation {

    @Override
    public double[] crossCorrelate(double[] X, double[] Y) {
        int N = X.length;
        return intRange(0, N)
              .mapToDouble(i -> correlateAtIndex(X, N, i, Y))
              .toArray();
    }

    private Double correlateAtIndex(double[] X, int N, int window, double[] Y) {
        return zip(Arrays.stream(X, 0, N - window).boxed(),
                   Arrays.stream(Y, window, N).boxed(),
                          (x, y) -> x * y)
              .reduce(0D, Double::sum);
    }

}
