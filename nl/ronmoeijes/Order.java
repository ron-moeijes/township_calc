package nl.ronmoeijes;

import java.util.HashMap;
import java.util.Map;

class Order {
    Order(Object... entries) {
        if (entries.length > 1) {
            Map<Product, Integer> products = new HashMap<>();
            for (int i = 0; i < entries.length; i += 2) {
                if (entries[i] instanceof Product && entries[i + 1] instanceof Integer) {
                    Product ingredient = (Product) entries[i];
                    int amount = (Integer) entries[i + 1];
                    products.put(ingredient, amount);
                } else if (!(entries[i] instanceof Product)) {
                    throw new IllegalArgumentException("Your key with index [" + i + "] was not a (valid) Product");
                } else if (!(entries[i + 1] instanceof Integer)) {
                    throw new IllegalArgumentException("Your value with index [" + (i + 1) + "] was not an int");
                }
            }
            System.out.println(products);
        } else {
            throw new IllegalArgumentException("This constructor requires at least one Product and one Integer");
        }
    }
}
