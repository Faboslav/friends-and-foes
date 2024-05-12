package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.effect.LongReachStatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

/**
 * Inspired by Reach Entity Attributes library
 *
 * @author JamiesWhiteShirt
 * <a href="https://github.com/JamiesWhiteShirt/reach-entity-attributes">https://github.com/JamiesWhiteShirt/reach-entity-attributes</a>
 */
@Mixin(Item.class)
abstract class ItemMixin implements ItemConvertible
{
	@ModifyConstant(
		method = "raycast(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/world/RaycastContext$FluidHandling;)Lnet/minecraft/util/hit/BlockHitResult;",
		require = 4,
		allow = 4,
		constant = @Constant(doubleValue = 5.0)
	)
	private static double getActualReachDistance(double currentReachDistance, World world, PlayerEntity player) {
		return LongReachStatusEffect.getModifiedReachDistance(player, currentReachDistance);
	}
}
