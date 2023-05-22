package tomasborsje.plugin.zombiemmo.registry;

import java.util.Dictionary;
import java.util.HashMap;

/**
 * Registry class that allows for registration and retrieval of type T by string id.
 * @param <T> The class to store in this registry
 */
public class Registry<T> {
    private final HashMap<String, T> registryDictionary = new HashMap<String, T>();

    public Registry() {

    }

    /**
     * Register an item into this registry.
     * @param id The id to register the item with
     * @param item The item to register
     * @return The registered item
     */
    public T register(String id, T item) {
        if(registryDictionary.containsKey(id)) {
            throw new IllegalArgumentException("Registry already contains an item for id "+id);
        }
        return registryDictionary.put(id, item);
    }

    /**
     * Get an item from the registry by id.
     * @param id The id to get the item for.
     * @return The item with the corresponding id
     */
    public T get(String id) {
        if(!registryDictionary.containsKey(id)) {
            throw new IllegalArgumentException("No item registered for id "+id);
        }
        return registryDictionary.get(id);
    }

}
