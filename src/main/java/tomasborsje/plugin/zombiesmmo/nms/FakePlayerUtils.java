package tomasborsje.plugin.zombiesmmo.nms;

import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.level.GameType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import tomasborsje.plugin.zombiesmmo.entities.FakePlayer;
import tomasborsje.plugin.zombiesmmo.events.NPCTickHandler;

public class FakePlayerUtils {
    public static void SpawnFakePlayer(FakePlayer npc, World world) {

        ServerLevel nmsWorld = ((CraftWorld) world).getHandle();

        // Make dummy connection
        npc.connection = new ServerGamePacketListenerImpl(npc.getServer(), new Connection(PacketFlow.CLIENTBOUND), npc);
        npc.setGameMode(GameType.CREATIVE);

        nmsWorld.addFreshEntity(npc); // Spawn fake player
        npc.spawn(); // Show fake player to others
        NPCTickHandler.AddNPC(npc);
    }

}
