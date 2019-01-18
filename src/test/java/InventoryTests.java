import org.junit.Assert;
import org.junit.Test;

public class InventoryTests {
    @Test
    public void addApples() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 2);

        Assert.assertEquals(2, inventory.products.get("apple").getQuantity());
    }

    @Test
    public void addMoreApples() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 1);
        inventory.addProduct("apple", .6, 2);

        Assert.assertEquals(3, inventory.products.get("apple").getQuantity());
    }

    @Test
    public void updatePrice() {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 1);
        inventory.addProduct("apple", .7, 2);

        Assert.assertEquals(.7, inventory.products.get("apple").getPrice(), .0001);
    }

    @Test(expected = InsufficientInventory.class)
    public void removeApples() throws InsufficientInventory {
        Inventory inventory = new Inventory();
        inventory.addProduct("apple", .6, 10);
        inventory.removeProduct("apple", 12);
    }
}
