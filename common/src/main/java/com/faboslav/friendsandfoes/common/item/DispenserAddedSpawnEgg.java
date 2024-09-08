package com.faboslav.friendsandfoes.common.item;

import com.faboslav.friendsandfoes.common.events.lifecycle.SetupEvent;
import com.faboslav.friendsandfoes.common.mixin.SpawnEggItemAccessor;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DispenserAddedSpawnEgg extends SpawnEggItem
{
	private static final List<Pair<Supplier<? extends EntityType<? extends MobEntity>>, SpawnEggItem>> SPAWN_EGGS = new ArrayList<>();
	private final Supplier<? extends EntityType<? extends MobEntity>> entityType;

	public DispenserAddedSpawnEgg(
		Supplier<? extends EntityType<? extends MobEntity>> typeIn,
		int primaryColorIn,
		int secondaryColorIn,
		Item.Settings builder
	) {
		super(null, primaryColorIn, secondaryColorIn, builder);
		this.entityType = typeIn;

		setupDispenserBehavior();
		SPAWN_EGGS.add(new Pair<>(typeIn, this));
	}

	protected void setupDispenserBehavior() {
		// Have to manually add dispenser behavior due to forge item registry event running too late.
		DispenserBlock.registerBehavior(
			this,
			new ItemDispenserBehavior()
			{
				public ItemStack execute(@NotNull BlockPointer source, @NotNull ItemStack stack) {
					Direction direction = source.getBlockState().get(DispenserBlock.FACING);
					EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getEntityType(stack.getNbt());
					entitytype.spawn(source.getWorld(), stack.getNbt(), null, source.getPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
					stack.decrement(1);
					return stack;
				}
			});
	}

	@Override
	public EntityType<?> getEntityType(@Nullable NbtCompound nbt) {
		if (nbt != null && nbt.contains("EntityTag", 10)) {
			NbtCompound nbtCompound = nbt.getCompound("EntityTag");
			if (nbtCompound.contains("id", 8)) {
				return EntityType.get(nbtCompound.getString("id")).orElse(this.entityType.get());
			}
		}

		return this.entityType.get();
	}

	public FeatureSet requiredFeatures() {
		return getEntityType(null).getRequiredFeatures();
	}

	public FeatureSet getRequiredFeatures() {
		return getEntityType(null).getRequiredFeatures();
	}

	protected EntityType<?> getDefaultType() {
		return this.entityType.get();
	}

	public static void onSetup(SetupEvent event) {
		var spawnEggMap = SpawnEggItemAccessor.variantsandventures$getSpawnEggs();
		for (var entry : DispenserAddedSpawnEgg.SPAWN_EGGS) {
			spawnEggMap.put(entry.getFirst().get(), entry.getSecond());
		}
	}
}