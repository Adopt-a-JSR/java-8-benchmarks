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

/**
 * @author richard
 */
public class CoBuy {
    
    private final int productId1;
    private final int productId2;

    public CoBuy(int x, int y) {
        this.productId1 = x;
        this.productId2 = y;
    }

    @Override
    public String toString() {
        return "[" + getProductId1() + "," + getProductId2() + "]";
    }

    /**
     * @return the productId1
     */
    public int getProductId1() {
        return productId1;
    }

    /**
     * @return the productId2
     */
    public int getProductId2() {
        return productId2;
    }
    
}
