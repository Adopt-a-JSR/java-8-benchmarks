/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import org.adoptajsr.runners.Purchases;
import org.adoptajsr.runners.Recommender;
import org.adoptajsr.runners.Purchases.Purchase;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.Collections.emptyList;
import java.util.Map.Entry;
import java.util.TreeMap;
import static java.util.stream.Streams.zip;

public class Java8Recommender extends Recommender {

    private Purchases purchases;
    private Map<Integer, List<Integer>> productsBySimilarity;

    @Override
    public void inject(Purchases coincidences) {
        this.purchases = coincidences;
    }
    
    private class CoBuy {
        int x,y;
        CoBuy(int x, int y) {
            this.x = x;
            this.y = y;
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
                          .flatMap(buys -> zip(buys.stream(), buys.stream(), CoBuy::new))
                          .collect(groupingBy(coBuy -> coBuy.x,
                                   groupingBy(coBuy -> coBuy.y, TreeMap::new, counting())));

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

}
