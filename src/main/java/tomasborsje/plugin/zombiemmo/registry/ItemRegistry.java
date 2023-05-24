package tomasborsje.plugin.zombiemmo.registry;

import tomasborsje.plugin.zombiemmo.items.BaseballBatItem;
import tomasborsje.plugin.zombiemmo.items.MakeshiftBandageItem;
import tomasborsje.plugin.zombiemmo.items.CustomItem;

public class ItemRegistry {
    public static final Registry<CustomItem> ITEMS = new Registry<>();
    public static final CustomItem MAKESHIFT_BANDAGE = ITEMS.register(new MakeshiftBandageItem());
    public static final CustomItem BASEBALL_BAT = ITEMS.register(new BaseballBatItem());
}
