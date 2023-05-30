package tomasborsje.plugin.zombiesmmo.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.zombiesmmo.entities.FakePlayer;
import tomasborsje.plugin.zombiesmmo.nms.FakePlayerUtils;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for(FakePlayer npc : NPCTickHandler.npcs) {
            npc.spawnFor(player);
            Bukkit.broadcastMessage("Loaded "+npc.getCustomId() + " for player at yaw "+npc.getDefaultYaw());
        }
    }
}
