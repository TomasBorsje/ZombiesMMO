package tomasborsje.plugin.zombiesmmo.events;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.zombiesmmo.items.CustomItem;
import tomasborsje.plugin.zombiesmmo.registry.ItemRegistry;

public class CustomItemUseListener implements Listener {

    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent interactEvent) {

        // If this is not a right click, we ignore.
        if (!(interactEvent.getAction() == Action.RIGHT_CLICK_AIR || interactEvent.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        ItemStack usedItem = interactEvent.getItem();

        // Get NBT if it exists
        CompoundTag nbt = CraftItemStack.asNMSCopy(usedItem).getTag();

        // Don't handle vanilla items or items without NBT
        if(nbt == null) {
            return;
        }

        // Check if the item has our custom ITEM_ID NBT tag
        if (nbt.contains("ITEM_ID")) {
            // Get our custom item id
            String itemId = nbt.getString("ITEM_ID");

            // If invalid id, ignore
            if(!ItemRegistry.ITEMS.hasKey(itemId)) { return; }

            // Get custom item from registry and apply the item's OnUse event
            CustomItem customItem = ItemRegistry.ITEMS.get(itemId);
            // If we can use the item, use it
            if(customItem.canUse(usedItem, interactEvent)) {
                customItem.onUse(usedItem, interactEvent);
            }
        }
    }
}
