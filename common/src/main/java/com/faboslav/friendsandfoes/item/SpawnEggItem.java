package com.faboslav.friendsandfoes.item;

import dev.architectury.registry.registries.RegistrySupplier;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SpawnEggItem extends net.minecraft.item.SpawnEggItem
{
	private RegistrySupplier<EntityType<? extends MobEntity>> type;

	public SpawnEggItem(RegistrySupplier type, int primaryColor, int secondaryColor, Settings settings) {
		super(null, primaryColor, secondaryColor, settings);

		this.type = type;
	}

	@Override
	public EntityType<?> getEntityType(@Nullable NbtCompound compoundTag) {
		if (compoundTag != null && compoundTag.contains("EntityTag", 10)) {
			NbtCompound compoundTag2 = compoundTag.getCompound("EntityTag");
			if (compoundTag2.contains("id", 8)) {
				return EntityType.get(compoundTag2.getString("id")).orElse(this.type.get());
			}
		}

		return this.type.get();
	}
}
