package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.effect.LongReachStatusEffect;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ScreenHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Inspired by Reach Entity Attributes library
 *
 * @author JamiesWhiteShirt
 * <a href="https://github.com/JamiesWhiteShirt/reach-entity-attributes">https://github.com/JamiesWhiteShirt/reach-entity-attributes</a>
 */
@Mixin(ScreenHandler.class)
abstract class ScreenHandlerMixin
{
	@ModifyConstant(
		method = "method_17696(Lnet/minecraft/block/Block;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Boolean;",
		require = 1,
		allow = 1,
		constant = @Constant(doubleValue = 64.0)
	)
	private static double getActualReachDistance(double currentReachDistance, Block block, PlayerEntity player) {
		return LongReachStatusEffect.getModifiedSquaredReachDistance(player, currentReachDistance);
	}
}
