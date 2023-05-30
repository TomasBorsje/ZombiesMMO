package tomasborsje.plugin.zombiesmmo.events;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import tomasborsje.plugin.zombiesmmo.entities.FakePlayer;

public class PlayerInteractEntityListener implements Listener {
    @EventHandler
    public void OnPlayerInteractEntity(PlayerInteractAtEntityEvent event) {
        if(event.getHand() != EquipmentSlot.HAND) {return;}

        Entity clickedEntity = event.getRightClicked();
        // Check if entity is one of our NPCs
        for(FakePlayer npc : NPCTickHandler.npcs) {
            if(npc.isEntity(clickedEntity)) {
                npc.OnInteracted(event.getPlayer());
                event.setCancelled(true);
                return;
            }
        }
    }
}
