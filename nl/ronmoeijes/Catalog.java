package nl.ronmoeijes;

import java.util.HashMap;
import java.util.Map;

class Catalog {
    private static Map<String, Product> productCatalog = new HashMap<>();

    static Map<String, Product> generateCatalog() {
        // Crops
        addCrop("wheat", 2, 5);
        addCrop("corn", 5, 1);
        addCrop("carrot", 10, 2);
        addCrop("sugarcane", 20, 3);
        addCrop("cotton", 30, 4);
        addCrop("strawberry", 60, 5);
        addCrop("tomato", 120, 6);
        addCrop("pine_tree", 180, 7);

        // Tier 1
        addProduct("chicken_feed", 10, setIngredients("wheat", 2, "carrot", 1));
        addProduct("bread", 5, setIngredients("wheat", 2));

        addProduct("sugar", 20, setIngredients("sugarcane", 1));
        addProduct("egg", 60, setIngredients("chicken_feed", 1));
        addProduct("bagel", 30, setIngredients("wheat", 2, "sugar", 1, "egg", 3));
        return productCatalog;
    }

    private static void addCrop(String product, int time, int price) {
        productCatalog.put(product, new Product(product, time, price));
    }

    private static void addProduct(String product, int time, Map<Product, Integer> ingredients) {
        productCatalog.put(product, new Product(product, time, ingredients));
    }

    private static Product getProduct(String product) {
        return productCatalog.get(product);
    }

    private static Map<Product, Integer> setIngredients(Object... entries) {
        Map<Product, Integer> ingredients = new HashMap<>();
        for (int i = 0; i < entries.length; i += 2) {
            if (getProduct((String) entries[i]) != null && entries[i + 1] instanceof Integer) {
                Product product = getProduct((String) entries[i]);
                int amount = (Integer) entries[i + 1];
                ingredients.put(product, amount);
            } else if (getProduct((String) entries[i]) == null) {
                throw new IllegalArgumentException("Your key with index [" + i + "] was not a (valid) Product");
            } else if (!(entries[i + 1] instanceof Integer)) {
                throw new IllegalArgumentException("Your value with index [" + (i + 1) + "] was not an int");
            }
        }
        return ingredients;
    }
    
}
