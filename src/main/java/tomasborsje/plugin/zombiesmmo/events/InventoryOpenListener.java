package tomasborsje.plugin.zombiesmmo.events;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventoryCustom;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.zombiesmmo.inventories.InventoryManager;
import tomasborsje.plugin.zombiesmmo.registry.ItemRegistry;

import java.util.ArrayList;

public class InventoryOpenListener implements Listener {

    static ArrayList<Inventory> customInventories = new ArrayList<>();

    @EventHandler
    public void OnInventoryOpen(InventoryOpenEvent event) {
        // Don't open normal inventories
        if(event.getInventory().getHolder() == null) {
            return;
        }

        event.setCancelled(true);
        try {
            // Replace with our custom loot inventory!
            Inventory customInv = InventoryManager.getInventory(event.getPlayer().getName(), event.getInventory().getLocation().toVector());
            if(customInv != null) {
                event.getPlayer().openInventory(customInv);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
