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

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author richard
 */
public class VectorDotProductTest {
    
    private static final VectorDotProducts underTest = new VectorDotProducts();
    static {
        underTest.x = new double[] {1, 2};
        underTest.y = new double[] {2, 1};
        underTest.setupDerivedData();
    }
    
    @Test
    public void gsImplementation() {
        Assert.assertEquals(4D, underTest.timeGSDotProduct(1), 0.0001);
    }
    
    @Test
    public void imperativeImplementation() {
        Assert.assertEquals(4D, underTest.timeImperativeDotProduct(1), 0.0001);
    }
    
    @Test
    public void imperativeBoxedImplementation() {
        Assert.assertEquals(4D, underTest.timeImperativeBoxedDotProduct(1), 0.0001);
    }
    
    @Test
    public void lambdaImplementation() {
        Assert.assertEquals(4D, underTest.timeLambdaDotProduct(1), 0.0001);
    }

}
