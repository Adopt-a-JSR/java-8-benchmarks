/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8;

import java.util.ArrayList;
import java.util.List;

public class Purchases {

    public static class Purchase {

        private final int userId;
        private final int productId;

        public Purchase(int userId, int productId) {
            this.userId = userId;
            this.productId = productId;
        }

        public int getUserId() {
            return userId;
        }

        public int getProductId() {
            return productId;
        }

    }

    private final List<Purchase> purchases;
    
    private int maxProductId;

    public Purchases() {
        maxProductId = Integer.MIN_VALUE;
        purchases = new ArrayList<>();
    }
    
    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
        maxProductId = Math.max(maxProductId, purchase.productId);
    }

    public int getMaxProductId() {
        return maxProductId;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

}
