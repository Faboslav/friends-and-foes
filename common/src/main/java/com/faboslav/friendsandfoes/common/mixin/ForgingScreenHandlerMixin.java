package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.entity.effect.LongReachStatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Inspired by Reach Entity Attributes library
 *
 * @author JamiesWhiteShirt
 * <a href="https://github.com/JamiesWhiteShirt/reach-entity-attributes">https://github.com/JamiesWhiteShirt/reach-entity-attributes</a>
 */
@Mixin(ForgingScreenHandler.class)
abstract class ForgingScreenHandlerMixin extends ScreenHandler
{
	ForgingScreenHandlerMixin(final ScreenHandlerType<?> type, final int id) {
		super(type, id);
	}

	@ModifyConstant(
		method = "method_24924(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)Ljava/lang/Boolean;",
		constant = @Constant(doubleValue = 64.0)
	)
	private double getActualReachDistance(double currentReachDistance, PlayerEntity player) {
		return LongReachStatusEffect.getModifiedSquaredReachDistance(player, currentReachDistance);
	}
}
