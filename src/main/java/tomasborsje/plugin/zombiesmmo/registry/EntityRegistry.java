package tomasborsje.plugin.zombiesmmo.registry;

import org.bukkit.Location;
import tomasborsje.plugin.zombiesmmo.entities.BaseCustomZombie;

import java.util.function.Function;

public class EntityRegistry {
    public static FunctionRegistry<BaseCustomZombie> ZOMBIES = new FunctionRegistry<>();

    public static Function<Location, BaseCustomZombie> CUSTOM_ZOMBIE = ZOMBIES.register("BASE_ZOMBIE", BaseCustomZombie::new);
}
