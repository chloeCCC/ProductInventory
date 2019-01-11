import java.util.ArrayList;
import java.util.List;

class InsufficientInventory extends Exception {
    public InsufficientInventory(int currentInventory, int requestedInventory) {
        super("There is not enough inventory for this product. " +
                "Current Inventory: " + currentInventory + " Needed: " + requestedInventory);
    }
}

public class Inventory {
    private List<Product> products = new ArrayList<>();

    private int getProductIndex(String productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
                return i;
            }
        }

        return -1;
    }

    void addProduct(String productId, double price, int quantity) {
        int index = getProductIndex(productId);
        if (index >= 0) {
            Product product = products.get(index);
            product.addStock(quantity);
            if (product.getPrice() != price) {
                product.setPrice(price);
            }
        } else {
            products.add(new Product(productId, price, quantity));
        }
    }

    void removeProduct(String productId, int quantity) throws InsufficientInventory {
        for (int i = products.size() - 1; i >= 0; i--) {
            if(products.get(i).getProductId().equals(productId)) {
                products.get(i).setQuantity(quantity);
                if(products.get(i).getQuantity() == 0) {
                    products.remove(i);
                }else if(products.get(i).getQuantity() < 0) {
                    throw new InsufficientInventory(products.get(i).getQuantity(), quantity);
                }

            }else {
                throw new InsufficientInventory(0, quantity);
            }
        }
    }

    double totalInventoryValue() {
        double total = 0;
        for (int i = 0; i < products.size(); i++) {
            total += products.get(i).getPrice() * products.get(i).getQuantity();
        }
        return total;
    }

    boolean inStock(String productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId().equals(productId)) {
            return true;
            }
        }
        return false;
    }

    Product getProduct(String productId) {
        int index = getProductIndex(productId);
        if (index >= 0) {
            return products.get(index);
        } else {
            return null;
        }
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
