import java.util.HashMap;
import java.util.Map;

class InsufficientInventory extends Exception {
    public InsufficientInventory(int currentInventory, int requestedInventory) {
        super("There is not enough inventory for this product. " +
                "Current Inventory: " + currentInventory + " Needed: " + requestedInventory);
    }
}

public class Inventory {

    Map<String, Product> products = new HashMap<>();

    void addProduct(String productId, double price, int quantity) {
        if (inStock(productId)) {
            Product p = products.get(productId);
            p.addStock(quantity);
            if (p.getPrice() != price) {
                p.setPrice(price);
            }
        } else {
            products.put(productId,new Product(productId, price, quantity));
        }
    }

    void removeProduct(String productId, int quantity) throws InsufficientInventory {
        if (inStock(productId)) {
            Product p = products.get(productId);
            p.setQuantity(quantity);
             if (p.getQuantity() == 0) {
                products.remove(productId);
             } else if (p.getQuantity() < 0) {
                 throw new InsufficientInventory(p.getQuantity(), quantity);
             }

        } else {
            throw new InsufficientInventory(0, quantity);
        }
    }

    double totalInventoryValue() {
        double total = 0;
        for (Product p :products.values()) {
            total += p.getQuantity()*p.getPrice();
        }
        return total;
    }

    boolean inStock(String productId) {
        return products.containsKey(productId);
    }

    String getAllProductNames() {
        return String.join(", ", products.keySet());
    }

    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        inventory.addProduct("milk", 3.5, 1);
        inventory.addProduct("banana", .6, 1);

        System.out.println(inventory.getAllProductNames());
        System.out.println(inventory.totalInventoryValue());
        System.out.println(inventory.inStock("banana"));
    }
}
