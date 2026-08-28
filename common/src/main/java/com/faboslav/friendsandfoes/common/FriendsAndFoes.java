package com.faboslav.friendsandfoes.common;

import com.faboslav.friendsandfoes.common.api.MoobloomVariantManager;
import com.faboslav.friendsandfoes.common.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.common.entity.event.IllusionerOnEntitySpawn;
import com.faboslav.friendsandfoes.common.events.AddItemGroupEntriesEvent;
import com.faboslav.friendsandfoes.common.events.entity.EntitySpawnEvent;
import com.faboslav.friendsandfoes.common.events.item.RegisterBrewingRecipesEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.*;
import com.faboslav.friendsandfoes.common.init.*;
import com.faboslav.friendsandfoes.common.modcompat.ModChecker;
import com.faboslav.friendsandfoes.common.network.MessageHandler;
import com.faboslav.friendsandfoes.common.network.packet.MoobloomVariantsSyncPacket;
import com.faboslav.friendsandfoes.common.platform.PlatformHooks;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//? if <= 1.21.11 {
/*import com.faboslav.friendsandfoes.common.events.entity.RegisterVillagerTradesEvent;
*///?}

public final class FriendsAndFoes
{
	private static final Logger LOGGER = LoggerFactory.getLogger(FriendsAndFoes.MOD_ID);
	private static final FriendsAndFoesConfig CONFIG = new FriendsAndFoesConfig();
	public static final String MOD_ID = "friendsandfoes";

	public static Identifier makeID(String path) {
		//? if >=1.21 {
		return Identifier.tryBuild(
			MOD_ID,
			path
		);
		//?} else {
		/*return new Identifier(
			MOD_ID,
			path
		);
		*///?}
	}

	public static Identifier makeNamespacedId(String id) {
		//? if >=1.21 {
		return Identifier.parse(
			id
		);
		//?} else {
		/*return new Identifier(
			id
		);
		*///?}
	}

	public static String makeStringID(String name) {
		return MOD_ID + ":" + name;
	}

	public static FriendsAndFoesConfig getConfig() {
		return CONFIG;
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
		//? if <= 1.21.11 {
		/*SetupEvent.EVENT.addListener(FriendsAndFoesItems::registerSpawnEggs);
		*///?}
		DatapackSyncEvent.EVENT.addListener(MoobloomVariantsSyncPacket::sendToClient);
		//? if <1.21.1 {
		/*RegisterBlockSetTypeEvent.EVENT.addListener(FriendsAndFoesBlockSetTypes::registerBlockSetTypes);
		*///?}
		RegisterFlammabilityEvent.EVENT.addListener(FriendsAndFoesBlocks::registerFlammability);
		RegisterEntityAttributesEvent.EVENT.addListener(FriendsAndFoesEntityTypes::registerEntityAttributes);
		RegisterEntitySpawnRestrictionsEvent.EVENT.addListener(FriendsAndFoesEntityTypes::registerEntitySpawnRestrictions);
		AddSpawnBiomeModificationsEvent.EVENT.addListener(FriendsAndFoesEntityTypes::addSpawnBiomeModifications);
		RegisterBrewingRecipesEvent.EVENT.addListener(FriendsAndFoesRecipes::registerBrewingRecipes);
		//? if <= 1.21.11 {
		/*RegisterVillagerTradesEvent.EVENT.addListener(FriendsAndFoesVillagerProfessions::registerVillagerTrades);
		*///?}
		AddItemGroupEntriesEvent.EVENT.addListener(FriendsAndFoesItemGroups::addItemGroupEntries);
		EntitySpawnEvent.EVENT.addListener(IllusionerOnEntitySpawn::handleEntitySpawn);

		FriendsAndFoesActivities.ACTIVITIES.init();
		//? if >=1.21.1 && <1.21.3 {
		/*FriendsAndFoesArmorMaterials.ARMOR_MATERIALS.init();
		*///?}
		FriendsAndFoesBlocks.BLOCKS.init();
		//? if >= 1.21.1 {
		FriendsAndFoesCriterias.CRITERIAS.init();
		//?}
		FriendsAndFoesEntityDataSerializers.init();
		FriendsAndFoesEntityTypes.ENTITY_TYPES.init();
		FriendsAndFoesItems.ITEMS.init();
		FriendsAndFoesItemGroups.ITEM_GROUPS.init();
		//? if >=1.21.1 {
		FriendsAndFoesMapDecorationTypes.MAP_DECORATION_TYPES.init();
		//?}
		FriendsAndFoesMemoryModuleTypes.MEMORY_MODULE_TYPES.init();
		FriendsAndFoesParticleTypes.PARTICLE_TYPES.init();
		FriendsAndFoesPointOfInterestTypes.POINT_OF_INTEREST_TYPES.init();
		FriendsAndFoesPotions.POTIONS.init();
		FriendsAndFoesSensorTypes.SENSOR_TYPES.init();
		FriendsAndFoesSoundEvents.SOUND_EVENTS.init();
		FriendsAndFoesStatusEffects.STATUS_EFFECTS.init();
		FriendsAndFoesStructureProcessorTypes.STRUCTURE_PROCESSOR.init();
		FriendsAndFoesStructureProcessorTypes.init();
		FriendsAndFoesStructureTypes.STRUCTURE_TYPES.init();
		//? if >= 26.1 {
		FriendsAndFoesTradeSets.init();
		//?}
		FriendsAndFoesVillagerProfessions.VILLAGER_PROFESSIONS.init();
	}

	public static void lateInit() {
		FriendsAndFoesBlockEntityTypes.lateInit();
		FriendsAndFoesItems.registerCompostableItems();
		PlatformHooks.BIOME_MODIFICATIONS.addButtercupFeature();
	}

	private static void registerServerDataListeners(final RegisterReloadListenerEvent event) {
		event.register(FriendsAndFoes.makeID("moobloom_variants"), MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER);
	}

	private static void setup(final SetupEvent event) {
		MessageHandler.init();
	}
}
