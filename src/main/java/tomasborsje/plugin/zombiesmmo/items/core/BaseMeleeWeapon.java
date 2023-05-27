package tomasborsje.plugin.zombiesmmo.items.core;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import tomasborsje.plugin.zombiesmmo.registry.ItemType;

import java.util.UUID;

public class BaseMeleeWeapon extends CustomItem {
    public final float ATTACK_SPEED_MULTIPLIER = 0.4f;
    public final float ATTACK_DAMAGE = 2.25f;

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
    public String getCustomId() {
        return "BASEBALL_BAT";
    }


    @Override
    public String getDisplayName() {
        return "Baseball Bat";
    }

    @Override
    public String getLoreDescription() {
        return ChatColor.RED + "\uD83D\uDDE1 Damage: " + ChatColor.WHITE + "7\n" +
                ChatColor.YELLOW + "☄ Attack Speed: " + ChatColor.WHITE + "5"+ "\n" +
                ChatColor.DARK_GRAY + "A bat once used to play baseball.";
    }
    @Override
    public ItemStack createStack() {
        ItemStack stack = super.createStack();

        ItemMeta meta = stack.getItemMeta();
        AttributeModifier attackSpeed = new AttributeModifier(UUID.randomUUID(), "generic.attackSpeed", ATTACK_SPEED_MULTIPLIER-1, AttributeModifier.Operation.MULTIPLY_SCALAR_1, EquipmentSlot.HAND);
        AttributeModifier attackDamage = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", ATTACK_DAMAGE-1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, attackSpeed);
        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, attackDamage);
        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        stack.setItemMeta(meta);

        return stack;
    }
}
