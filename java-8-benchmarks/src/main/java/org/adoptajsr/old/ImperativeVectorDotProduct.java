/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.old;

import org.adoptajsr.runners.VectorDotProduct;

public class ImperativeVectorDotProduct extends VectorDotProduct {
    
    private double[] x;
    private double[] y;

    @Override
    public void prepare(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double dotProduct() {
        final int N = x.length;
        double sum = 0;
        for (int i = 0; i < N; i++) {
            sum += x[i] * y[i];
        }
        return sum;
    }

    @Override
    public String getName() {
        return "Imperative Dot Product";
    }

}
