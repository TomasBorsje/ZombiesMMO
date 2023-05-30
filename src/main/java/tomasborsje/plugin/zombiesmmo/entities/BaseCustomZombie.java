package tomasborsje.plugin.zombiesmmo.entities;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R3.util.CraftChatMessage;
import tomasborsje.plugin.zombiesmmo.entities.goals.SilentZombieAttackGoal;
import tomasborsje.plugin.zombiesmmo.events.ServerTickListener;
import tomasborsje.plugin.zombiesmmo.nms.HologramSpawner;
import tomasborsje.plugin.zombiesmmo.registry.IHasId;
import tomasborsje.plugin.zombiesmmo.util.EntityUtils;

import java.util.function.Predicate;

public class BaseCustomZombie extends Zombie implements IHasId {

    public static final float BASE_SPEED = 0.20f;
    private static final float HEALTH_BAR_OFFSET = 1.7f;
    ArmorStand healthDisplay;

    public BaseCustomZombie(Location loc) {
        super(EntityType.ZOMBIE, ((CraftWorld) loc.getWorld()).getHandle());

        this.setPos(loc.getX(), loc.getY(), loc.getZ());
        this.setCanPickUpLoot(false);
        this.setAggressive(true);
        this.setCustomNameVisible(true);
        this.setCustomName(CraftChatMessage.fromJSONOrString(ChatColor.GREEN + "[Lv10]"+ChatColor.WHITE+" Zombie"));
        healthDisplay = HologramSpawner.SpawnHologram(loc.add(0, HEALTH_BAR_OFFSET, 0), EntityUtils.GetHealthBarMessage(this));
        this.setSilent(true);
    }
    
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SilentZombieAttackGoal(this, 1, false));
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
    }

    @Override
    protected boolean supportsBreakDoorGoal() {
        return super.supportsBreakDoorGoal();
    }

    @Override
    public void removeAllGoals(Predicate<Goal> predicate) {
        super.removeAllGoals(predicate);
    }

    @Override
    public void tick() {
        super.tick();
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(BASE_SPEED * (ServerTickListener.IsNight ? 1.5f : 1));
        if(healthDisplay != null) {
            healthDisplay.setPos(this.getX(), this.getY()+HEALTH_BAR_OFFSET, this.getZ());
            healthDisplay.setCustomName(CraftChatMessage.fromJSONOrString(EntityUtils.GetHealthBarMessage(this)));
        }
    }

    @Override
    protected void tickDeath() {
        super.tickDeath();
        if(healthDisplay != null) {
            healthDisplay.kill();
            healthDisplay = null;
        }
    }

    @Override
    public String getCustomId() {
        return "BASIC_ZOMBIE";
    }
}
