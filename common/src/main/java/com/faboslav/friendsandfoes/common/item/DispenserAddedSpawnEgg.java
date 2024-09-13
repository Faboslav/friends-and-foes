package com.faboslav.friendsandfoes.common.item;

import com.faboslav.friendsandfoes.common.events.lifecycle.SetupEvent;
import com.faboslav.friendsandfoes.common.mixin.SpawnEggItemAccessor;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
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
	private static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = Registries.ENTITY_TYPE.getCodec().fieldOf("id");
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
		DispenserBlock.registerBehavior(
			this,
			new ItemDispenserBehavior()
			{
				public ItemStack execute(@NotNull BlockPointer source, @NotNull ItemStack stack) {
					Direction direction = source.state().get(DispenserBlock.FACING);
					EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getEntityType(stack);
					entitytype.spawnFromItemStack(source.world(), stack, null, source.pos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
					stack.decrement(1);
					return stack;
				}
			});
	}

	@Override
	public EntityType<?> getEntityType(ItemStack stack) {
		var customData = stack.getOrDefault(DataComponentTypes.ENTITY_DATA, NbtComponent.DEFAULT);
		return !customData.isEmpty() ? customData.get(ENTITY_TYPE_FIELD_CODEC).result().orElse(this.entityType.get()):this.entityType.get();
	}

	@Override
	public FeatureSet getRequiredFeatures() {
		return getEntityType(ItemStack.EMPTY).getRequiredFeatures();
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