package tomasborsje.plugin.zombiesmmo;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import tomasborsje.plugin.zombiesmmo.commands.ZombiesGiveCommand;
import tomasborsje.plugin.zombiesmmo.commands.ZombiesSpawnCommand;
import tomasborsje.plugin.zombiesmmo.commands.ZombiesSpawnNPCCommand;
import tomasborsje.plugin.zombiesmmo.entities.FakePlayer;
import tomasborsje.plugin.zombiesmmo.events.*;
import tomasborsje.plugin.zombiesmmo.inventories.InventoryManager;
import tomasborsje.plugin.zombiesmmo.nms.FakePlayerUtils;
import tomasborsje.plugin.zombiesmmo.registry.NPCRegistry;

import java.io.IOException;
import java.util.Random;

/**
 * Main class of the Zombie MMO Spigot plugin.
 */
public class ZombiesMMO extends JavaPlugin {
    public static Random RANDOM = new Random();
    public static Plugin Plugin = null;
    public static ServerTickListener serverTick;
    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Enabled ZombieMMO plugin.");
        Plugin = this;

        // Command registration
        registerCommands();

        // Register listeners
        registerListeners();

        // Setup tick loop task
        serverTick = new ServerTickListener(this);
        serverTick.runTaskTimer(this,0,1);

        // Load map inventories
        InventoryManager.LoadInventories();

    }

    void registerCommands() {
        this.getCommand("zombiesgive").setExecutor(new ZombiesGiveCommand());
        this.getCommand("zombiesspawn").setExecutor(new ZombiesSpawnCommand());
        this.getCommand("zombiesspawnnpc").setExecutor(new ZombiesSpawnNPCCommand());
    }

    void registerListeners() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new CustomItemUseListener(), this);
        pluginManager.registerEvents(new EntityDamageListener(), this);
        pluginManager.registerEvents(new ArmorStandManipulateListener(), this);
        pluginManager.registerEvents(new EntityCombustListener(), this);
        pluginManager.registerEvents(new EntityDeathListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new WorldLoadListener(), this);
        pluginManager.registerEvents(new PlayerInteractEntityListener(), this);
        pluginManager.registerEvents(new InventoryOpenListener(), this);
    }
    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Disabled ZombieMMO plugin.");
    }
}