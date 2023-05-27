package tomasborsje.plugin.zombiesmmo.registry;

import tomasborsje.plugin.zombiesmmo.items.basic.MakeshiftBandage;
import tomasborsje.plugin.zombiesmmo.items.basic.PistolAmmo;
import tomasborsje.plugin.zombiesmmo.items.core.BaseMeleeWeapon;
import tomasborsje.plugin.zombiesmmo.items.core.CustomItem;
import tomasborsje.plugin.zombiesmmo.items.guns.StandardPistol;

public class ItemRegistry {
    public static final Registry<CustomItem> ITEMS = new Registry<>();
    public static final CustomItem MAKESHIFT_BANDAGE = ITEMS.register(new MakeshiftBandage());
    public static final CustomItem BASEBALL_BAT = ITEMS.register(new BaseMeleeWeapon());
    public static final CustomItem STANDARD_PISTOL = ITEMS.register(new StandardPistol());
    public static final CustomItem PISTOL_AMMO = ITEMS.register(new PistolAmmo());
}
