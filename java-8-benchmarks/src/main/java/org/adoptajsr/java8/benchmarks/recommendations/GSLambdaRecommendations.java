package org.adoptajsr.java8.benchmarks.recommendations;

import com.gs.collections.api.RichIterable;
import com.gs.collections.api.bag.primitive.MutableIntBag;
import com.gs.collections.api.list.MutableList;
import com.gs.collections.api.map.MutableMap;
import com.gs.collections.api.multimap.list.ListMultimap;
import com.gs.collections.api.tuple.Pair;
import com.gs.collections.impl.utility.ListIterate;
import org.adoptajsr.java8.util.CoBuy;
import org.adoptajsr.java8.util.Purchases;

public class GSLambdaRecommendations {

    private Purchases purchases;

    public GSLambdaRecommendations(Purchases purchases) {
        this.purchases = purchases;
    }

    private MutableList<Integer> gsKeys(MutableIntBag bag) {
        return bag.asLazy()
                .collect(Integer::new)
                .toSortedListBy(userId -> -bag.occurrencesOf(userId));
    }

    public RichIterable<CoBuy> gsCombinations(RichIterable<Purchases.Purchase> purchases) {
        return purchases.flatCollect(x -> purchases.select(y -> x != y)
                .collect(y -> new CoBuy(x.getProductId(), y.getProductId())));
    }

    public MutableMap<Integer, MutableList<Integer>> calculateRecommendations() {
        ListMultimap<Integer, Purchases.Purchase> buysByUser =
                ListIterate.groupBy(purchases.getPurchases(), Purchases.Purchase::getUserId);

        MutableMap<Integer, MutableIntBag> productSimilarity =
                buysByUser.multiValuesView()
                        .flatCollect(this::gsCombinations)
                        .groupBy(CoBuy::getProductId1)
                        .keyMultiValuePairsView()
                        .toMap((Pair<Integer, ?> pair) -> pair.getOne(),
                                pair -> pair.getTwo().asLazy()
                                        .collectInt(CoBuy::getProductId2).toBag());

        return productSimilarity.keyValuesView()
                .toMap(pair -> pair.getOne(),
                        pair -> gsKeys(pair.getTwo()));
    }

}
