package org.adoptajsr.java8.benchmarks.recommendations;

import org.adoptajsr.java8.util.Purchases;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToLongFunction;

import static java.util.Collections.emptyList;
import static java.util.Map.Entry;
import static java.util.stream.Collectors.*;
import static org.adoptajsr.java8.util.Purchases.Purchase;

public class ImperativeRecommendations {

    private Purchases purchases;

    public ImperativeRecommendations(Purchases purchases) {
        this.purchases = purchases;
    }

    public Map<Integer, List<Integer>> calculateRecommendations() {
        Map<Integer, List<Integer>> buysByUser = buysByUser();
        Map<Integer, Map<Integer, Long>> productSimilarity = calculateProductSimilarity(buysByUser);
        return getRecommendations(productSimilarity);
    }

    private Map<Integer, List<Integer>> getRecommendations(Map<Integer, Map<Integer, Long>> productSimilarity) {
        Map<Integer, List<Integer>> recommendations = new HashMap<>();
        for (Entry<Integer, Map<Integer, Long>> entry : productSimilarity.entrySet()) {
            recommendations.put(entry.getKey(), sortedKeys(entry.getValue()));
        }
        return recommendations;
    }

    private List<Integer> sortedKeys(Map<Integer, Long> from) {
        List<Integer> keys = new ArrayList<>(from.keySet());
        ToLongFunction<? super Integer> lookup = from::get;
        keys.sort(Comparators.comparing(lookup).reverseOrder());
        return keys;
    }

    private Map<Integer, Map<Integer, Long>> calculateProductSimilarity(Map<Integer, List<Integer>> buysByUser) {
        Map<Integer, Map<Integer, Long>> productsBySimilarity = new HashMap<>();
        for (Entry<Integer, List<Integer>> usersPurchases : buysByUser.entrySet()) {
            List<Integer> purchases = usersPurchases.getValue();
            for (int product : purchases) {
                for (int otherProduct : purchases) {
                    if (product == otherProduct)
                        continue;

                    Map<Integer, Long> purchaseCounts =
                        productsBySimilarity.computeIfAbsent(product, unused -> new HashMap<Integer, Long>());

                    purchaseCounts.compute(otherProduct, (unused, oldCount) -> oldCount == null ? 1 : oldCount + 1);
                }
            }
        }
        return productsBySimilarity;
    }

    private Map<Integer, List<Integer>> buysByUser() {
        Map<Integer, List<Integer>> buys = new HashMap<>();
        for (Purchase purchase : purchases.getPurchases()) {
            int userId = purchase.getUserId();
            List<Integer> otherProducts = buys.computeIfAbsent(userId, unused -> new ArrayList<Integer>());
            otherProducts.add(purchase.getProductId());
        }
        return buys;
    }

    public List<Integer> alsoBought(int item, int number, Map<Integer, List<Integer>> productsBySimilarity) {
        List<Integer> otherProducts = productsBySimilarity.get(item);
        if (otherProducts == null) {
            return emptyList();
        } else if (otherProducts.size() < number) {
            return otherProducts;
        } else {
            return otherProducts.subList(0, number);
        }
    }

}
