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
