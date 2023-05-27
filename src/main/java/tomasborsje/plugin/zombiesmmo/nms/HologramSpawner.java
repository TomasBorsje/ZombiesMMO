package tomasborsje.plugin.zombiesmmo.nms;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitScheduler;
import net.minecraft.world.entity.decoration.ArmorStand;
import tomasborsje.plugin.zombiesmmo.ZombiesMMO;

public class HologramSpawner {
    public static ArmorStand SpawnHologram(Location location, String message) {
        Level level = ((CraftWorld)location.getWorld()).getHandle();
        ArmorStand as = new ArmorStand(level, location.getX(), location.getY()+1.975, location.getZ());
        as.setMarker(true);
        as.setInvisible(true);
        as.setCustomNameVisible(true);
        as.setCustomName(CraftChatMessage.fromJSONOrString(message));
        level.addFreshEntity(as);
        return as;
    }

    /**
     * Spawns a hologram (floating text) at the given location for `duration` ticks.
     * @param location The location to center the text at.
     * @param message The text to display.
     * @param duration Number of ticks to display for.
     */
    public static void SpawnTemporaryHologram(Location location, String message, long duration) {
        ArmorStand armorStand = SpawnHologram(location, message);
        Bukkit.getScheduler().runTaskLater(ZombiesMMO.Plugin, armorStand::kill, duration);
    }
}
