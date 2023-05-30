package tomasborsje.plugin.zombiesmmo.registry;

import tomasborsje.plugin.zombiesmmo.inventories.CupboardInventory;
import tomasborsje.plugin.zombiesmmo.inventories.CustomInventory;

public class InventoryRegistry {
    public static Registry<CustomInventory> INVENTORIES = new Registry<>();

    public static CustomInventory CUPBOARD = INVENTORIES.register(new CupboardInventory());
}
