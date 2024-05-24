package com.faboslav.friendsandfoes.init.registry.forge;

import com.faboslav.friendsandfoes.common.init.registry.CustomRegistryLookup;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class ResourcefulRegistriesImpl
{
	private static final List<CustomRegistryInfo<?, ?>> CUSTOM_REGISTRIES = new ArrayList<>();

	public static <T> ResourcefulRegistry<T> create(Registry<T> registry, String id) {
		return new ForgeResourcefulRegistry<>(registry.getKey(), id);
	}

	public static <T, R extends T, K extends Registry<T>> Pair<Supplier<CustomRegistryLookup<T, R>>, ResourcefulRegistry<T>> createCustomRegistryInternal(
		String modId,
		Class<T> type,
		RegistryKey<K> key,
		boolean save,
		boolean sync,
		boolean allowModification
	) {
		CustomRegistryInfo<T, R> info = new CustomRegistryInfo<>(new LateSupplier<>(), key, save, sync, allowModification);
		CUSTOM_REGISTRIES.add(info);
		return Pair.of(info.lookup(), new ForgeResourcefulRegistry<>(key, modId));
	}

	public static void onRegisterForgeRegistries(NewRegistryEvent event) {
		CUSTOM_REGISTRIES.forEach(registry -> registry.build(event));
	}

	public static class LateSupplier<T> implements Supplier<T>
	{
		private T value;
		private boolean initialized = false;

		public void set(T value) {
			this.value = value;
			this.initialized = true;
		}

		@Override
		public T get() {
			if (!initialized) {
				throw new IllegalStateException("LateSupplier not initialized");
			}
			return value;
		}
	}

	public record CustomRegistryInfo<T, K extends T>(
		LateSupplier<CustomRegistryLookup<T, K>> lookup,
		RegistryKey<? extends Registry<T>> key,
		boolean save,
		boolean sync,
		boolean allowModification
	)
	{

		public void build(NewRegistryEvent event) {
			lookup.set(new ForgeCustomRegistry<>(event.create(getBuilder())));
		}

		public RegistryBuilder<T> getBuilder() {
			RegistryBuilder<T> builder = new RegistryBuilder<>();
			builder.setName(key.getValue());
			if (!save) builder.disableSaving();
			if (!sync) builder.disableSync();
			if (allowModification) builder.allowModification();
			return builder;
		}
	}
}
