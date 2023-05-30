package tomasborsje.plugin.zombiesmmo.entities;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import tomasborsje.plugin.zombiesmmo.registry.IHasId;

import java.util.List;
import java.util.UUID;

public class FakePlayer extends ServerPlayer implements IHasId {
    public FakePlayer(ServerLevel level, String name) {
        super(level.getServer(), level, new GameProfile(UUID.randomUUID(), name));
        Vector spawnLoc = getSpawnLocation();
        this.setPos(spawnLoc.getX(), spawnLoc.getY(), spawnLoc.getZ());
        addSkinMetadata();
    }
    public byte getDefaultYaw() {
        return 0;
    }
    public ItemStack getHeldItem() {
        return null;
    }
    public Property getSkin()
    {
        return new Property("textures", "ewogICJ0aW1lc3RhbXAiIDogMTY4NTI1MTk4NDA4NywKICAicHJvZmlsZUlkIiA6ICJjMDI0Y2M0YTQwMzc0YWFjYTk2ZTE2Y2MwODM1ZDE4MCIsCiAgInByb2ZpbGVOYW1lIiA6ICJDb3JlVGVjaCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS82NGQyMjExMTk1ZWZhMTllZmJmYzY2ODg5NzExYjlhZWM2YzYyZjA2ODQyZGI1ZGRhOTRhODAyODJlNzNkNWY5IiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=", "DuZQvt8I2tTkJ/GQMuxIE5zHIS/+Y6uLNMUOpuH/VyGBd6AB8ov96tW+1pZamyqbb3XghCAyebYtyhCe5KpGXy2zYES1pjHYo/3CxiSybW3ifck2nPQRo0KrHVu4IoUKOxu+P8uE0Or3ZIu9nIwTvhuzBHsJgXB2lg+gqt3ObKaBAjJmTfkAEqshAFVkr6MEPAst4pikSD94X2SCRgqtOJfo0Ql4v5zd0cQistYssjuo7wuqZ00HEa7icyikTOQ4UmchQMwgsH4fpl1XyFCvfuz/FHbgwjqJjcKvM2229fM1pFvyFQ4ipV8hzqcEhUOET66jwieO63UnfCB2+q4mWnD3XAnikv6bILUjEqZ6LAIwipolTRRRWEv7IfbHzuD1NxHo+uikJZF+Aa5umro2CegttgaVK0Sb0mjV05eOTFAPQcYWPWQzq6wU3tCf5RRhH/2RE6ktRbMLCTCDtVRghFQm2os4RQjOzuo7jHcCnMXrKDtLhREhI5uldrPI//88Edjc/UQiQUK6/dNEih+MlWxbgBFzZI3b60AooL1GBsBD/nFNrEa10/r4weH8e1MS+cdYxCF+bV0ibruTCV3bQEdENwqstNB8HcxwuNdLCnkJSC1I5N0qfP+ZCprDl0sL6/IpEcGcjSoNl4zaeFPDehgLgaouvrdtOPGfbcYwkvY=");
    }
    public void spawn() {
        for (org.bukkit.entity.Player player : Bukkit.getOnlinePlayers()) {
            spawnFor(player); // Spawn for each player
        }
    }

    public Vector getSpawnLocation() {
        return new Vector(0,0,0);
    }

    public void addSkinMetadata() {
       this.getGameProfile().getProperties().put("textures", getSkin());
    }

    public void spawnFor(org.bukkit.entity.Player p) {
        var connection = ((CraftPlayer) p).getHandle().connection;

        // add player in player list for player
        connection.send(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, this));
        connection.send(new ClientboundAddPlayerPacket(this));
        connection.send(new ClientboundAddEntityPacket(this));
        connection.send(new ClientboundRotateHeadPacket(this, (byte) ((getDefaultYaw()%360)*256/360)));
        ItemStack heldItem = this.getHeldItem();
        if(heldItem != null) {
            connection.send(new ClientboundSetEquipmentPacket(this.getBukkitEntity().getEntityId(), List.of(Pair.of(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(heldItem)))));
        }
    }

    public void OnInteracted(org.bukkit.entity.Player player) {
        return;
    }


    public void updateLook(double yaw, double pitch) {
        for (org.bukkit.entity.Player pl : Bukkit.getOnlinePlayers()) {
            // Don't look at ourselves
            if(this.isEntity(pl)) {continue;}
            
            var connection = ((CraftPlayer) pl).getHandle().connection;
            // Horizontal head movement
            connection.send(new ClientboundRotateHeadPacket(this, (byte) ((yaw%360)*256/360)));
            // Body movement and vertical head movement
            connection.send(new ClientboundMoveEntityPacket.Rot(this.getBukkitEntity().getEntityId(), (byte) ((yaw%360.)*256/360), (byte) ((pitch%360.)*256/360), false));
            ItemStack heldItem = this.getHeldItem();
            if(heldItem != null) {
                connection.send(new ClientboundSetEquipmentPacket(this.getBukkitEntity().getEntityId(), List.of(Pair.of(EquipmentSlot.MAINHAND, CraftItemStack.asNMSCopy(heldItem)))));
            }
        }
    }

    public void remove() {
        this.kill();
    }

    public boolean isEntity(Entity et) {
        return this.getId() == et.getEntityId(); // Check if entity IDs match
    }

    @Override
    public String getCustomId() {
        return "UNKNOWN";
    }
}
