/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.adoptajsr.java8.benchmarks;

import com.google.caliper.SimpleBenchmark;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;
import org.adoptajsr.java8.util.CoBuy;
import org.adoptajsr.java8.util.Purchases;

/**
 *
 * @author richard
 */
public class Recommendations extends SimpleBenchmark {
    
    private Purchases purchases;

    @Override
    protected void setUp() throws Exception {
        setPurchases(purchases.load());
    }
    
    public void setPurchases(Purchases purchases) {
        this.purchases = purchases;
    }
    
    public Map<Integer, List<Integer>> timeLambdaRecommendations(int reps) {
        Map<Integer, List<Integer>> results = null;
        for (int i = 0; i < reps; i++) {
            // build a user id to products purchased map
            Map<Integer, List<Integer>> buysByUser =
                    purchases.getPurchases()
                             .stream()
                             .collect(groupingBy(Purchases.Purchase::getUserId, productIds()));

            // product id -> product id -> frequency purchased together
            Map<Integer, Map<Integer, Long>> productSimilarity =
                    buysByUser.values()
                              .stream()
                              .flatMap(this::combinations)
                              .collect(groupingBy(CoBuy::getProductId1,
                                       groupingBy(CoBuy::getProductId2, counting())));

            // replace the tree maps by a sorted list of keys
            results = productSimilarity.entrySet()
                                       .stream()
                                       .collect(toMap(Map.Entry::getKey, e -> keys(e.getValue())));
        }
        
        return results;
    }
    
    public Stream<CoBuy> combinations(List<Integer> values) {
        return values.stream()
                     .flatMap(x -> values.stream()
                         .filter(y -> x != y)
                         .map(y -> new CoBuy(x, y)));
    }
    
    private List<Integer> keys(Map<Integer, Long> map) {
        return new ArrayList<>(map.keySet());
    }


    private Collector<Purchases.Purchase, List<Integer>> productIds() {
        return mapping(buy -> buy.getProductId(), toList());
    }
    
    public List<Integer> alsoBought(int item, int number, Map<Integer, List<Integer>> productsBySimilarity) {
        return productsBySimilarity.getOrDefault(item, Collections.emptyList())
                                   .stream()
                                   .limit(number)
                                   .collect(toList());
    }

    
}
