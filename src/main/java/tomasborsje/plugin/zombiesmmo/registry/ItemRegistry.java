package tomasborsje.plugin.zombiesmmo.registry;

import tomasborsje.plugin.zombiesmmo.items.BaseballBatItem;
import tomasborsje.plugin.zombiesmmo.items.MakeshiftBandageItem;
import tomasborsje.plugin.zombiesmmo.items.CustomItem;

public class ItemRegistry {
    public static final Registry<CustomItem> ITEMS = new Registry<>();
    public static final CustomItem MAKESHIFT_BANDAGE = ITEMS.register(new MakeshiftBandageItem());
    public static final CustomItem BASEBALL_BAT = ITEMS.register(new BaseballBatItem());
}
