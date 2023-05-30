package tomasborsje.plugin.zombiesmmo.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;
import tomasborsje.plugin.zombiesmmo.entities.FakePlayer;

import java.util.ArrayList;

public class NPCTickHandler {
    public static final ArrayList<FakePlayer> npcs = new ArrayList<>();

    public static void AddNPC(FakePlayer npc) {
        npcs.add(npc);
    }

    public void tick(World world) {
        // Tick each NPC, for each player

        for(FakePlayer npc : npcs) {
            // Set bounding box to check
            BoundingBox npcInteractBox = new BoundingBox(npc.getX()-5,npc.getY()-4,npc.getZ()-5,npc.getX()+5,npc.getY()+4,npc.getZ()+5);
            // Get nearby players that aren't us
            var nearbyPlayers = world.getNearbyEntities(npcInteractBox, (entity -> entity.getType() == EntityType.PLAYER && !npc.isEntity(entity)));
            // If we have nearby players, calculate the look vector
            if(!nearbyPlayers.isEmpty()) {
                var player = (HumanEntity)nearbyPlayers.iterator().next();

                //The location of the NPC
                Location loc = npc.getBukkitEntity().getLocation();
                //Calculate a new direction by subtracting the location of the player vector from the location vector of the npc
                loc.setDirection(player.getLocation().subtract(loc).toVector());

                //yaw and pitch used to calculate head movement
                float yaw = loc.getYaw();
                float pitch = loc.getPitch();

                // Set NPC's look vector to look at players
                npc.updateLook(yaw, pitch);
            }
            else {
                npc.updateLook(npc.getDefaultYaw(), 0);
            }
        }

    }
}
