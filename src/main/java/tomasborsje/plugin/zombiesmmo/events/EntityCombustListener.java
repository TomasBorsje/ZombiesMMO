package tomasborsje.plugin.zombiesmmo.events;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.inventory.ItemStack;

public class EntityCombustListener implements Listener {
    @EventHandler
    public void OnEntityCombust(EntityCombustEvent event) {
        if(event instanceof EntityCombustByBlockEvent || event instanceof EntityCombustByEntityEvent) {
            // Don't handle these events.
            return;
        }

        // Check if mob wearing helmet
        if(event.getEntity() instanceof LivingEntity) {
            ItemStack helmet = ((LivingEntity) event.getEntity()).getEquipment().getHelmet();

            if(helmet != null && helmet.getType() != Material.AIR) {
                // Mob has a helmet, don't burn.
                return;
            }
        }

        // If it's a zombie or skeleton, don't set on fire.
        EntityType type = event.getEntity().getType();
        if(type == EntityType.ZOMBIE || type == EntityType.SKELETON) {
            event.setCancelled(true);
            return;
        }
    }
}
