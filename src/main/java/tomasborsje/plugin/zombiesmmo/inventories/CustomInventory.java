package tomasborsje.plugin.zombiesmmo.inventories;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import tomasborsje.plugin.zombiesmmo.registry.IHasId;

public abstract class CustomInventory implements IHasId {

    public int getSize() {
        return 9;
    }

    public String getInventoryName() {
        return "Unknown";
    }

    public Inventory generateNewInstance() {
        Inventory newInv = Bukkit.createInventory(null, getSize(), getInventoryName());
        fill(newInv);
        return newInv;
    }

    @Override
    public String getCustomId() {
        return "UNKNOWN";
    }

    /**
     * Fill the inventory with items as newly generated.
     */
    public void fill(Inventory inventory) { }

}
