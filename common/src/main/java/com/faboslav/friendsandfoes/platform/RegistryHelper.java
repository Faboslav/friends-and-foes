package com.faboslav.friendsandfoes.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
import net.minecraft.sound.SoundEvent;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.function.Supplier;

public final class RegistryHelper
{
	@ExpectPlatform
	public static void addToItemGroupBefore(ItemGroup itemGroup, Item item, Item before) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static void addToItemGroupAfter(ItemGroup itemGroup, Item item, Item after) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static <T extends Activity> Supplier<T> registerActivity(String name, Supplier<T> activity) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static void registerEntityModelLayer(EntityModelLayer location, Supplier<TexturedModelData> definition) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static <T extends Entity> void registerEntityRenderer(
		Supplier<EntityType<T>> type,
		EntityRendererFactory<T> renderProvider
	) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(
		String name,
		Supplier<EntityType<T>> entityType
	) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static void registerEntityAttribute(
		Supplier<? extends EntityType<? extends LivingEntity>> type,
		Supplier<DefaultAttributeContainer.Builder> attribute
	) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static <T extends Item> Supplier<T> registerSpawnEggItem(
		String name,
		Supplier<? extends EntityType<? extends MobEntity>> type,
		int backgroundColor,
		int highlightColor,
		Item.Settings props
	) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static <T extends MemoryModuleType<?>> Supplier<T> registerMemoryModuleType(
		String name,
		Supplier<T> memoryModuleType
	) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static <T extends PointOfInterestType> Supplier<T> registerPointOfInterestType(
		String name,
		Supplier<T> pointOfInterestType
	) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static void registerRenderType(RenderLayer type, Block... blocks) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static <T extends SoundEvent> Supplier<T> registerSoundEvent(String name, Supplier<T> soundEvent) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static <T extends VillagerProfession> Supplier<T> registerVillagerProfession(
		String name,
		Supplier<T> villagerProfession
	) {
		throw new AssertionError();
	}

	public static <T extends Block> void registerFlammableBlock(Supplier<T> block, int burnChance, int spreadChance) {
		registerFlammableBlock(Blocks.FIRE, block, burnChance, spreadChance);
	}

	@ExpectPlatform
	public static <T extends Block> void registerFlammableBlock(
		Block fireBlock,
		Supplier<T> block,
		int burnChance,
		int spreadChance
	) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static <T extends Structure> void registerStructureType(
		String name,
		StructureType<T> structureCodec
	) {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static <T extends StructureProcessor> void registerStructureProcessorType(
		String name,
		StructureProcessorType<T> structureProcessorType
	) {
		throw new AssertionError();
	}
}
