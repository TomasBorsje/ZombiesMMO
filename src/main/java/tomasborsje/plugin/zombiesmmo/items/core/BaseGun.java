package tomasborsje.plugin.zombiesmmo.items.core;

import net.minecraft.nbt.CompoundTag;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import tomasborsje.plugin.zombiesmmo.ZombiesMMO;
import tomasborsje.plugin.zombiesmmo.registry.AmmoType;
import tomasborsje.plugin.zombiesmmo.registry.ItemRegistry;
import tomasborsje.plugin.zombiesmmo.registry.ItemType;

import java.util.Collection;

public abstract class BaseGun extends CustomItem {
    public static int RANGE = 60;
    public static float PROJECTILE_SIZE = 0.3f;
    public static float PROJECTILE_STEP = 0.02f;
    public static float HEADSHOT_RADIUS = 0.27f;
    // Instance variables
    public float CRIT_MULTIPLIER = 1.5f;
    public float PROJECTILE_DAMAGE = 5f;
    public int CLIP_SIZE = 16;
    public int FIRE_RATE = 5;
    public AmmoType AMMO_TYPE = AmmoType.PISTOL_AMMO;
    public Sound FIRE_SOUND = Sound.ENTITY_FIREWORK_ROCKET_BLAST;
    public float FIRE_SOUND_PITCH = 0.7f;
    @Override
    public Rarity getRarity() {
        return Rarity.RARE;
    }

    @Override
    public ItemType getType() {
        return ItemType.GUN;
    }

    @Override
    public String getCustomId() {
        return "BASE_GUN";
    }

    @Override
    public String getLoreDescription() {
        return "";
    }

    @Override
    public String getDisplayName() {
        return "Pistol";
    }

    @Override
    public Material getBaseMaterial() {
        return Material.IRON_HOE;
    }

