package org.adoptajsr.java8.benchmarks;

import java.util.List;
import java.util.Map;
import org.adoptajsr.java8.benchmarks.Recommendations;
import org.adoptajsr.java8.util.Purchases;
import org.adoptajsr.java8.util.Purchases.Purchase;
import org.junit.Assert;
import org.junit.Test;

public class Java8RecommenderTest {
    
    public static void main(String[] args) {
        new Java8RecommenderTest().example();
    }
    
    // products: 3 .. 10
    // users: 1,2
    @Test
    public void example() {
        Purchases purchases = new Purchases();
        buy(purchases, 4, 5, 2);
        buy(purchases, 3, 10, 1);
        
        Recommendations recommendations = new Recommendations();
        recommendations.setPurchases(purchases);
        Map<Integer, List<Integer>> results = recommendations.timeLambdaRecommendations(1);
        
        Assert.assertTrue(recommendations.alsoBought(4, 1, results).contains(5));
        Assert.assertTrue(recommendations.alsoBought(5, 1, results).contains(4));
    }

    private void buy(Purchases purchases, int firstProduct, int lastProduct, int user) {
        for (int i = firstProduct; i <= lastProduct; i++) {
            purchases.addPurchase(new Purchase(user, i));
        }
    }

}
