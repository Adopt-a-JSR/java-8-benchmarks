package org.adoptajsr.java8.benchmarks.recommendations;

import org.adoptajsr.java8.util.CoBuy;
import org.adoptajsr.java8.util.Purchases;

import java.util.*;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

public class LambdaRecommendations {

    private Purchases purchases;

    public LambdaRecommendations(Purchases purchases) {
        this.purchases = purchases;
    }

    public Map<Integer, List<Integer>> calculateRecommendations() {
        Map<Integer, List<Integer>> buysByUser = buysByUser();
        Map<Integer, Map<Integer, Long>> productSimilarity = calcProductSimilarity(buysByUser);

        // replace the tree maps by a sorted list of keys
        return productSimilarity.entrySet()
                                .stream()
                                .collect(toMap(Map.Entry::getKey, e -> keys(e.getValue())));
    }

    // product id -> product id -> frequency purchased together
    private Map<Integer, Map<Integer, Long>> calcProductSimilarity(Map<Integer, List<Integer>> buysByUser) {
        return buysByUser.values()
                .stream()
                .flatMap(this::combinations)
                .collect(groupingBy(CoBuy::getProductId1,
                        groupingBy(CoBuy::getProductId2, counting())));
    }

    private Map<Integer, List<Integer>> buysByUser() {
        return purchases.getPurchases()
                .stream()
                .collect(groupingBy(Purchases.Purchase::getUserId, productIds()));
    }

    public Stream<CoBuy> combinations(List<Integer> values) {
        return values.stream()
                .flatMap(x -> values.stream()
                        .filter(y -> x != y)
                        .map(y -> new CoBuy(x, y)));
    }

    private List<Integer> keys(Map<Integer, Long> map) {
        List<Integer> userIds = new ArrayList<>(map.keySet());
        // TODO: complain about type inference
        ToLongFunction<? super Integer> lookup = map::get;
        userIds.sort(Comparators.comparing(lookup).reverseOrder());
        return userIds;
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
