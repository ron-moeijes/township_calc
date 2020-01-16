package nl.ronmoeijes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static nl.ronmoeijes.CustomUtils.createName;

class Product {
    private final String id;
    private final String name;
    private final int time;
    private final int price;
    private final Map<Product, Integer> ingredients;

    private int tier;
    private int totalTime;
    private int totalPrice;
    private Map<Product, Integer> totalIngredients;
    private BiFunction<Integer, Integer, Integer> add = Integer::sum;

    // Crop constructor
    Product(String id, int time, int price){
        this(id, time, price, new HashMap<>());
    }

    // Product constructor
    Product(String id, int time, Map<Product, Integer> ingredients) {
        this(id, time, 0, ingredients);
    }

    // Main product constructor, never directly used
    private Product(String id, int time, int price, Map<Product, Integer> ingredients) {
        this.id = id;
        this.name = createName(id);
        this.time = time;
        this.price = price;
        this.ingredients = ingredients;
        setTier();
        setTotalTime();
        setTotalPrice();
    }

    public String toString() {
        return name;
    }

    private String getId() {
        return id;
    }

    private String getName() {
        return name;
    }

    public Map<Product, Integer> getIngredients() {
        return ingredients;
    }

    private void setTier() {
        if (hasIngredients()) {
            tier = 1;
            for (Map.Entry<Product, Integer> ingredient : ingredients.entrySet()) {
                while (tier <= ingredient.getKey().getTier()) {
                    tier++;
                }
            }
        } else {
            tier = 0;
        }
    }

    private int getTier() {
        return tier;
    }

    private void setTotalTime() {
        if (hasIngredients()) {
            for (Map.Entry<Product, Integer> ingredient : ingredients.entrySet()) {
                totalTime += ingredient.getKey().getTotalTime() * ingredient.getValue();
            }
            totalTime += time;
        } else {
            totalTime = time;
        }
    }

    private int getTotalTime() {
        return totalTime;
    }

    private void setTotalPrice() {
        if (hasIngredients()) {
            for (Map.Entry<Product, Integer> ingredient : ingredients.entrySet()) {
                totalPrice += ingredient.getKey().getTotalPrice() * ingredient.getValue();
            }
        } else {
            totalPrice = price;
        }
    }

    private int getTotalPrice() {
        return totalPrice;
    }

    private boolean hasIngredients() {
        return ingredients != null;
    }

    private void setTotalIngredients() {
        totalIngredients = getTotalIngredients();
    }

    private Map<Product, Integer> getTotalIngredients(Map<Product, Integer> totalIngredients, int multi) {
        System.out.println(getId() + getTier() + hasIngredients() + multi);
        if (hasIngredients()) {
            for (Map.Entry<Product, Integer> ingredient : ingredients.entrySet()) {
                totalIngredients.merge(ingredient.getKey(), multi * ingredient.getValue(), add);
                System.out.println(totalIngredients);
                if (ingredient.getKey().hasIngredients()) {
                    ingredient.getKey().getTotalIngredients(totalIngredients, multi * ingredient.getValue());
                }
            }
        }
//            for (Map.Entry<Product, Integer> ingredient : ingredients.entrySet()) {
//                System.out.println(ingredient.getValue() + "x " + ingredient.getKey().getName() + " Tier = " + ingredient.getKey().getTier());
//                totalIngredients.put(ingredient.getKey(), ingredient.getValue());
//                System.out.println(totalIngredients);
//                if (ingredient.getKey().hasIngredients()) {
//                    ingredient.getKey().getTotalIngredients(totalIngredients, 1);
//                }
//            }
//        }
        return totalIngredients;
    }

    private Map<Product, Integer> getTotalIngredients() {
        return getTotalIngredients(new HashMap<>(), 1);
    }

    void getIngredientTree() {
        setTotalIngredients();
        if (totalIngredients.isEmpty()) {
            System.out.println("The product \'" + name + "\' is a crop and has no ingredients!");
        } else {
            System.out.println("A \'" + name + "\' contains the following ingredients:");
            for (Map.Entry<Product, Integer> ingredient : totalIngredients.entrySet()) {
                System.out.println(ingredient.getValue() + "x " + ingredient.getKey().getName());
            }
        }
    }

}
