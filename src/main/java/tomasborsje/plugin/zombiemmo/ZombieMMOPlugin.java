package tomasborsje.plugin.zombiemmo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import tomasborsje.plugin.zombiemmo.commands.TestCommand;
import tomasborsje.plugin.zombiemmo.events.CustomItemUseListener;

import java.util.Random;

/**
 * Main class of the Zombie MMO Spigot plugin.
 */
public class ZombieMMOPlugin extends JavaPlugin {
    public static Random RANDOM = new Random();
    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled ZombieMMO plugin.");
        // Command registration
        this.getCommand("test").setExecutor(new TestCommand());

        // Custom item use listener
        this.getServer().getPluginManager().registerEvents(new CustomItemUseListener(), this);
    }
    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Disabled ZombieMMO plugin.");
    }
}