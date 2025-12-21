package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(Chicken.class)
public abstract class ChickenEntityMixin extends Animal
{
	protected ChickenEntityMixin(EntityType<? extends Animal> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V",
			ordinal = 1,
			shift = At.Shift.AFTER
		), method = "registerGoals"
	)
	private void friendsandfoes_addFleeGoal(CallbackInfo ci) {
		this.goalSelector.addGoal(2, new AvoidEntityGoal(
			(Chicken) (Object) this,
			MaulerEntity.class,
			10.0F,
			1.4,
			1.4,
			(entity) -> {
				return !((MaulerEntity) entity).isBurrowedDown();
			}
		));
	}
}