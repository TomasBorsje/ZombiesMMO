package tomasborsje.plugin.zombiesmmo.entities.npcs;

import com.mojang.authlib.properties.Property;
import net.minecraft.server.level.ServerLevel;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tomasborsje.plugin.zombiesmmo.entities.FakePlayer;
import tomasborsje.plugin.zombiesmmo.registry.ItemRegistry;

public class MaxwellNPC extends FakePlayer {
    int interactCounter = 0;
    public MaxwellNPC(ServerLevel serverLevel) {
        super(serverLevel, ChatColor.GOLD+"Maxwell");
    }

    @Override
    public ItemStack getHeldItem() {
        return new ItemStack(Material.WOODEN_SWORD);
    }

    @Override
    public Vector getSpawnLocation() {
        return new Vector( 3.4f, -59, 11.3f);
    }

    @Override
    public byte getDefaultYaw() {
        return (byte) 45;
    }

    @Override
    public void OnInteracted(Player player) {
        if(interactCounter%2==0) {
            player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Maxwell: " + ChatColor.RESET + "" + ChatColor.WHITE + "Hey, I'm Maxwell. You new around here?");
        }
        if(interactCounter%2==1) {
            player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Maxwell: " + ChatColor.RESET + "" + ChatColor.WHITE + "Take this pistol. You'll probably need it.");
            player.getInventory().addItem(ItemRegistry.STANDARD_PISTOL.createStack(), ItemRegistry.PISTOL_AMMO.createStack(14));
        }

        interactCounter++;
        player.playSound(this.getBukkitEntity(), Sound.ENTITY_VILLAGER_YES, 1f, (float)(0.65f+Math.random()*0.1f));
    }

    @Override
    public Property getSkin() {
        // Default is mechanic skin already
        return super.getSkin();
    }

    @Override
    public String getCustomId() {
        return "MAXWELL";
    }
}
