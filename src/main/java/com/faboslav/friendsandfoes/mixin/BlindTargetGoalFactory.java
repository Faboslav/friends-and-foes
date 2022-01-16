package com.faboslav.friendsandfoes.mixin;

import net.minecraft.entity.mob.IllusionerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(IllusionerEntity.BlindTargetGoal.class)
public interface BlindTargetGoalFactory
{
	@Invoker("<init>")
	static IllusionerEntity.BlindTargetGoal newBlindTargetGoal(IllusionerEntity entity) {
		throw new AssertionError();
	}
}