package tomasborsje.plugin.zombiesmmo.registry;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import tomasborsje.plugin.zombiesmmo.entities.BaseCustomZombie;

import java.util.Locale;
import java.util.function.Function;
import java.util.function.Supplier;

public class EntityRegistry {
    public static SupplierRegistry<BaseCustomZombie> ZOMBIES = new SupplierRegistry<>();

    public static Function<Location, BaseCustomZombie> CUSTOM_ZOMBIE = ZOMBIES.register("BASE_ZOMBIE", BaseCustomZombie::new);
}
