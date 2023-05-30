package tomasborsje.plugin.zombiesmmo.inventories;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;
import tomasborsje.plugin.zombiesmmo.registry.InventoryRegistry;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;

public class InventoryManager {

    static HashMap<String, HashMap<Vector, Inventory>> inventories = new HashMap<>();
    static HashMap<Vector, CustomInventory> inventoryLocations = new HashMap<>();

    public static void LoadInventories() {
        Bukkit.getLogger().log(Level.INFO, "Loading map inventories...");
        try {
            Bukkit.getPluginManager().getPlugin("ZombieMMO").getDataFolder().mkdirs();;
            File inventoriesFile = new File(Bukkit.getServer().getPluginManager().getPlugin("ZombieMMO").getDataFolder(), File.separator + "map-inventories.txt");

            // Only read if the file already exists, otherwise we create it and move on
            if (!inventoriesFile.createNewFile()) {
                // Read in all lines
                Scanner myReader = new Scanner(inventoriesFile);
                // For each inventory, load its location and ID
                while (myReader.hasNextLine()) {
                    String[] line = myReader.nextLine().split(" ");
                    try {
                        String inventoryId = line[0];
                        int x = Integer.parseInt(line[1]);
                        int y = Integer.parseInt(line[2]);
                        int z = Integer.parseInt(line[3]);
                        inventoryLocations.put(new Vector(x, y, z), InventoryRegistry.INVENTORIES.get(inventoryId));
                    } catch (Exception e) {
                        Bukkit.getLogger().log(Level.WARNING, "Error loading inventory with string "+ Arrays.toString(line));
                    }
                }
            }
            Bukkit.getLogger().log(Level.INFO, "Successfully loaded map inventories!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Returns the inventory for a player at this location. Null if no inventory.
     * @param username The player's username.
     * @param coordinates The coordinates to get the inventory at.
     * @return The inventory for the player if it exists. Otherwise, null.
     */
    @Nullable
    public static Inventory getInventory(String username, Vector coordinates) {

        if(!inventoryLocations.containsKey(coordinates)) {return null;}

        // Add player name if we don't have them already
        inventories.putIfAbsent(username, new HashMap<>());

        // If the inventory doesn't exist yet, generate it.
        if(!inventories.get(username).containsKey(coordinates)) {
            inventories.get(username).put(coordinates, InventoryRegistry.CUPBOARD.generateNewInstance());
        }
        // return the inventory at this location for this player.
        return inventories.get(username).get(coordinates);
    }

}
