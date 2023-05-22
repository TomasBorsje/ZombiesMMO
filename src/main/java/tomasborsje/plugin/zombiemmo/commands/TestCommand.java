package tomasborsje.plugin.zombiemmo.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import tomasborsje.plugin.zombiemmo.items.MakeshiftBandageItem;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player player) {
            ItemStack testItem = new ItemStack(Material.WOODEN_SWORD);
            player.getInventory().addItem(new MakeshiftBandageItem().Create());
        }
        return true;
    }
}
