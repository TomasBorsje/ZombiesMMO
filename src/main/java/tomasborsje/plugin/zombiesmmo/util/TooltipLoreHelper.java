package tomasborsje.plugin.zombiesmmo.util;

import org.bukkit.ChatColor;
import tomasborsje.plugin.zombiesmmo.items.core.CustomItem;
import tomasborsje.plugin.zombiesmmo.items.core.Rarity;

public class TooltipLoreHelper {
    public static String GetItemQualityTooltip(CustomItem item) {
        return GetItemRarityColor(item.getRarity())+""+ChatColor.BOLD+"-- " + item.getRarity().toString() + " " + item.getType().toString() + " --";
    }
    public static ChatColor GetItemRarityColor(Rarity rarity) {
        switch(rarity) {
            case SCRAP -> {
                return ChatColor.GRAY;
            }
            case COMMON -> {
                return ChatColor.WHITE;
            }
            case UNCOMMON -> {
                return ChatColor.GREEN;
            }
            case RARE -> {
                return ChatColor.BLUE;
            }
            case EPIC -> {
                return ChatColor.DARK_PURPLE;
            }
            case LEGENDARY -> {
                return ChatColor.GOLD;
            }
        }
        return ChatColor.WHITE;
    }
}
