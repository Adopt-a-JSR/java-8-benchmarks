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

import com.google.caliper.SimpleBenchmark;
import com.gs.collections.api.RichIterable;
import com.gs.collections.api.bag.primitive.MutableIntBag;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.api.multimap.bag.BagMultimap;
import com.gs.collections.api.multimap.list.ListMultimap;
import com.gs.collections.api.set.SetIterable;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.impl.bag.mutable.HashBag;
import com.gs.collections.impl.bag.mutable.primitive.IntHashBag;
import com.gs.collections.impl.list.mutable.FastList;
import com.gs.collections.impl.utility.ListIterate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparators;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.ToLongFunction;
import java.util.stream.Collector;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;
import org.adoptajsr.java8.util.CoBuy;
import org.adoptajsr.java8.util.Purchases;
import org.adoptajsr.java8.util.Purchases.Purchase;

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
        List<Integer> userIds = new ArrayList<>(map.keySet());
        // TODO: complain about type inference
        ToLongFunction<? super Integer> lookup = id -> map.get(id);
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
    
    public ListMultimap<Integer, Integer> timeGSRecommendations(int reps) {
        ListMultimap<Integer, Integer> results = null;
        for (int i = 0; i < reps; i++) {
            ListMultimap<Integer, Purchase> buysByUser =
                    ListIterate.groupBy(purchases.getPurchases(), Purchase::getUserId);

            MutableMap<Integer, MutableIntBag> productSimilarity = 
                    buysByUser.multiValuesView()
                              .flatCollect(this::gsCombinations)
                              .groupBy(CoBuy::getProductId1)
                              .keyMultiValuePairsView()
                              .toMap(Pair::getOne,
                                     pair -> pair.getTwo().asLazy()
                                                          .collectInt(CoBuy::getProductId2).toBag());
            
            productSimilarity.keyValuesView()
                             .toMap(pair -> pair.getOne(),
                                    pair -> gsKeys(pair.getTwo()));
        }
        return results;
    }
    
    private MutableList<Integer> gsKeys(MutableIntBag bag) {
        return bag.asLazy()
                  .collect(Integer::new)
                  .toSortedListBy(userId -> -bag.occurrencesOf(userId));
    }
    
    public RichIterable<CoBuy> gsCombinations(RichIterable<Purchase> purchases) {
        return purchases.flatCollect(x -> purchases.select(y -> x != y)
                                                   .collect(y -> new CoBuy(x.getProductId(), y.getProductId())));
    }

    
}
