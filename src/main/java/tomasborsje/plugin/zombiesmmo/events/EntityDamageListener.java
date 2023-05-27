package tomasborsje.plugin.zombiesmmo.events;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;
import tomasborsje.plugin.zombiesmmo.nms.HologramSpawner;
import tomasborsje.plugin.zombiesmmo.registry.ItemRegistry;
import tomasborsje.plugin.zombiesmmo.registry.ItemType;

public class EntityDamageListener implements Listener {
    private static final float DAMAGE_NUMBER_RADIUS = 0.5f;

    @EventHandler
    public void EntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof CraftArmorStand) { event.setCancelled(true); return; }
        if(event.getDamager() instanceof CraftPlayer player && event.getEntity() instanceof LivingEntity livingEntity) {
            // Get item in use NBT
            CompoundTag nbt = CraftItemStack.asNMSCopy(player.getItemInHand()).getTag();

            // We don't allow players to deal damage unless they use charged attacks
            // Prevents spam clicking, we want realism :)
            if(nbt.contains("ITEM_ID")
                    && ItemRegistry.ITEMS.get(nbt.getString("ITEM_ID")).getType() == ItemType.WEAPON
                    && player.getAttackCooldown() < MINIMUM_ATTACK_THRESHOLD) {
                event.setCancelled(true);
                return;
            }
            // Round damage to ints
            event.setDamage(Math.round(event.getDamage()));

            // Calculate damage marker position
            double randomAngle = Math.random()*Math.PI*2;
            Vector offset = new Vector(Math.cos(randomAngle)*DAMAGE_NUMBER_RADIUS, Math.random()/2f-0.25f, Math.sin(randomAngle)*DAMAGE_NUMBER_RADIUS);

            // Show damage marker
            HologramSpawner.SpawnTemporaryHologram(event.getEntity().getLocation().add(offset),ChatColor.GOLD+String.format("%.0f", event.getDamage()), 20);

            // Disable invincibility frames (or else guns, etc don't work)
            livingEntity.setNoDamageTicks(0);
        }
    }

    public final static float MINIMUM_ATTACK_THRESHOLD = 0.80f;
}
