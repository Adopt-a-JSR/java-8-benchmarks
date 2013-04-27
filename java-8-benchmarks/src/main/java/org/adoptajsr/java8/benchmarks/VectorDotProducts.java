package org.adoptajsr.java8.benchmarks;

import com.google.caliper.SimpleBenchmark;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;
import java.util.stream.Streams;

/**
 *
 * @author richard
 */
public class VectorDotProducts extends SimpleBenchmark {
    
    private static final int SIZE = 10_000_000;
    
    double[] x,y;

    @Override
    protected void setUp() throws Exception {
        x = randomArray();
        y = randomArray();
    }
    
    private double[] randomArray() {
        Random random = new Random();
        double[] array = new double[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = random.nextDouble();
        }
        return array;
    }

    public double timeImperativeDotProduct() {
        double sum = 0;
        for (int i = 0; i < SIZE; i++) {
            sum += x[i] * y[i];
        }
        return sum;
    }

    public double timeLambdaDotProduct(int reps) {
        double result = 0;
        for (int i = 0; i < reps; i++) {
            result = Streams.zip(stream(x),stream(y),
                                 (left, right) -> left * right)
                            .reduce(0.0, Double::sum);
        }
        return result;
    }
    
    private Stream<Double> stream(double[] values) {
        return Arrays.stream(values).boxed(); 
    }

}
