package tomasborsje.plugin.zombiesmmo.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.zombiesmmo.registry.ItemRegistry;

public class ZombiesGiveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            String itemId = args[0].toUpperCase();
            int count = args.length == 2 ? Integer.parseInt(args[1]) : 1;
            if(!ItemRegistry.ITEMS.hasKey(itemId)) {
                player.sendMessage(ChatColor.RED+"No item registered with ID "+itemId);
            }
            else {
                ItemStack stack = ItemRegistry.ITEMS.get(itemId).createStack();
                stack.setAmount(count);
                player.getInventory().addItem(stack);
                player.sendMessage(ChatColor.GREEN+"Gave "+count+"x "+itemId);
            }
        }
        return true;
    }
}
