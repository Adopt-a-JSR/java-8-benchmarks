/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8;

import static java.util.Arrays.stream;
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
              .mapToDouble(i -> zip(stream(X, 0, N - i).boxed(),
                                    stream(Y, i, N).boxed(),
                                    (x, y) -> x * y)
                               .reduce(0D, Double::sum))
              .toArray();
    }

}
