package com.faboslav.friendsandfoes.common;

import com.faboslav.friendsandfoes.common.api.MoobloomVariantManager;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationLoader;
import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.common.entity.event.IllusionerOnEntitySpawn;
import com.faboslav.friendsandfoes.common.events.AddItemGroupEntriesEvent;
import com.faboslav.friendsandfoes.common.events.entity.EntitySpawnEvent;
import com.faboslav.friendsandfoes.common.events.entity.RegisterVillagerTradesEvent;
import com.faboslav.friendsandfoes.common.events.item.RegisterBrewingRecipesEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.*;
import com.faboslav.friendsandfoes.common.init.*;
import com.faboslav.friendsandfoes.common.modcompat.ModChecker;
import com.faboslav.friendsandfoes.common.network.MessageHandler;
import com.faboslav.friendsandfoes.common.network.packet.EntityAnimationsSyncPacket;
import com.faboslav.friendsandfoes.common.network.packet.MoobloomVariantsSyncPacket;
import com.faboslav.friendsandfoes.common.platform.PlatformHooks;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class FriendsAndFoes
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FriendsAndFoes.MOD_ID);
	private static final FriendsAndFoesConfig CONFIG = new FriendsAndFoesConfig();
	public static final String MOD_ID = "friendsandfoes";

	public static ResourceLocation makeID(String path) {
		return ResourceLocation.fromNamespaceAndPath(
			MOD_ID,
			path
		);
	}

	public static String makeStringID(String name) {
		return MOD_ID + ":" + name;
	}

	public static ResourceLocation makeNamespacedId(String id) {
		return ResourceLocation.tryParse(
			id
		);
	}

	public static FriendsAndFoesConfig getConfig() {
		return CONFIG.HANDLER.instance();
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public static void init() {
		FriendsAndFoesTags.init();
		FriendsAndFoes.getConfig().load();
		ModChecker.setupModCompat();

		RegisterReloadListenerEvent.EVENT.addListener(FriendsAndFoes::registerServerDataListeners);
		SetupEvent.EVENT.addListener(FriendsAndFoes::setup);
		DatapackSyncEvent.EVENT.addListener(MoobloomVariantsSyncPacket::sendToClient);
		DatapackSyncEvent.EVENT.addListener(EntityAnimationsSyncPacket::sendToClient);
		RegisterFlammabilityEvent.EVENT.addListener(FriendsAndFoesBlocks::registerFlammablity);
		RegisterEntityAttributesEvent.EVENT.addListener(FriendsAndFoesEntityTypes::registerEntityAttributes);
		RegisterEntitySpawnRestrictionsEvent.EVENT.addListener(FriendsAndFoesEntityTypes::registerEntitySpawnRestrictions);
		AddSpawnBiomeModificationsEvent.EVENT.addListener(FriendsAndFoesEntityTypes::addSpawnBiomeModifications);
		RegisterBrewingRecipesEvent.EVENT.addListener(FriendsAndFoesRecipes::registerBrewingRecipes);
		RegisterVillagerTradesEvent.EVENT.addListener(FriendsAndFoesVillagerProfessions::registerVillagerTrades);
		AddItemGroupEntriesEvent.EVENT.addListener(FriendsAndFoesItemGroups::addItemGroupEntries);
		EntitySpawnEvent.EVENT.addListener(IllusionerOnEntitySpawn::handleEntitySpawn);

		FriendsAndFoesActivities.ACTIVITIES.init();
		//? if <1.21.3 {
		/*FriendsAndFoesArmorMaterials.ARMOR_MATERIALS.init();
		*///?}
		FriendsAndFoesBlocks.BLOCKS.init();
		FriendsAndFoesCriterias.CRITERIAS.init();
		FriendsAndFoesEntityTypes.ENTITY_TYPES.init();
		FriendsAndFoesItemGroups.ITEM_GROUPS.init();
		FriendsAndFoesItems.ITEMS.init();
		FriendsAndFoesMemoryModuleTypes.MEMORY_MODULE_TYPES.init();
		FriendsAndFoesParticleTypes.PARTICLE_TYPES.init();
		FriendsAndFoesPointOfInterestTypes.POINT_OF_INTEREST_TYPES.init();
		FriendsAndFoesPotions.POTIONS.init();
		FriendsAndFoesSensorTypes.SENSOR_TYPES.init();
		FriendsAndFoesSoundEvents.SOUND_EVENTS.init();
		FriendsAndFoesStatusEffects.STATUS_EFFECTS.init();
		FriendsAndFoesStructureProcessorTypes.STRUCTURE_PROCESSOR.init();
		FriendsAndFoesStructureProcessorTypes.init();
		FriendsAndFoesStructureTypes.STRUCTURES.init();
		FriendsAndFoesVillagerProfessions.VILLAGER_PROFESSIONS.init();
	}

	public static void lateInit() {
		FriendsAndFoesBlockEntityTypes.lateInit();
		FriendsAndFoesItems.registerCompostableItems();
		PlatformHooks.BIOME_MODIFICATIONS.addButtercupFeature();
	}

	private static void registerServerDataListeners(final RegisterReloadListenerEvent event) {
		event.register(FriendsAndFoes.makeID("moobloom_variants"), MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER);
		event.register(FriendsAndFoes.makeID("entity_animations"), AnimationLoader.INSTANCE);
	}

	private static void setup(final SetupEvent event) {
		MessageHandler.init();
	}
}
