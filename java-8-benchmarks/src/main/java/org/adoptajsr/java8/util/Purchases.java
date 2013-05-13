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

package org.adoptajsr.java8.util;

import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Purchases {

    public static class Purchase {

        private final int userId;
        private final int productId;

        public Purchase(int userId, int productId) {
            this.userId = userId;
            this.productId = productId;
        }

        public int getUserId() {
            return userId;
        }

        public int getProductId() {
            return productId;
        }

    }

    private final List<Purchase> purchases;
    
    private int maxProductId;

    public Purchases() {
        maxProductId = Integer.MIN_VALUE;
        purchases = new ArrayList<>();
    }

    public static Purchases load() {
        String file = Purchases.class.getResource("datafile").getFile();
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
    
    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
        maxProductId = Math.max(maxProductId, purchase.productId);
    }

    public int getMaxProductId() {
        return maxProductId;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

}
