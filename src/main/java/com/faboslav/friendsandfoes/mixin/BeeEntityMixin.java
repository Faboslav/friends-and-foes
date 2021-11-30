package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.passive.ai.goal.BeePollinateMoobloomGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity implements Angerable
{
    BeePollinateMoobloomGoal pollinateMoobloomGoal;

    public BeeEntityMixin(
            EntityType<? extends AnimalEntity> entityType,
            World world
    ) {
        super(entityType, world);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 4, shift = At.Shift.AFTER), method = "initGoals")
    private void addFleeEntityGoal(CallbackInfo ci) {
        this.pollinateMoobloomGoal = new BeePollinateMoobloomGoal((BeeEntity) (Object) this, (BeeEntityAccessor) this);
        this.goalSelector.add(3, this.pollinateMoobloomGoal);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damage(
            DamageSource source,
            float amount,
            CallbackInfoReturnable<Boolean> info
    ) {
        if (this.isInvulnerableTo(source)) {
            info.setReturnValue(false);
        } else {
            if (!this.world.isClient) {
                this.pollinateMoobloomGoal.cancel();
            }
        }
    }
}
