package tomasborsje.plugin.zombiesmmo.items.guns;

import org.bukkit.Material;
import tomasborsje.plugin.zombiesmmo.items.core.BaseGun;
import tomasborsje.plugin.zombiesmmo.items.core.Rarity;
import tomasborsje.plugin.zombiesmmo.registry.AmmoType;
import tomasborsje.plugin.zombiesmmo.registry.ItemType;

public class StandardPistol extends BaseGun {

    public StandardPistol() {
        this.AMMO_TYPE = AmmoType.PISTOL_AMMO;
        this.PROJECTILE_DAMAGE = 7;
        this.CLIP_SIZE = 7;
        this.FIRE_SOUND_PITCH = 0.4f;
        this.FIRE_RATE = 8;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }

    @Override
    public ItemType getType() {
        return ItemType.GUN;
    }

    @Override
    public String getCustomId() {
        return "STANDARD_PISTOL";
    }

    @Override
    public String getLoreDescription() {
        return "A standard issue military pistol.";
    }

    @Override
    public String getDisplayName() {
        return "Standard Pistol";
    }

    @Override
    public Material getBaseMaterial() {
        return Material.STONE_HOE;
    }
}
