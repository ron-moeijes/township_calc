package nl.ronmoeijes;

import java.util.Map;

import static nl.ronmoeijes.Catalog.generateCatalog;

class Main {
    static final boolean DEBUG = true;
    public static void main(String[] args) {
        Map<String, Product> productCatalog = generateCatalog();
        new Order(productCatalog.get("bagel"), 1, productCatalog.get("egg"), 3);
    }
}
