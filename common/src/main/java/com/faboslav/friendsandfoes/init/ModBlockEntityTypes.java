package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

/**
 * @see BlockEntityType
 */
public class ModBlockEntityTypes
{
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(FriendsAndFoes.MOD_ID, Registry.BLOCK_ENTITY_TYPE_KEY);
	public static final RegistrySupplier<BlockEntityType<BeehiveBlockEntity>> FRIENDS_AND_FOES_BEEHIVES;

	static {
		FRIENDS_AND_FOES_BEEHIVES = BLOCK_ENTITY_TYPES.register("friends_and_foes_beehives", () -> BlockEntityType.Builder.create(BeehiveBlockEntity::new, ModBlocks.ACACIA_BEEHIVE.get(), ModBlocks.BIRCH_BEEHIVE.get(), ModBlocks.CRIMSON_BEEHIVE.get(), ModBlocks.DARK_OAK_BEEHIVE.get(), ModBlocks.JUNGLE_BEEHIVE.get(), ModBlocks.SPRUCE_BEEHIVE.get(), ModBlocks.WARPED_BEEHIVE.get()).build(Util.getChoiceType(TypeReferences.BLOCK_ENTITY, FriendsAndFoes.makeStringID("friends_and_foes_beehives"))));
	}

	public static void initRegister() {
		BLOCK_ENTITY_TYPES.register();
	}

	public static void init() {}
}
