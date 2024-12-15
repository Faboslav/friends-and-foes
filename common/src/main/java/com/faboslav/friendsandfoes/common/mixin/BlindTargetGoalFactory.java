package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.entity.monster.Illusioner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Illusioner.IllusionerBlindnessSpellGoal.class)
public interface BlindTargetGoalFactory
{
	@Invoker("<init>")
	static Illusioner.IllusionerBlindnessSpellGoal newBlindTargetGoal(Illusioner entity) {
		throw new AssertionError();
	}
}