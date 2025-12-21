package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(Rabbit.class)
public abstract class RabbitEntityMixin extends Animal
{
	protected RabbitEntityMixin(EntityType<? extends Animal> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;addGoal(ILnet/minecraft/world/entity/ai/goal/Goal;)V",
			ordinal = 6,
			shift = At.Shift.AFTER),
		method = "registerGoals"
	)
	private void friendsandfoes_addFleeGoal(CallbackInfo ci) {
		this.goalSelector.addGoal(4, new AvoidEntityGoal(
			(Rabbit) (Object) this,
			MaulerEntity.class,
			10.0F,
			2.2,
			2.2,
			(entity) -> {
				return !((MaulerEntity) entity).isBurrowedDown();
			}
		));
	}
}