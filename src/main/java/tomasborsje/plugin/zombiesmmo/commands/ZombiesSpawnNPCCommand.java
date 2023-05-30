package tomasborsje.plugin.zombiesmmo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.entity.Player;
import tomasborsje.plugin.zombiesmmo.entities.BaseCustomZombie;
import tomasborsje.plugin.zombiesmmo.nms.FakePlayerUtils;
import tomasborsje.plugin.zombiesmmo.registry.EntityRegistry;

public class ZombiesSpawnNPCCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player player) {
            String entityId = args[0].toUpperCase();
            int count = args.length == 2 ? Integer.parseInt(args[1]) : 1;

            //FakePlayerUtils.SpawnFakePlayer(player.getLocation());
        }
        return true;
    }
}
