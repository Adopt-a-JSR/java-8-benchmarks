/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr;

import com.google.caliper.Runner;
import org.adoptajsr.java8.benchmarks.CharacterEncodings;
import org.adoptajsr.java8.benchmarks.Correlations;
import org.adoptajsr.java8.benchmarks.Recommendations;
import org.adoptajsr.java8.benchmarks.VectorDotProducts;

/**
 * @author richard
 */
public class RunBenchmarks {

    public static void main(String[] args) {
        Runner.main(Correlations.class, args);
        Runner.main(VectorDotProducts.class, args);
        Runner.main(CharacterEncodings.class, args);
        Runner.main(Recommendations.class, args);
    }

}
