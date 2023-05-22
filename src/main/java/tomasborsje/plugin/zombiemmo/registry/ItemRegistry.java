package tomasborsje.plugin.zombiemmo.registry;

import tomasborsje.plugin.zombiemmo.items.MakeshiftBandageItem;
import tomasborsje.plugin.zombiemmo.items.CustomItem;

public class ItemRegistry {
    public static final Registry<CustomItem> ITEMS = new Registry<>();

    public static final CustomItem MAKESHIFT_BANDAGE = ITEMS.register(MakeshiftBandageItem.ID, new MakeshiftBandageItem());
}
