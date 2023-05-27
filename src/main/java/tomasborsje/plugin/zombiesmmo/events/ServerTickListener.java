package tomasborsje.plugin.zombiesmmo.events;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_19_R3.boss.CraftBossBar;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;

public class ServerTickListener extends BukkitRunnable {
    private final JavaPlugin plugin;
    public static boolean IsNight = true;
    public static float NIGHT_THRESHOLD = 13000;
    public static float DAY_THRESHOLD = 23500;
    public static int DAY_LENGTH = 24000;
    BossBar bar;
    private int ticks = 0;

    HashMap<String, Integer> playerCooldowns = new HashMap<String, Integer>();

    public boolean IsOnCooldown(String playerName) {
        return playerCooldowns.containsKey(playerName) && playerCooldowns.get(playerName) > 0;
    }
    public void SetCooldown(String playername, int ticks) {
        playerCooldowns.put(playername, ticks);
    }
    public ServerTickListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        World world = plugin.getServer().getWorlds().get(0);
        if(world == null) {return;}

        // Check if we switched from day to night or vice versa
        boolean newIsNight = isNight(world);
        if(IsNight && !newIsNight) {
            Bukkit.broadcastMessage(ChatColor.GOLD+"☀ "+ChatColor.YELLOW+ChatColor.BOLD+"The morning sun rises over the wasteland.");
            Bukkit.broadcastMessage(ChatColor.GOLD+"☀ "+ChatColor.YELLOW+ChatColor.BOLD+"A new day has begun!");
        }
        else if(!IsNight && newIsNight) {
            Bukkit.broadcastMessage(ChatColor.DARK_GRAY+"☁ "+ChatColor.GRAY+ChatColor.BOLD+"The sun recedes behind the horizon.");
            Bukkit.broadcastMessage(ChatColor.DARK_GRAY+"☁ "+ChatColor.GRAY+ChatColor.BOLD+"Night has begun...");
        }
        IsNight = newIsNight;

        // Reduce player cooldowns
        for(var pair : playerCooldowns.entrySet()) {
            if(pair.getValue() > 0) {
                playerCooldowns.put(pair.getKey(), pair.getValue() - 1);
            }
        }

        ticks++;
        // Boss bar setup
        if(bar == null) {
            bar = plugin.getServer().createBossBar("ZombieMMO", BarColor.WHITE, BarStyle.SOLID);
            ServerBossEvent bossEvent = ((CraftBossBar)bar).getHandle();
            Component textComp = CraftChatMessage.fromJSON("{\"text\":\"ZombieMMO\",\"color\":\"dark_purple\",\"bold\":true}");
            bossEvent.setName(textComp);
        }
        updateBossBar(world);
        updateAmmoDisplay();
    }

    /**
     * Displays each player's current ammo
     * on their XP bar as their level.
     */
    private void updateAmmoDisplay() {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        for(Player player : players) {
            ItemStack heldItem = CraftItemStack.asNMSCopy(player.getItemInHand());
            if(heldItem.hasTag() && heldItem.getTag().contains("AMMO_COUNT")) {
                int ammoCount = heldItem.getTag().getInt("AMMO_COUNT");
                player.setLevel(ammoCount);
                player.setExp(ammoCount == 0 ? 0 : 1f);
            }
            else{
                player.setLevel(0);
                player.setExp(0);
            }
        }
    }

    void updateBossBar(World world) {
        long worldTime = world.getTime() % DAY_LENGTH;

        try {
            if(isNight(world)) {
                bar.setProgress(1-((worldTime-NIGHT_THRESHOLD)/(DAY_THRESHOLD-NIGHT_THRESHOLD)));
                bar.setColor(BarColor.PURPLE);
                setBarNameJson("[{\"text\":\"☁ \",\"color\":\"dark_gray\",\"bold\":false},"
                        +"{\"text\":\"ZombieMMO - Night\",\"color\":\"gray\",\"bold\":true,\"underlined\":true},"
                        +"{\"text\":\" ☁\",\"color\":\"dark_gray\",\"bold\":false}]");
            }
            else {
                // Maths to make bar work with wrapping from 23999->0
                if(worldTime > DAY_THRESHOLD) {
                    bar.setProgress(1 - ((worldTime - DAY_THRESHOLD)/(DAY_LENGTH - DAY_THRESHOLD + NIGHT_THRESHOLD)));
                }
                else {
                    bar.setProgress(1-((worldTime+DAY_LENGTH-DAY_THRESHOLD)/(DAY_LENGTH-DAY_THRESHOLD+NIGHT_THRESHOLD)));
                }
                bar.setColor(BarColor.YELLOW);
                setBarNameJson("[{\"text\":\"☀ \",\"color\":\"gold\",\"bold\":false},"
                        +"{\"text\":\"ZombieMMO - Day\",\"color\":\"yellow\",\"bold\":true,\"underlined\":true},"
                        +"{\"text\":\" ☀\",\"color\":\"gold\",\"bold\":false}]");
            }
        } catch (Exception e) {
        bar.setProgress(0); // TODO: First tick in daytime has invalid progress (1.77)
    }

        Collection<? extends Player> players = plugin.getServer().getOnlinePlayers();
        for(Player p : players) {
            if(!bar.getPlayers().contains(p)) {
                bar.addPlayer(p);
            }
        }
    }
    void setBarNameJson(String json) {
        ServerBossEvent bossEvent = ((CraftBossBar)bar).getHandle();
        Component textComp = CraftChatMessage.fromJSON(json);
        bossEvent.setName(textComp);
    }
    boolean isNight(World world) {
        if(world == null) {return true;}
        long worldTime = world.getTime() % 24000;

        return NIGHT_THRESHOLD < worldTime && worldTime < DAY_THRESHOLD;
    }
}
