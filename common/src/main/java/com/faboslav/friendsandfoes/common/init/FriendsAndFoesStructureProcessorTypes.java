package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.common.world.processor.CitadelBottomProcessor;
import com.faboslav.friendsandfoes.common.world.processor.IllusionerShackBrewingStandProcessor;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.registry.Registry;
import org.apache.commons.lang3.NotImplementedException;

/**
 * @see StructureProcessorType
 */
public final class FriendsAndFoesStructureProcessorTypes
{
	public static final ResourcefulRegistry<StructureProcessorType<?>> STRUCTURE_PROCESSOR = ResourcefulRegistries.create(Registry.STRUCTURE_PROCESSOR, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<StructureProcessorType<CitadelBottomProcessor>> CITADEL_BOTTOM_PROCESSOR = STRUCTURE_PROCESSOR.register("citadel_bottom_processor", () -> () -> CitadelBottomProcessor.CODEC);
	public static final RegistryEntry<StructureProcessorType<IllusionerShackBrewingStandProcessor>> ILLUSIONER_SHACK_BREWING_STAND_PROCESSOR = STRUCTURE_PROCESSOR.register("illusioner_shack_brewing_stand_processor", () -> () -> IllusionerShackBrewingStandProcessor.CODEC);

	private FriendsAndFoesStructureProcessorTypes() {
	}

	@ExpectPlatform
	public static void init() {
		throw new NotImplementedException();
	}
}
