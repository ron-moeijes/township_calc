package nl.ronmoeijes;

import java.util.HashMap;
import java.util.Map;

import static nl.ronmoeijes.Product.setIngredients;

class Catalog {
    private static final Map<String, Product> productCatalog = new HashMap<>();
    static Map<String, Product> generateCatalog() {
        // Crops
        Product wheat = new Product("wheat", 2, 0); productCatalog.put(wheat.getId(), wheat);
        Product corn = new Product("corn", 5, 1); productCatalog.put(corn.getId(), corn);
        Product carrot = new Product("carrot", 10, 2); productCatalog.put(carrot.getId(), carrot);
        Product sugarcane = new Product("sugarcane",20,3); productCatalog.put(sugarcane.getId(), sugarcane);
        Product cotton = new Product("cotton", 30, 4); productCatalog.put(cotton.getId(), cotton);
        Product strawberry = new Product("strawberry", 60, 5); productCatalog.put(strawberry.getId(), strawberry);
        Product tomato = new Product("tomato", 120, 6); productCatalog.put(tomato.getId(), tomato);
        Product pine_tree = new Product("pine_tree", 180, 7); productCatalog.put(pine_tree.getId(), corn);

        // Tier 1
        Product chicken_feed = new Product("chicken_feed", 10, setIngredients(wheat, 2, carrot, 1)); productCatalog.put(chicken_feed.getId(), chicken_feed);
        Product bread = new Product("bread", 5, setIngredients(wheat, 2)); productCatalog.put(bread.getId(), bread);
        
        Product sugar = new Product("sugar",20, setIngredients(sugarcane, 1)); productCatalog.put(sugar.getId(), sugar);
        Product egg = new Product("egg", 60, setIngredients(chicken_feed, 1)); productCatalog.put(egg.getId(), egg);
        Product bagel = new Product("bagel", 30, setIngredients(wheat, 2, sugar, 1, egg, 3)); productCatalog.put(bagel.getId(), bagel);
        return productCatalog;
    }

}
