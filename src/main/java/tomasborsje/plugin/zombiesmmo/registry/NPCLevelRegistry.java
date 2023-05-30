package tomasborsje.plugin.zombiesmmo.registry;

import net.minecraft.server.level.ServerLevel;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * Registry class that allows for registration and retrieval of type T by string id.
 * @param <T> The class to store in this registry
 */
public class NPCLevelRegistry<T extends IHasId> {
    private final HashMap<String, Function<ServerLevel, T>> registryDictionary = new HashMap<>();

    public NPCLevelRegistry() {

    }

    /**
     * Register an item into this registry.
     * @param supplier The item to register
     * @return The registered item
     */
    public Function<ServerLevel, T> register(String id, Function<ServerLevel, T> supplier) {
        if(registryDictionary.containsKey(id)) {
            throw new IllegalArgumentException("Registry already contains an item for id "+id);
        }
        registryDictionary.put(id, supplier);
        return supplier;
    }

    /**
     * Get an item from the registry by id.
     * @param id The id to get the item for.
     * @return The item with the corresponding id
     */
    public Function<ServerLevel, T> get(String id) {
        if(!registryDictionary.containsKey(id)) {
            throw new IllegalArgumentException("No item registered for id "+id);
        }
        return registryDictionary.get(id);
    }

    public Set<Map.Entry<String, Function<ServerLevel, T>>> getEntries() {
        return registryDictionary.entrySet();
    }

    /**
     * Returns whether a key is registered in the registry.
     * @param id The key to check for
     * @return Whether the key is registered
     */
    public boolean hasKey(String id) {
        return registryDictionary.containsKey(id);
    }

}
