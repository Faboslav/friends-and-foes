package com.faboslav.friendsandfoes.platform.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.function.Supplier;

public final class RegistryHelperImpl
{
	public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
		var registry = Registry.register(Registry.BLOCK, FriendsAndFoes.makeID(name), block.get());
		return () -> registry;
	}

	public static void registerEntityModelLayer(EntityModelLayer location, Supplier<TexturedModelData> definition) {
		EntityModelLayerRegistry.registerModelLayer(location, definition::get);
	}

	public static <T extends Entity> void registerEntityRenderer(
		Supplier<EntityType<T>> type,
		EntityRendererFactory<T> renderProvider
	) {
		EntityRendererRegistry.register(type.get(), renderProvider);
	}

	public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(
		String name,
		Supplier<EntityType<T>> entityType
	) {
		var registry = Registry.register(Registry.ENTITY_TYPE, FriendsAndFoes.makeID(name), entityType.get());
		return () -> registry;
	}

	public static void registerEntityAttribute(
		Supplier<? extends EntityType<? extends LivingEntity>> type,
		Supplier<DefaultAttributeContainer.Builder> attribute
	) {
		FabricDefaultAttributeRegistry.register(type.get(), attribute.get());
	}

	public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
		var registry = Registry.register(Registry.ITEM, FriendsAndFoes.makeID(name), item.get());
		return () -> registry;
	}

	public static <T extends PointOfInterestType> Supplier<T> registerPointOfInterestType(
		String name,
		Supplier<T> pointOfInterestType
	) {
		var registry = Registry.register(Registry.POINT_OF_INTEREST_TYPE, FriendsAndFoes.makeID(name), pointOfInterestType.get());
		return () -> registry;
	}

	public static void registerRenderType(RenderLayer type, Block... blocks) {
		BlockRenderLayerMap.INSTANCE.putBlocks(type, blocks);
	}

	public static <T extends SoundEvent> Supplier<T> registerSoundEvent(String name, Supplier<T> soundEvent) {
		var registry = Registry.register(Registry.SOUND_EVENT, FriendsAndFoes.makeID(name), soundEvent.get());
		return () -> registry;
	}

	public static <T extends VillagerProfession> Supplier<T> registerVillagerProfession(
		String name,
		Supplier<T> villagerProfession
	) {
		var registry = Registry.register(Registry.VILLAGER_PROFESSION, FriendsAndFoes.makeID(name), villagerProfession.get());
		return () -> registry;
	}
}
