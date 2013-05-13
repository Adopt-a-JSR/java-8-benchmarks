/**
    Copyright 2013 Richard Warburton <richard.warburton@gmail.com>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.   
   */

package org.adoptajsr.java8.benchmarks;

import com.google.caliper.SimpleBenchmark;
import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.Streams;
import static java.util.stream.IntStream.range;
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
        setValues(DoubleStream.generate(Math::random)
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
            result = range(0, length)
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
