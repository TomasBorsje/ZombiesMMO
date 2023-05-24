package tomasborsje.plugin.zombiesmmo.registry;

import java.util.HashMap;

/**
 * Registry class that allows for registration and retrieval of type T by string id.
 * @param <T> The class to store in this registry
 */
public class Registry<T extends IHasId> {
    private final HashMap<String, T> registryDictionary = new HashMap<String, T>();

    public Registry() {

    }

    /**
     * Register an item into this registry.
     * @param item The item to register
     * @return The registered item
     */
    public T register(T item) {
        String id = item.getId();
        if(registryDictionary.containsKey(id)) {
            throw new IllegalArgumentException("Registry already contains an item for id "+id);
        }
        registryDictionary.put(id, item);
        return item;
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

    /**
     * Returns whether a key is registered in the registry.
     * @param id The key to check for
     * @return Whether the key is registered
     */
    public boolean hasKey(String id) {
        return registryDictionary.containsKey(id);
    }

}
