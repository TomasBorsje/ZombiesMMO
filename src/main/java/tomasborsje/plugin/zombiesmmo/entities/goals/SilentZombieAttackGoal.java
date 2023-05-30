package tomasborsje.plugin.zombiesmmo.entities.goals;

import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.monster.Zombie;

public class SilentZombieAttackGoal extends ZombieAttackGoal {
    public SilentZombieAttackGoal(Zombie var0, double var1, boolean var3) {
        super(var0, var1, var3);
    }

    public void start() {
        super.start();
        this.mob.setSilent(false);
    }

    public void stop() {
        super.stop();
        this.mob.setSilent(true);
    }
}
