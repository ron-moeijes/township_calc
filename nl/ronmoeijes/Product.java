package nl.ronmoeijes;

import java.util.HashMap;
import java.util.Map;

import static nl.ronmoeijes.Main.DEBUG;
import static nl.ronmoeijes.Name.createName;

class Product {
    private final String id;
    private final Map<Product, Integer> ingredients;
    private int price;
    private final String name;
    private int time;
    private int tier;
    private Map<Product, Integer> crops;

    // Crop constructor
    Product(String id, int time, int price){
        this(id, time, price, new HashMap<>());
    }

    // Product constructor
    Product(String id, int time, Map<Product, Integer> ingredients) {
        this(id, time, 0, ingredients);
    }

    private Product(String id, int time, int price, Map<Product, Integer> ingredients) {
        this.id = id;
        this.name = createName(id);
        this.price = price;
        this.time = time;
        this.ingredients = ingredients;
        if (!ingredients.isEmpty()){
            updateTime();
            updatePrice();
            tier = 1;
            updateTier();
            crops = determineCrops(new HashMap<>(), 1, 0);
        } else {
            tier = 0;
        }
    }

    public String toString() {
        return name;
    }

    private void updateTime() {
        for (Map.Entry<Product, Integer> ingredient : ingredients.entrySet()) {
            this.time += ingredient.getKey().time * ingredient.getValue();
        }
    }

    private void updatePrice() {
        for (Map.Entry<Product, Integer> ingredient : ingredients.entrySet()) {
            this.price += ingredient.getKey().price * ingredient.getValue();
        }
    }

    private void updateTier() {
        for (Map.Entry<Product, Integer> ingredient : ingredients.entrySet()) {
            if (ingredient.getKey().tier > this.tier) {
                this.tier = ingredient.getKey().tier;
            }
        }
    }

    private String getId() {
        return id;
    }

//    private int totalTime(){
//        int total_time = time;
//        for (Ingredient i : ingredients) {
//            total_time+=i.type.time;
//        }
//        return total_time;
//    }

    private Map<Product, Integer> determineCrops(Map<Product, Integer> crops, int multi, int depth) {
        if (DEBUG) {
            System.out.println(name);
            System.out.println("================================================");
        }
        for (Map.Entry<Product, Integer> ingredient : ingredients.entrySet()) {
            if (depth == 0) { multi = 1; }
            Product product = ingredient.getKey();
            if (DEBUG) {
                System.out.println("Current ingredient = " + ingredient);
                System.out.println("Current key (Product) = " + product.getId());
                System.out.println("Current value (amount) = " + ingredient.getValue());
                System.out.println("Current multiplier = " + multi);
                System.out.println("Current tier = " + product.tier);
                System.out.println("Is a crop = " + ((product.tier == 0) ? "True" : "False"));
                System.out.println("Already in crops = " + crops.containsKey(product));
            }
            if (product.tier == 0 && !crops.containsKey(product)) {
                crops.put(product, ingredient.getValue() * multi);
            } else if (crops.containsKey(product)) {
                crops.replace(product, crops.get(product) + ingredient.getValue() * multi);
            } else if (!product.ingredients.isEmpty()) {
                multi *= ingredient.getValue();
                depth += 1;
                if (DEBUG) {
                    System.out.println(">>Going inside \"" + product.getId() + "\"-loop...");
                    System.out.println(">>Current depth: " + depth);
                }
                product.determineCrops(crops, multi, depth);
            }
            if (depth > 0) { depth -= 1; }
            if (DEBUG) {
                System.out.println("<<\"" + product.getId() + "\"-loop completed, results: " + crops);
                System.out.println("<<Current depth: " + depth);
                System.out.println("------------------------------------------------");
            }
        }
        return crops;
    }

    void printCrops() {
        System.out.println(crops);
    }

    void printAttributes() {
        System.out.println("Name: "+name);
        System.out.println("ID: "+id);
        System.out.println("Tier: " + tier);
        System.out.println("Price: "+price);
        System.out.println("Production time: "+time+"m");
//        System.out.println("Total production time: "+totalTime()+"m");
        System.out.println("Ingredients: ");
        for (Map.Entry<Product, Integer> ingredient : ingredients.entrySet()) {
            System.out.println(ingredient.getValue() + "x " + ingredient.getKey());
        }
    }
}
