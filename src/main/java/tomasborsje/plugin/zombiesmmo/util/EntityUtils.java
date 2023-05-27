package tomasborsje.plugin.zombiesmmo.util;

import net.minecraft.world.entity.LivingEntity;
import org.bukkit.ChatColor;
public class EntityUtils {
    public static String GetHealthBarMessage(net.minecraft.world.entity.LivingEntity entity) {
        return ChatColor.RED+"♥"+" "+(int)Math.ceil(entity.getHealth())+"/"+(int)entity.getMaxHealth();
    }
    public static String GetHealthBarMessage(org.bukkit.entity.LivingEntity entity) {
        return ChatColor.RED+"♥"+" "+(int)Math.ceil(entity.getHealth())+"/"+(int)entity.getMaxHealth();
    }
}
