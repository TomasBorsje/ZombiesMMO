package tomasborsje.plugin.zombiesmmo.inventories;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.zombiesmmo.registry.ItemRegistry;

public class CupboardInventory extends CustomInventory{
    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public String getInventoryName() {
        return "Cupboard";
    }

    @Override
    public void fill(Inventory inv) {
        for (int i = 0; i < inv.getSize(); i++) {
            // 10% chance for carrot
            if(Math.random() < 0.1f) {
                inv.setItem(i, new ItemStack(Material.CARROT));
            }
            // Otherwise 10% chance for potato
            else if(Math.random() < 0.1f) {
                inv.setItem(i, new ItemStack(Material.POTATO));
            }
            // Otherwise 20% chance for poisonous potato
            else if(Math.random() < 0.2f) {
                inv.setItem(i, new ItemStack(Material.POISONOUS_POTATO));
            }
        }
    }

    @Override
    public String getCustomId() {
        return "CUPBOARD";
    }
}
