package tomasborsje.plugin.zombiesmmo.items.basic;

import org.bukkit.*;
import tomasborsje.plugin.zombiesmmo.items.core.CustomItem;
import tomasborsje.plugin.zombiesmmo.items.core.Rarity;
import tomasborsje.plugin.zombiesmmo.registry.ItemType;

public class PistolAmmo extends CustomItem {
    @Override
    public String getCustomId() {
        return "PISTOL_AMMO";
    }
    @Override
    public String getDisplayName() {
        return "Pistol Ammo";
    }
    @Override
    public String getLoreDescription() {
        return "Civilian-grade 10mm ammunition.";
    }
    @Override
    public Material getBaseMaterial() {
        return Material.IRON_NUGGET;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.UNCOMMON;
    }

    @Override
    public ItemType getType() {
        return ItemType.AMMO;
    }

}
