/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import org.adoptajsr.java8.Purchases.Purchase;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.Collections.emptyList;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class Java8Recommender extends Recommender {

    private Purchases purchases;
    private Map<Integer, List<Integer>> productsBySimilarity;

    @Override
    public void inject(Purchases coincidences) {
        this.purchases = coincidences;
    }
    
    private static class CoBuy {
        int productId1,productId2;
        CoBuy(int x, int y) {
            this.productId1 = x;
            this.productId2 = y;
        }

        @Override
        public String toString() {
            return "[" + productId1 + "," + productId2 + "]";
        }
    }

    @Override
    public void preprocess() {
        // build a user id to products purchased map
        Map<Integer, List<Integer>> buysByUser =
                purchases.getPurchases()
                         .stream()
                         .collect(groupingBy(Purchase::getUserId, productIds()));

        // product id -> product id -> frequency purchased together
        Map<Integer, Map<Integer, Long>> productSimilarity =
                buysByUser.values()
                          .stream()
                          .flatMap(this::combinations)
                          .collect(groupingBy(coBuy -> coBuy.productId1,
                                   groupingBy(coBuy -> coBuy.productId2, counting())));

        // replace the tree maps by a sorted list of keys
        productsBySimilarity = productSimilarity.entrySet()
                                                .stream()
                                                .collect(toMap(Entry::getKey,
                                                               e -> keys(e.getValue())));
    }

    private List<Integer> keys(Map<Integer, Long> map) {
        return new ArrayList<>(map.keySet());
    }

    @Override
    public List<Integer> alsoBought(int item, int number) {
        return productsBySimilarity.getOrDefault(item, emptyList())
                                   .stream()
                                   .limit(number)
                                   .collect(toList());
    }

    private Collector<Purchase, List<Integer>> productIds() {
        return mapping(buy -> buy.getProductId(), toList());
    }
    
    Stream<CoBuy> combinations(List<Integer> values) {
        return values.stream()
                     .flatMap(x -> values.stream()
                         .filter(y -> x != y)
                         .map(y -> new CoBuy(x, y)));
    }

}
