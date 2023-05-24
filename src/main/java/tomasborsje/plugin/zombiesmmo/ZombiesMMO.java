package tomasborsje.plugin.zombiesmmo;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import tomasborsje.plugin.zombiesmmo.commands.ZombiesGiveCommand;
import tomasborsje.plugin.zombiesmmo.events.CustomItemUseListener;

import java.util.Random;

/**
 * Main class of the Zombie MMO Spigot plugin.
 */
public class ZombiesMMO extends JavaPlugin {
    public static Random RANDOM = new Random();
    public static Plugin Plugin = null;
    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled ZombieMMO plugin.");
        Plugin = this;
        // Command registration
        this.getCommand("zombiesgive").setExecutor(new ZombiesGiveCommand());

        // Custom item use listener
        this.getServer().getPluginManager().registerEvents(new CustomItemUseListener(), this);
    }
    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Disabled ZombieMMO plugin.");
    }
}