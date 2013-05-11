/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8.benchmarks;

import com.gs.collections.impl.list.mutable.primitive.DoubleArrayList;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author richard
 */
public class VectorDotProductTest {
    
    private final VectorDotProducts underTest = new VectorDotProducts();
    
    @Test
    public void gsImplementation() {
        underTest.gsX = DoubleArrayList.newListWith(1, 2);
        underTest.gsY = DoubleArrayList.newListWith(2, 1);
        Assert.assertEquals(4D, underTest.timeGSDotProduct(1), 0.0001);
    }
    
    @Test
    public void imperativeImplementation() {
        underTest.x = new double[] {1, 2};
        underTest.y = new double[] {2, 1};
        Assert.assertEquals(4D, underTest.timeImperativeDotProduct(1), 0.0001);
    }
    
    @Test
    public void lambdaImplementation() {
        underTest.x = new double[] {1, 2};
        underTest.y = new double[] {2, 1};
        Assert.assertEquals(4D, underTest.timeLambdaDotProduct(1), 0.0001);
    }
    
    
}
