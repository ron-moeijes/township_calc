package nl.ronmoeijes;

import java.util.*;

import static nl.ronmoeijes.Main.DEBUG;
import static nl.ronmoeijes.Name.createName;

class Product {
    final String name;
    private final String id;
    private final boolean isCrop;
    private int price;
    private final int time;
    private final Map<Object, Object> ingredients;

    Product(String id, int time, int price){
        this(id, time, price, new HashMap<>());
    }

    Product(String id, int time, Map<Object, Object> ingredients){
        this(id, time, 0, ingredients);
    }

    private Product(String id, int time, int price, Map<Object, Object> ingredients){
        this.id = id;
        this.name = createName(id);
        this.price = price;
        this.time = time;
        this.ingredients = ingredients;
        if (!ingredients.isEmpty()){
            isCrop = false;
            for(Map.Entry<Object, Object> entry: ingredients.entrySet()) {
                this.price += (Integer) entry.getValue();
            }
        } else { isCrop = true; }
    }

    public String toString() {
        return name;
    }

    String getId() {
        return id;
    }

//    private int totalTime(){
//        int total_time = time;
//        for (Ingredient i : ingredients) {
//            total_time+=i.type.time;
//        }
//        return total_time;
//    }

    private Map<String, Integer> determineCrops(Map<String, Integer> crops, int multi, int depth) {
        for(Map.Entry<Object, Object> entry: ingredients.entrySet()) {
            if (depth == 0) { multi = 1; }
            String str = entry.getKey().toString();
            Product p = (Product) entry.getKey();
            if (DEBUG) {
                System.out.println("Current key = " + str);
                System.out.println("Current value = " + entry.getValue());
                System.out.println("Current multiplier = " + multi);
                System.out.println("Is a crop = " + p.isCrop);
                System.out.println("Already in crops " + crops.containsKey(str));
            }
            if (p.isCrop && !crops.containsKey(str)) {
                crops.put(str, (Integer) entry.getValue()*multi);
            } else if (crops.containsKey(str)) {
                    crops.replace(str, crops.get(str) + (Integer) entry.getValue()*multi);
            } else if (!p.ingredients.isEmpty()){
                multi *= (Integer) entry.getValue();
                depth += 1;
                if (DEBUG) {
                    System.out.println(">>Going inside \"" + str + "\"-loop...");
                    System.out.println(">>Current depth: " + depth);
                }
                p.determineCrops(crops, multi, depth);
            }
            if (depth > 0) { depth -= 1; }
            if (DEBUG) {
                System.out.println("<<\"" + str + "\"-loop completed, results: " + crops);
                System.out.println("<<Current depth: " + depth);
                System.out.println("------------------------------------------------");
            }
        }
        return crops;
    }

    void printCrops() {
        Map<String, Integer> crops = determineCrops(new HashMap<>(), 1, 0);
        System.out.println(crops);
    }

    static Map<Object, Object> setIngredients(Object ... l) {
        Map<Object, Object> ingredients = new HashMap<>();
        for (int i = 0;i < l.length;i+=2) {
            if (l[i] instanceof Product && l[i+1] instanceof Integer) {
                ingredients.put(l[i], l[i + 1]);
            } else if (!(l[i] instanceof Product)) { throw new IllegalArgumentException("Your key was not a Product");
            } else if (!(l[i+1] instanceof Integer)) { throw new IllegalArgumentException("Your value was not an int"); }
        }
        return ingredients;
    }

    void printProperties(){
        System.out.println("Name: "+name);
        System.out.println("ID: "+id);
//        System.out.println("Tier: "+tier);
        System.out.println("Price: "+price);
        System.out.println("Production time: "+time+"m");
//        System.out.println("Total production time: "+totalTime()+"m");
        System.out.println("Ingredients: ");
        for(Map.Entry<Object, Object> entry: ingredients.entrySet()) {
            System.out.println(entry.getValue()+"x "+entry.getKey());
        }
    }
}
