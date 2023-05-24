package tomasborsje.plugin.zombiemmo.events;

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.zombiemmo.items.CustomItem;
import tomasborsje.plugin.zombiemmo.registry.ItemRegistry;

public class CustomItemUseListener implements Listener {

    @EventHandler
    public void OnPlayerInteract(PlayerInteractEvent interactEvent) {

        // If this is not a right click, we ignore.
        if (!(interactEvent.getAction() == Action.RIGHT_CLICK_AIR || interactEvent.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        ItemStack usedItem = interactEvent.getItem();
        // Get NBT if it exists
        NBTTagCompound nbt = CraftItemStack.asNMSCopy(usedItem).u();

        // Don't handle vanilla items or items without NBT
        if(nbt == null) {
            return;
        }

        // Check if the item has our custom ITEM_ID NBT tag
        if (nbt.e("ITEM_ID")) {
            // Get our custom item id
            String itemId = nbt.l("ITEM_ID");
            // Get custom item from registry and apply the item's OnUse event
            CustomItem customItem = ItemRegistry.ITEMS.get(itemId);
            // If we can use the item, use it
            if(customItem.canUse(usedItem, interactEvent)) {
                customItem.onUse(usedItem, interactEvent);
            }
        }
    }
}
