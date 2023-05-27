package tomasborsje.plugin.zombiesmmo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.zombiesmmo.entities.BaseCustomZombie;
import tomasborsje.plugin.zombiesmmo.registry.EntityRegistry;
import tomasborsje.plugin.zombiesmmo.registry.ItemRegistry;

public class ZombiesSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            String entityId = args[0].toUpperCase();
            int count = args.length == 2 ? Integer.parseInt(args[1]) : 1;
            if(!EntityRegistry.ZOMBIES.hasKey(entityId)) {
                player.sendMessage(ChatColor.RED+"No entity registered with ID "+entityId);
            }
            else {
                // Create zombie from registry
                BaseCustomZombie zombie = EntityRegistry.ZOMBIES.get(entityId).apply(player.getLocation());
                // Spawn zombie in world
                ((CraftWorld)player.getWorld()).getHandle().addFreshEntity(zombie);
                // Display chat message
                player.sendMessage(ChatColor.GREEN+"Spawned "+ChatColor.WHITE+entityId+"!");
            }
        }
        return true;
    }
}
