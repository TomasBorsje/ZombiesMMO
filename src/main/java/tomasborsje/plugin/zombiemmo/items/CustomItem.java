package tomasborsje.plugin.zombiemmo.items;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomasborsje.plugin.zombiemmo.registry.IHasId;
import tomasborsje.plugin.zombiemmo.registry.ItemType;
import tomasborsje.plugin.zombiemmo.registry.TooltipLoreHelper;

import java.util.Collections;
import java.util.List;

public abstract class CustomItem implements IHasId {
    public Rarity getRarity() {
        return Rarity.COMMON;
    }
    public ItemType getType() {
        return ItemType.UNKNOWN;
    }
    public String getId() {
        return "EMPTY";
    }
    public List<String> getDescription() {
        return Collections.singletonList("Unknown");
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
        nbt.putString("ITEM_ID", this.getId());
        // Set nbt to the nmsStack
        nmsStack.setTag(nbt);

        // Bring NMS stack back to spigot level
        stack = CraftItemStack.asBukkitCopy(nmsStack);

        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(TooltipLoreHelper.GetItemRarityColor(getRarity()) + getDisplayName());
        List<String> lore = getDescription();
        TooltipLoreHelper.AppendItemQualityTooltip(this, lore);
        meta.setLore(lore);
        stack.setItemMeta(meta);

        return stack;
    }

    public void onUse(ItemStack itemStack, PlayerInteractEvent event) { };
    public boolean canUse(ItemStack itemStack, PlayerInteractEvent event) { return false; };
}
