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

import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.api.multimap.list.ListMultimap;
import java.util.List;
import java.util.Map;

import org.adoptajsr.java8.benchmarks.recommendations.GSLambdaRecommendations;
import org.adoptajsr.java8.benchmarks.recommendations.LambdaRecommendations;
import org.adoptajsr.java8.util.Purchases;
import org.adoptajsr.java8.util.Purchases.Purchase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class RecommendationsTest {

    @Test
    public void lambdaVariant() {
        LambdaRecommendations recommendations = new LambdaRecommendations(makePurchases());
        Map<Integer, List<Integer>> results = recommendations.calculateRecommendations();
        
        Assert.assertTrue(recommendations.alsoBought(4, 1, results).contains(5));
        Assert.assertTrue(recommendations.alsoBought(5, 1, results).contains(4));
    }
    
    @Ignore
    @Test
    public void gsVariant() {
        GSLambdaRecommendations recommendations = new GSLambdaRecommendations(makePurchases());
        MutableMap<Integer, MutableList<Integer>> results = recommendations.calculateRecommendations();
             
        Assert.assertEquals(4, results.get(5).get(0).intValue());
        Assert.assertEquals(5, results.get(4).get(0).intValue());
    }

    // products: 3 .. 10
    // users: 1,2
    private Purchases makePurchases() {
        Purchases purchases = new Purchases();
        buy(purchases, 4, 5, 2);
        buy(purchases, 3, 10, 1);
        return purchases;
    }

    private void buy(Purchases purchases, int firstProduct, int lastProduct, int user) {
        for (int i = firstProduct; i <= lastProduct; i++) {
            purchases.addPurchase(new Purchase(user, i));
        }
    }

}
