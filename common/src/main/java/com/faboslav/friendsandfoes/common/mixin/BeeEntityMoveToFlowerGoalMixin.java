package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.entity.animal.Bee;
import org.spongepowered.asm.mixin.Mixin;

//? if <=1.21.5 {
/*import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

*///?}

@Mixin(Bee.BeeGoToKnownFlowerGoal.class)
public class BeeEntityMoveToFlowerGoalMixin
{
	//? if <=1.21.5 {
	/*@Final
	@Shadow(aliases = {"field_226508_a_", "field_20372", "f_28009_"})
	private Bee this$0;

	@ModifyReceiver(
		method = "<init>(Lnet/minecraft/world/entity/animal/Bee;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"
		)
	)
	private RandomSource friendsandfoes_fixGoalRandomSourceUsage1(RandomSource randomSource, int range) {
		return this$0.getRandom();
	}
	*///?}
}
