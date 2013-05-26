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

import static java.util.Map.*;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import java.util.stream.Stream;

import org.adoptajsr.java8.benchmarks.recommendations.GSLambdaRecommendations;
import org.adoptajsr.java8.benchmarks.recommendations.LambdaRecommendations;
import org.adoptajsr.java8.util.CoBuy;
import org.adoptajsr.java8.util.Purchases;
import org.adoptajsr.java8.util.Purchases.Purchase;

/**
 *
 * @author richard
 */
public class Recommendations extends SimpleBenchmark {

    private LambdaRecommendations lambdaRecommendations;
    private GSLambdaRecommendations gsLambdaRecommendations;

    @Override
    protected void setUp() throws Exception {
        setPurchases(Purchases.load());
    }
    
    public void setPurchases(Purchases purchases) {
        lambdaRecommendations = new LambdaRecommendations(purchases);
        gsLambdaRecommendations = new GSLambdaRecommendations(purchases);
    }

    public Map<Integer, List<Integer>> calculateRecommendations(int reps) {
        Map<Integer, List<Integer>> results = null;
        for (int i = 0; i < reps; i++) {
            results = lambdaRecommendations.calculateRecommendations();
        }
        return results;
    }
    
    public MutableMap<Integer, MutableList<Integer>> timeGSRecommendations(int reps) {
        MutableMap<Integer, MutableList<Integer>> results = null;
        for (int i = 0; i < reps; i++) {
            results = gsLambdaRecommendations.calculateRecommendations();
        }
        return results;
    }
    
}
