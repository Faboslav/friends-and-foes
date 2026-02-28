package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.entity.animal.bee.Bee;
import org.spongepowered.asm.mixin.Mixin;

//? if <= 1.21.6 {
/*import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
*///?}

@Mixin(Bee.BeeGoToKnownFlowerGoal.class)
public class BeeGoToKnownFlowerGoalMixin
{
	//? if <= 1.21.6 {
	/*@Final
	@Shadow(aliases = {"field_226508_a_", "field_20372", "f_28009_"})
	private Bee this$0;

	/^*
	 * @author TelepathicGrunt
	 * @reason Always use the entity's own randomSource instead of world's when creating/initing entities or else you risk a crash from threaded worldgen entity spawning. Fixed this bug with vanilla bees.
	 ^/
	@ModifyReceiver(
		method = "<init>(Lnet/minecraft/world/entity/animal/Bee;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/util/RandomSource;nextInt(I)I"
		)
	)
	private RandomSource friendsandfoes$fixGoalRandomSourceUsage2(RandomSource randomSource, int range) {
		return this$0.getRandom();
	}
	*///?}
}