package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.registry.BlockRegistry;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.HashSet;
import java.util.Set;

@Mixin(PointOfInterestType.class)
public abstract class PointOfInterestTypeMixin
{
	@ModifyArgs(
		method = "<clinit>",
		slice = @Slice(from = @At(
			value = "CONSTANT",
			args = "stringValue=beehive")
		),
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/poi/PointOfInterestType;register(Ljava/lang/String;Ljava/util/Set;II)Lnet/minecraft/world/poi/PointOfInterestType;",
			ordinal = 0
		)
	)
	private static void appendBeehives(Args args) {
		Set<BlockState> originalBeehiveStates = args.get(1);
		Set<BlockState> addedBeehiveStates = ImmutableList.of(
			BlockRegistry.ACACIA_BEEHIVE,
			BlockRegistry.BIRCH_BEEHIVE,
			BlockRegistry.CRIMSON_BEEHIVE,
			BlockRegistry.DARK_OAK_BEEHIVE,
			BlockRegistry.JUNGLE_BEEHIVE,
			BlockRegistry.SPRUCE_BEEHIVE,
			BlockRegistry.WARPED_BEEHIVE
		).stream().flatMap((block) -> {
			return block.getStateManager().getStates().stream();
		}).collect(ImmutableSet.toImmutableSet());

		Set<BlockState> newBeehiveStates = new HashSet<>();
		newBeehiveStates.addAll(originalBeehiveStates);
		newBeehiveStates.addAll(addedBeehiveStates);
		newBeehiveStates = newBeehiveStates.stream().collect(ImmutableSet.toImmutableSet());

		// Add new blockStates
		args.set(1, newBeehiveStates);

		// Set ticketCount
		args.set(2, 1);
	}
}