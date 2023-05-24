package tomasborsje.plugin.zombiesmmo.registry;

import org.bukkit.ChatColor;
import tomasborsje.plugin.zombiesmmo.items.CustomItem;
import tomasborsje.plugin.zombiesmmo.items.Rarity;

import java.util.List;

public class TooltipLoreHelper {
    public static void AppendItemQualityTooltip(CustomItem item, List<String> tooltip) {
        tooltip.add("");
        tooltip.add(GetItemRarityColor(item.getRarity())+""+ChatColor.BOLD+"-- " + item.getRarity().toString() + " " + item.getType().toString() + " --");
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
