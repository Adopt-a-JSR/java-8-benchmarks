package org.adoptajsr.java8;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.adoptajsr.java8.Purchases.Purchase;

public abstract class Recommender {
    
    public static void main (String[] args) {
        Purchases purchases = loadPurchases();
        final int maxProductId = purchases.getMaxProductId();
        Random random = new Random();
        List<Integer> others = new ArrayList<>();
        Arrays.asList(new Java8Recommender()).forEach(recommender -> {
            recommender.inject(purchases);
            for (int i = 0; i < 10; i++) {
                long time = System.currentTimeMillis();
                recommender.preprocess();
                for (int j = 0; j < maxProductId; j += 2) {
                    List<Integer> recommendations = recommender.alsoBought(j, i);
                    
                    // Avoid being DCE'd
                    if (random.nextDouble() < 0.01)
                        others.addAll(recommendations);
                }
                long timeTaken = System.currentTimeMillis() - time;
                System.out.println("TIME : " + timeTaken);
                others.clear();
            }
        });
    }

    private static Purchases loadPurchases() {
        String file = Recommender.class.getResource("datafile").getFile();
        Purchases coincidences = new Purchases();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            while (true) {
                String[] line = reader.readNext();
                if (line == null)
                    return coincidences;
                
                int userId = Integer.parseInt(line[0].trim());
                int productId = Integer.parseInt(line[1].trim());
                coincidences.addPurchase(new Purchase(userId, productId));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public abstract List<Integer> alsoBought(int item, int number);

    public abstract void inject(Purchases coincidences);

    public abstract void preprocess();

}
