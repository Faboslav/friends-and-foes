package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.MaulerEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(ChickenEntity.class)
public abstract class ChickenEntityMixin extends AnimalEntity
{
	protected ChickenEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V",
			ordinal = 1,
			shift = At.Shift.AFTER
		), method = "initGoals"
	)
	private void friendsandfoes_addFleeGoal(CallbackInfo ci) {
		this.goalSelector.add(2, new FleeEntityGoal(
			(ChickenEntity) (Object) this,
			MaulerEntity.class,
			10.0F,
			1.4,
			1.4,
			(entity) -> {
				return ((MaulerEntity) entity).isBurrowedDown() == false;
			}
		));
	}
}