/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.runners;

/**
 * @author richard
 */
public abstract class Correlation {

    public static void main(String[] args) {
        
    }

    public double[] autoCorrelate(double[] x) {
        return crossCorrelate(x, x);
    }

    public abstract double[] crossCorrelate(double[] x, double[] y);

}
