package tomasborsje.plugin.zombiemmo.items;

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MakeshiftBandageItem implements CustomItem {
    public static final String ID = "MAKESHIFT_BANDAGE";
    private static List<String> LORE = Arrays.asList(ChatColor.GRAY + "Applies " + ChatColor.RED + ChatColor.BOLD + "Regeneration II" +
                    ChatColor.RESET + ChatColor.GRAY + " for " + ChatColor.WHITE + "15 seconds.",
            ChatColor.DARK_GRAY + "A makeshift bandage torn off some old clothes.");

    @Override
    public ItemStack Create() {
        ItemStack stack = new ItemStack(Material.PAPER);

        // Mappings not set up yet so use Bukkit item for paper
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);

        NBTTagCompound nbt = nmsStack.v();
        // Add ITEM_ID tag
        nbt.a("ITEM_ID", ID);
        // Set nbt to the nmsStack
        nmsStack.c(nbt);

        // Bring NMS stack back to spigot level
        stack = CraftItemStack.asBukkitCopy(nmsStack);

        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "Makeshift Bandage");
        meta.setLore(LORE);
        stack.setItemMeta(meta);

        return stack;
    }

    @Override
    public void OnUse(ItemStack itemStack, PlayerInteractEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        // Play sound
        player.playSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1.5f);
        // Spawn particles
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            Location particleLocation = player.getLocation().add(random.nextFloat(-1, 1), random.nextFloat(-1, 1) + 1, random.nextFloat(-1, 1));
            world.spawnParticle(Particle.VILLAGER_HAPPY, particleLocation, 1);
        }
        // Apply potion effect
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 1));
        // Send chat message
        player.sendMessage(ChatColor.GREEN + "You used a makeshift bandage!");
        // Reduce stack count
        itemStack.setAmount(itemStack.getAmount() - 1);
    }

    @Override
    public boolean CanUse(ItemStack itemStack, PlayerInteractEvent event) {
        return true;
    }
}
