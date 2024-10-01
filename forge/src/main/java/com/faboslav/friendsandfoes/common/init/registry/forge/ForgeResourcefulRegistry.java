package com.faboslav.friendsandfoes.common.init.registry.forge;

import com.faboslav.friendsandfoes.common.init.registry.RegistryEntries;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Event/registry related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
@SuppressWarnings({"all", "deprecation", "removal"})
public class ForgeResourcefulRegistry<T> implements ResourcefulRegistry<T>
{
	private final DeferredRegister<T> register;
	private final RegistryEntries<T> entries = new RegistryEntries<>();

	public ForgeResourcefulRegistry(Registry<T> registry, String id) {
		this.register = DeferredRegister.create(registry.getKey(), id);
	}

	@Override
	public <I extends T> RegistryEntry<I> register(String id, Supplier<I> supplier) {
		return this.entries.add(new ForgeRegistryEntry<>(register.register(id, supplier)));
	}

	@Override
	public Collection<RegistryEntry<T>> getEntries() {
		return this.entries.getEntries();
	}

	@Override
	public void init() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		register.register(bus);
	}
}