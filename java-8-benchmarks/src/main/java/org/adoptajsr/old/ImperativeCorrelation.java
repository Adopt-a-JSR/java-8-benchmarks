/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.old;

import org.adoptajsr.runners.Correlation;

/**
 *
 * @author richard
 */
public class ImperativeCorrelation extends Correlation {

    @Override
    public double[] crossCorrelate(double[] x, double[] y) {
        int N = x.length;
        double[] R = new double[N];
        for (int i = 0; i < N; i++) {
            R[i] = correlationAtIndex(N, i, x, y);
        }
        return R;
    }

    private double correlationAtIndex(int N, int i, double[] x, double[] y) {
        double sum = 0;
        for (int j = 0; j < N - i; j++) {
            sum += x[j] * y[j + i];
        }
        return sum;
    }

}
