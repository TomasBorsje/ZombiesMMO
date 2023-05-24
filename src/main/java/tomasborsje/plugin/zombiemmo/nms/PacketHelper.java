package tomasborsje.plugin.zombiemmo.nms;

import net.minecraft.network.protocol.game.PacketPlayOutExplosion;
import net.minecraft.world.phys.Vec3D;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PacketHelper {
    public static void SendExplosion(Player player, Location loc, float size) {
        ((CraftPlayer)player).getHandle().b.a(new PacketPlayOutExplosion(loc.getX(),
                loc.getY(),
                loc.getZ(),
                size,
                new ArrayList<>(),
                new Vec3D(
                        player.getVelocity().getX(),
                        player.getVelocity().getY(),
                        player.getVelocity().getZ()
                )));
    }
}
