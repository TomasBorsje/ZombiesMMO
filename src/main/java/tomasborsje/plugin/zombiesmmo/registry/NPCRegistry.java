package tomasborsje.plugin.zombiesmmo.registry;

import net.minecraft.server.level.ServerLevel;
import org.bukkit.Location;
import tomasborsje.plugin.zombiesmmo.entities.FakePlayer;
import tomasborsje.plugin.zombiesmmo.entities.npcs.MaxwellNPC;

import java.util.function.Function;

public class NPCRegistry {
    public static NPCLevelRegistry<FakePlayer> NPCs = new NPCLevelRegistry<>();

    public static Function<ServerLevel, FakePlayer> MAXWELL = NPCs.register("MAXWELL", MaxwellNPC::new);
}
