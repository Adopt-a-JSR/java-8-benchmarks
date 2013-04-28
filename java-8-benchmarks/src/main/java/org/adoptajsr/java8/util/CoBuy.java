/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8.util;

/**
 * @author richard
 */
public class CoBuy {
    
    private final int productId1;
    private final int productId2;

    public CoBuy(int x, int y) {
        this.productId1 = x;
        this.productId2 = y;
    }

    @Override
    public String toString() {
        return "[" + getProductId1() + "," + getProductId2() + "]";
    }

    /**
     * @return the productId1
     */
    public int getProductId1() {
        return productId1;
    }

    /**
     * @return the productId2
     */
    public int getProductId2() {
        return productId2;
    }
    
}
