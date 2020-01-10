package nl.ronmoeijes;

//import static nl.ronmoeijes.Catalog.initialize;

import java.util.Map;

import static nl.ronmoeijes.Catalog.generateCatalog;

class Main {
    static final boolean DEBUG = false;
    public static void main(String[] args) {
        Map<String, Product> productCatalog = generateCatalog();
        productCatalog.get("bagel").printCrops();
    }
}
