package tomasborsje.plugin.zombiemmo.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomasborsje.plugin.zombiemmo.registry.ItemType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BaseballBatItem extends CustomItem {
    public final float ATTACK_SPEED = 0.5f;

    @Override
    public Material getBaseMaterial() {
        return Material.WOODEN_SWORD;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.COMMON;
    }

    @Override
    public ItemType getType() {
        return ItemType.WEAPON;
    }

    @Override
    public String getId() {
        return "BASEBALL_BAT";
    }


    @Override
    public String getDisplayName() {
        return "Baseball Bat";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<String>(Arrays.asList(ChatColor.RED + "\uD83D\uDDE1 Damage: " + ChatColor.WHITE + "7", ChatColor.YELLOW + "☄ Attack Speed: " + ChatColor.WHITE + "5", "", ChatColor.DARK_GRAY + "A bat once used to play baseball."));
    }

    @Override
    public ItemStack createStack() {
        ItemStack stack = super.createStack();

        ItemMeta meta = stack.getItemMeta();
        AttributeModifier attackSpeed = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", 1f/ATTACK_SPEED, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeed);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);

        return stack;
    }
}