    @Override
    public ItemStack createStack() {
        ItemStack stack = super.createStack();
        var meta = stack.getItemMeta();
        var lore = meta.getLore();
        if(!getLoreDescription().equals("")) {
            lore.add(0, "");
        }
        lore.add(0, ChatColor.YELLOW + "☄ Ammo Type: " + ChatColor.WHITE + ItemRegistry.ITEMS.get(AMMO_TYPE.toString()).getDisplayName());
        lore.add(0, ChatColor.BLUE + "⚡ Fire Rate: " + ChatColor.WHITE + String.format("%.0f", 20f/FIRE_RATE*60) +"/min");
        lore.add(0, ChatColor.RED + "\uD83D\uDDE1 Damage: " + ChatColor.WHITE + String.format("%.0f", PROJECTILE_DAMAGE));
        meta.setLore(lore);
        stack.setItemMeta(meta);

        var nmsStack = CraftItemStack.asNMSCopy(stack);
        var nbt = nmsStack.getTag();
        nbt.putInt("AMMO_COUNT", CLIP_SIZE);
        nmsStack.setTag(nbt);

        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    @Override
    public void onRightClick(ItemStack itemStack, PlayerInteractEvent event) {
        net.minecraft.world.item.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        CompoundTag nbt = nmsStack.getTag();

        // Check ammo count
        int ammoCount = nbt.getInt("AMMO_COUNT");
        if(ammoCount<1) {
            event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_DISPENSER_FAIL, 1f, 1.3f);
            return;
        }

        // Reduce ammo count
        nbt.putInt("AMMO_COUNT", ammoCount-1);
        nmsStack.setTag(nbt);
        event.getPlayer().setItemInHand(CraftItemStack.asBukkitCopy(nmsStack));

        // Fire gun!
        Location bulletLoc = event.getPlayer().getEyeLocation();
        Vector lookDir = bulletLoc.getDirection();
        World world = bulletLoc.getWorld();
        // Play fire sound
        world.playSound(event.getPlayer().getLocation(), FIRE_SOUND, 1f, FIRE_SOUND_PITCH);

        // Set cooldown
        ZombiesMMO.serverTick.SetCooldown(event.getPlayer().getName(), FIRE_RATE);

        // Calculate bullet path
        for(int step = 0; step < RANGE/PROJECTILE_STEP; step++) {
            // Move bullet along path
            bulletLoc = bulletLoc.add(lookDir.normalize().multiply(PROJECTILE_STEP));

            // every 2nd step, spawn a particle
            if(step % (3 * (0.1f/PROJECTILE_STEP)) == 0) {
                world.spawnParticle(Particle.FIREWORKS_SPARK, bulletLoc, 1, 0, 0, 0, 0);
            }

            Block blockAtBullet = world.getBlockAt(bulletLoc);
            if(!blockAtBullet.isPassable()) {
                return;
            }

            // Get hit entities
            Collection<Entity> hitEntities = world.getNearbyEntities(bulletLoc, PROJECTILE_SIZE, PROJECTILE_SIZE, PROJECTILE_SIZE);

            // If we hit 0 living entities, continue
            if(hitEntities.stream().noneMatch((entity) -> !(entity instanceof Player)
            && !(entity instanceof ArmorStand) && entity instanceof LivingEntity && !entity.isDead())) { continue; }

            // We've reached an entity
            LivingEntity hitEntity = (LivingEntity) hitEntities.iterator().next();
            // Check if we hit at head height
            if(Math.abs((hitEntity.getLocation().getY()+hitEntity.getEyeHeight())-bulletLoc.getY()) < HEADSHOT_RADIUS) {
                // We've hit a headshot, 2x damage crit
                hitEntity.damage(PROJECTILE_DAMAGE * CRIT_MULTIPLIER, event.getPlayer());
                event.getPlayer().spawnParticle(Particle.CRIT, bulletLoc, 8, 0.3f, 0.3f, 0.3f, 0);
                event.getPlayer().playSound(event.getPlayer().getEyeLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.4f);
            }
            else {
                // Otherwise normal damage
                hitEntity.damage(PROJECTILE_DAMAGE, event.getPlayer());
            }

            // No i-frames
            hitEntity.setNoDamageTicks(0);
            // No knockback
            hitEntity.setVelocity(new Vector(0,0,0));
            // Slow zombie for 15 ticks
            hitEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15, 1, false, false));
            // End bullet
            return;
        }
    }

    @Override
    public void onLeftClick(ItemStack itemStack, PlayerInteractEvent event) {
        var nmsStack = CraftItemStack.asNMSCopy(itemStack);
        // Reload, setting ammo to max
        var nbt = nmsStack.getTag();
        if(nbt == null) {return;}
        int neededAmmo = CLIP_SIZE - nbt.getInt("AMMO_COUNT");
        if(neededAmmo == 0) {
            return;
        }

        boolean reloaded = false;

        PlayerInventory inventory = event.getPlayer().getInventory();
        // Consume as much ammo as we can
        for(int i = 0; i < inventory.getSize(); i++) {
            // Get inventory stack
            net.minecraft.world.item.ItemStack invNmsStack = CraftItemStack.asNMSCopy(inventory.getItem(i));
            // If tag, see if we can use as ammo
            if(invNmsStack.hasTag() && invNmsStack.getTag().contains("ITEM_ID")) {
                CompoundTag invNBT = invNmsStack.getTag();
                String itemId = invNBT.getString("ITEM_ID");

                // If this is the correct ammo type, consume it
                if(itemId.equals(AMMO_TYPE.name())) {
                    int ammoInStack = invNmsStack.getCount();
                    // Enough in the stack to not have to replace it
                    if(ammoInStack > neededAmmo) {
                        inventory.getItem(i).setAmount(invNmsStack.getCount()-neededAmmo);
                        nbt.putInt("AMMO_COUNT", CLIP_SIZE);
                        reloaded = true;
                        break;
                    }
                    else {
                        reloaded = true;
                        inventory.clear(i);
                        neededAmmo -= ammoInStack;
                        nbt.putInt("AMMO_COUNT", CLIP_SIZE-neededAmmo);
                    }
                }
            }
        }

        if(reloaded) {
            // Play reload sound
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_LEVER_CLICK, 1f, 0.7f);
            Bukkit.getScheduler().runTaskLater(ZombiesMMO.Plugin, () -> event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.BLOCK_LEVER_CLICK, 1f, 1f), 4);
        }
        else {
            // Play fail sound
            event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.BLOCK_DISPENSER_FAIL, 1f, 1.3f);
        }

        nmsStack.setTag(nbt);
        event.getPlayer().setItemInHand(CraftItemStack.asBukkitCopy(nmsStack));
    }

    @Override
    public boolean canRightClickUse(ItemStack itemStack, PlayerInteractEvent event) {
        return !ZombiesMMO.serverTick.IsOnCooldown(event.getPlayer().getName());
    }

    @Override
    public boolean canLeftClickUse(ItemStack itemStack, PlayerInteractEvent event) {
        return true;
    }
}
