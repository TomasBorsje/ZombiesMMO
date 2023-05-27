package tomasborsje.plugin.zombiesmmo.items.core;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomasborsje.plugin.zombiesmmo.registry.IHasId;
import tomasborsje.plugin.zombiesmmo.registry.ItemType;
import tomasborsje.plugin.zombiesmmo.util.TooltipLoreHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class CustomItem implements IHasId {
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    public ItemType getType() {
        return ItemType.UNKNOWN;
    }
    public String getCustomId() {
        return "EMPTY";
    }
    public String getLoreDescription() {
        return "";
    }
    public String getDisplayName() {
        return "Unknown Item";
    }

    public Material getBaseMaterial() {
        return Material.BARRIER;
    }
    public ItemStack createStack() {
        ItemStack stack = new ItemStack(getBaseMaterial());

        // Mappings not set up yet so use Bukkit item for paper
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);

        CompoundTag nbt = nmsStack.getOrCreateTag();

        // Add ITEM_ID tag
        nbt.putString("ITEM_ID", this.getCustomId());
        // Set nbt to the nmsStack
        nmsStack.setTag(nbt);

        // Bring NMS stack back to spigot level
        stack = CraftItemStack.asBukkitCopy(nmsStack);

        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TooltipLoreHelper.GetItemRarityColor(getRarity()) + getDisplayName());
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        List<String> lore = new ArrayList<String>();
        if(!getLoreDescription().equals("")) {
            lore.add(ChatColor.DARK_GRAY + getLoreDescription());
            lore.add("");
        }
        lore.add(TooltipLoreHelper.GetItemQualityTooltip(this));
        meta.setLore(lore);
        stack.setItemMeta(meta);

        return stack;
    }

    public void onRightClick(ItemStack itemStack, PlayerInteractEvent event) { };
    public boolean canRightClickUse(ItemStack itemStack, PlayerInteractEvent event) { return false; };
    public void onLeftClick(ItemStack itemStack, PlayerInteractEvent event) { };
    public boolean canLeftClickUse(ItemStack itemStack, PlayerInteractEvent event) { return false; };
}
