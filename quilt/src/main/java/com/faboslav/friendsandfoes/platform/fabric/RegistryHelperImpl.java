package com.faboslav.friendsandfoes.platform.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.function.Supplier;

public final class RegistryHelperImpl
{
	public static void addToItemGroupBefore(ItemGroup itemGroup, Item item, Item before) {
		ItemGroupEvents.modifyEntriesEvent(itemGroup).register((content) -> {
			content.addBefore(before, item.getDefaultStack());
		});
	}

	public static void addToItemGroupAfter(ItemGroup itemGroup, Item item, Item after) {
		ItemGroupEvents.modifyEntriesEvent(itemGroup).register((content) -> {
			content.addAfter(after, item.getDefaultStack());
		});
	}

	public static <T extends Activity> Supplier<T> registerActivity(String name, Supplier<T> activity) {
		var registry = Registry.register(Registries.ACTIVITY, FriendsAndFoes.makeID(name), activity.get());
		return () -> registry;
	}

	public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
		var registry = Registry.register(Registries.BLOCK, FriendsAndFoes.makeID(name), block.get());
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
		var registry = Registry.register(Registries.ENTITY_TYPE, FriendsAndFoes.makeID(name), entityType.get());
		return () -> registry;
	}

	public static void registerEntityAttribute(
		Supplier<? extends EntityType<? extends LivingEntity>> type,
		Supplier<DefaultAttributeContainer.Builder> attribute
	) {
		FabricDefaultAttributeRegistry.register(type.get(), attribute.get());
	}

	public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
		var registry = Registry.register(Registries.ITEM, FriendsAndFoes.makeID(name), item.get());
		return () -> registry;
	}

	public static <T extends Item> Supplier<T> registerSpawnEggItem(
		String name,
		Supplier<? extends EntityType<? extends MobEntity>> type,
		int backgroundColor,
		int highlightColor,
		Item.Settings props
	) {
		return (Supplier<T>) registerItem(name, () -> new SpawnEggItem(type.get(), backgroundColor, highlightColor, props));
	}

	public static <T extends MemoryModuleType<?>> Supplier<T> registerMemoryModuleType(
		String name,
		Supplier<T> memoryModuleType
	) {
		var registry = Registry.register(Registries.MEMORY_MODULE_TYPE, FriendsAndFoes.makeID(name), memoryModuleType.get());
		return () -> registry;
	}

	public static <T extends PointOfInterestType> Supplier<T> registerPointOfInterestType(
		String name,
		Supplier<T> pointOfInterestType
	) {
		var registry = Registry.register(Registries.POINT_OF_INTEREST_TYPE, FriendsAndFoes.makeID(name), pointOfInterestType.get());
		return () -> registry;
	}

	public static void registerRenderType(RenderLayer type, Block... blocks) {
		BlockRenderLayerMap.INSTANCE.putBlocks(type, blocks);
	}

	public static <T extends SoundEvent> Supplier<T> registerSoundEvent(String name, Supplier<T> soundEvent) {
		var registry = Registry.register(Registries.SOUND_EVENT, FriendsAndFoes.makeID(name), soundEvent.get());
		return () -> registry;
	}

	public static <T extends VillagerProfession> Supplier<T> registerVillagerProfession(
		String name,
		Supplier<T> villagerProfession
	) {
		var registry = Registry.register(Registries.VILLAGER_PROFESSION, FriendsAndFoes.makeID(name), villagerProfession.get());
		return () -> registry;
	}

	public static <T extends Block> void registerFlammableBlock(
		Block fireBlock,
		Supplier<T> block,
		int burnChance,
		int spreadChance
	) {
		FlammableBlockRegistry.getInstance(fireBlock).add(block.get(), burnChance, spreadChance);
	}

	public static <T extends Structure> void registerStructureType(
		String name,
		StructureType<T> structureType
	) {
		Registry.register(Registries.STRUCTURE_TYPE, FriendsAndFoes.makeID(name), structureType);
	}

	public static <T extends StructureProcessor> void registerStructureProcessorType(
		String name,
		StructureProcessorType<T> structureProcessorType
	) {
		Registry.register(Registries.STRUCTURE_PROCESSOR, FriendsAndFoes.makeID(name), structureProcessorType);
	}
}
