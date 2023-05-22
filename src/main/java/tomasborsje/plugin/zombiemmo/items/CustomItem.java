package tomasborsje.plugin.zombiemmo.items;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public interface CustomItem {
    public ItemStack Create();
    public void OnUse(ItemStack itemStack, PlayerInteractEvent event);
    public boolean CanUse(ItemStack itemStack, PlayerInteractEvent event);
}
