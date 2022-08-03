package com.faboslav.friendsandfoes.platform.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.mixin.forge.FireBlockAccessor;
import net.minecraft.block.Block;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

public final class RegistryHelperImpl
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FriendsAndFoes.MOD_ID);
	public static final Map<EntityModelLayer, Supplier<TexturedModelData>> ENTITY_MODEL_LAYERS = new ConcurrentHashMap<>();
	public static final Map<Supplier<? extends EntityType<? extends LivingEntity>>, Supplier<DefaultAttributeContainer.Builder>> ENTITY_ATTRIBUTES = new ConcurrentHashMap<>();
	public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, FriendsAndFoes.MOD_ID);
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FriendsAndFoes.MOD_ID);
	public static final DeferredRegister<PointOfInterestType> POINT_OF_INTEREST_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, FriendsAndFoes.MOD_ID);
	public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, FriendsAndFoes.MOD_ID);
	public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, FriendsAndFoes.MOD_ID);

	public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
		return BLOCKS.register(name, block);
	}

	public static void registerEntityModelLayer(EntityModelLayer location, Supplier<TexturedModelData> definition) {
		ENTITY_MODEL_LAYERS.put(location, definition);
	}

	public static <T extends Entity> void registerEntityRenderer(
		Supplier<EntityType<T>> type,
		EntityRendererFactory<T> renderProvider
	) {
		EntityRenderers.register(type.get(), renderProvider);
	}

	public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(
		String name,
		Supplier<EntityType<T>> entityType
	) {
		return ENTITY_TYPES.register(name, entityType);
	}

	public static void registerEntityAttribute(
		Supplier<? extends EntityType<? extends LivingEntity>> type,
		Supplier<DefaultAttributeContainer.Builder> attribute
	) {
		ENTITY_ATTRIBUTES.put(type, attribute);
	}

	public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
		return ITEMS.register(name, item);
	}

	public static <T extends PointOfInterestType> Supplier<T> registerPointOfInterestType(
		String name,
		Supplier<T> pointOfInterestType
	) {
		return POINT_OF_INTEREST_TYPES.register(name, pointOfInterestType);
	}

	public static void registerRenderType(RenderLayer type, Block... blocks) {
		for (Block block : blocks) {
			RenderLayers.setRenderLayer(block, type);
		}
	}

	public static <T extends SoundEvent> Supplier<T> registerSoundEvent(String name, Supplier<T> soundEvent) {
		return SOUND_EVENTS.register(name, soundEvent);
	}

	public static <T extends VillagerProfession> Supplier<T> registerVillagerProfession(
		String name,
		Supplier<T> villagerProfession
	) {
		return VILLAGER_PROFESSIONS.register(name, villagerProfession);
	}

	public static <T extends Block> void registerFlammableBlock(Block fireBlock, Supplier<T> block, int burnChance, int spreadChance) {
		((FireBlockAccessor)fireBlock).invokeRegisterFlammableBlock(block.get(), burnChance, spreadChance);
	}
}
