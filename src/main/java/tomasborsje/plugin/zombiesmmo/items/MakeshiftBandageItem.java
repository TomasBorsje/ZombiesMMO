package tomasborsje.plugin.zombiesmmo.items;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import tomasborsje.plugin.zombiesmmo.ZombiesMMO;
import tomasborsje.plugin.zombiesmmo.registry.ItemType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MakeshiftBandageItem extends CustomItem {
    @Override
    public String getId() {
        return "MAKESHIFT_BANDAGE";
    }

    @Override
    public String getDisplayName() {
        return "Makeshift Bandage";
    }

    @Override
    public List<String> getDescription() {
        return new ArrayList<String>(Arrays.asList(
                ChatColor.GRAY + "Applies " + ChatColor.RED + ChatColor.BOLD + "Regeneration II" + ChatColor.RESET + ChatColor.GRAY + " for " + ChatColor.WHITE + "15 seconds.",
                "",
                ChatColor.DARK_GRAY + "A makeshift bandage torn off some old clothes."));
    }

    @Override
    public Material getBaseMaterial() {
        return Material.PAPER;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.UNCOMMON;
    }

    @Override
    public ItemType getType() {
        return ItemType.MEDICINE;
    }

    @Override
    public void onUse(ItemStack itemStack, PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        // Play sounds with delay
        player.playSound(player, Sound.BLOCK_WOOL_PLACE, 1.5f, 0.2f);
        Bukkit.getScheduler().runTaskLater(ZombiesMMO.Plugin, () -> player.playSound(player, Sound.BLOCK_WOOL_PLACE, 1.3f, 0.6f), 5);
        Bukkit.getScheduler().runTaskLater(ZombiesMMO.Plugin, () -> player.playSound(player, Sound.BLOCK_WOOL_PLACE, 1.1f, 0.8f), 10);

        // Spawn particles
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            Location particleLocation = player.getLocation().add(random.nextFloat(-1, 1), random.nextFloat(-1, 1) + 1, random.nextFloat(-1, 1));
            world.spawnParticle(Particle.VILLAGER_HAPPY, particleLocation, 1);
        }
        // Apply potion effect
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 15, 1, false, false));
        // Send chat message
        player.sendMessage(ChatColor.GREEN + "You used a makeshift bandage!");
        // Reduce stack count
        itemStack.setAmount(itemStack.getAmount() - 1);
    }

    @Override
    public boolean canUse(ItemStack itemStack, PlayerInteractEvent event) {
        return true;
    }
}
