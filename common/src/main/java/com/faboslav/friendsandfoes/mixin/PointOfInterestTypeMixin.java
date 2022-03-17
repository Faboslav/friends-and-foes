package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.init.ModBlocks;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.poi.PointOfInterestType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@Mixin(PointOfInterestType.class)
public abstract class PointOfInterestTypeMixin
{

	@Shadow
	public abstract String getId();

	private static final HashSet<BlockState> CUSTOM_BEEHIVES = new HashSet<>()
	{{
		ModBlocks.ACACIA_BEEHIVE.get().getStateManager().getStates();
		ModBlocks.BIRCH_BEEHIVE.get().getStateManager().getStates();
		ModBlocks.CRIMSON_BEEHIVE.get().getStateManager().getStates();
		ModBlocks.DARK_OAK_BEEHIVE.get().getStateManager().getStates();
		ModBlocks.JUNGLE_BEEHIVE.get().getStateManager().getStates();
		ModBlocks.SPRUCE_BEEHIVE.get().getStateManager().getStates();
		ModBlocks.WARPED_BEEHIVE.get().getStateManager().getStates();
	}};

	@Inject(
		method = "getAllStatesOf",
		at = @At("RETURN"),
		cancellable = true
	)
	private static void getAllStatesOf(
		Block block,
		CallbackInfoReturnable<Set<BlockState>> cir
	) {
		var beehiveBlockStates = cir.getReturnValue();

		if(!(block instanceof BeehiveBlock)) {
			cir.setReturnValue(beehiveBlockStates);
		}

		Set<BlockState> newBeehiveStates = new HashSet<>();
		newBeehiveStates.addAll(beehiveBlockStates);
		newBeehiveStates.addAll(CUSTOM_BEEHIVES);
		newBeehiveStates = newBeehiveStates.stream().collect(ImmutableSet.toImmutableSet());

		cir.setReturnValue(newBeehiveStates);
	}

	@Inject(
		method = "getTicketCount",
		at = @At("RETURN"),
		cancellable = true
	)
	public void getTicketCount(
		CallbackInfoReturnable<Integer> cir
	) {
		var id = this.getId();

		if(!Objects.equals(id, "beehive")) {
			cir.setReturnValue(cir.getReturnValue());
		}

		cir.setReturnValue(1);
	}

	/*
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
			ModBlocks.ACACIA_BEEHIVE.get(),
			ModBlocks.BIRCH_BEEHIVE.get(),
			ModBlocks.CRIMSON_BEEHIVE.get(),
			ModBlocks.DARK_OAK_BEEHIVE.get(),
			ModBlocks.JUNGLE_BEEHIVE.get(),
			ModBlocks.SPRUCE_BEEHIVE.get(),
			ModBlocks.WARPED_BEEHIVE.get()
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
	}*/
}