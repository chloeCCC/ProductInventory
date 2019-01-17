import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class InsufficientInventory extends Exception {
    public InsufficientInventory(int currentInventory, int requestedInventory) {
        super("There is not enough inventory for this product. " +
                "Current Inventory: " + currentInventory + " Needed: " + requestedInventory);
    }
}

public class Inventory {
    private Set<Product> products = new HashSet<>();

    void addProduct(String productId, double price, int quantity) {
        if (inStock(productId)) {
            Product p = getProduct(productId);
            p.addStock(quantity);
            if (p.getPrice() != price) {
                p.setPrice(price);
            }
        } else {
            products.add(new Product(productId, price, quantity));
        }
    }

    void removeProduct(String productId, int quantity) throws InsufficientInventory {
        if (inStock(productId)) {
            Product p = getProduct(productId);
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
        for (Product p : products) {
            total += p.getPrice() * p.getQuantity();
        }
        return total;
    }

    boolean inStock(String productId) {
        for (Product p : products) {
            if (p.getProductId().equals(productId)) {
                return true;
            }
        }
        return false;
    }

    Product getProduct(String productId) { ;
        for (Product p : products) {
            if (p.getProductId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    String getAllProductNames() {
        List<String> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getProductId());
        }
        return String.join(", ", productIds);
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
