package com.faboslav.friendsandfoes.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.DispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

public final class DispensableSpawnEggItem extends SpawnEggItem
{
	private final Supplier<? extends EntityType<? extends MobEntity>> entityType;

	private static DispenserBehavior createDispenseItemBehavior() {
		return new ItemDispenserBehavior()
		{
			@Override
			public ItemStack dispenseSilently(BlockPointer source, ItemStack stack) {
				Direction direction = source.getBlockState().get(DispenserBlock.FACING);
				EntityType<?> entityType = ((DispensableSpawnEggItem) stack.getItem()).getEntityType(stack.getNbt());

				try {
					entityType.spawnFromItemStack(source.getWorld(), stack, null, source.getPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
				} catch (Exception var6) {
					return ItemStack.EMPTY;
				}

				stack.decrement(1);
				source.getWorld().emitGameEvent(null, GameEvent.ENTITY_PLACE, source.getPos());
				return stack;
			}
		};
	}

	public DispensableSpawnEggItem(
		Supplier<? extends EntityType<? extends MobEntity>> entityType,
		int backgroundColor,
		int highlightColor,
		Settings properties
	) {
		this(entityType, backgroundColor, highlightColor, properties, createDispenseItemBehavior());
	}

	public DispensableSpawnEggItem(
		Supplier<? extends EntityType<? extends MobEntity>> entityType,
		int backgroundColor,
		int highlightColor,
		Settings properties,
		@Nullable DispenserBehavior dispenseItemBehavior
	) {
		super(null, backgroundColor, highlightColor, properties);
		this.entityType = Objects.requireNonNull(entityType, "entityType");
		/*
		DispensableSpawnEggItem.SPAWN_EGGS.remove(null);
		entityType.listen(type -> {
			DispensableSpawnEggItem.SPAWN_EGGS.put(type, this);
			this.type = type;

			if (dispenseItemBehavior != null) {
				DispenserBlock.registerBehavior(this, dispenseItemBehavior);
			}
		});*/
	}

	@Override
	public EntityType<?> getEntityType(@Nullable NbtCompound compoundTag) {
		EntityType<?> type = super.getEntityType(compoundTag);
		return type == null ? entityType.get():type;
	}
}
