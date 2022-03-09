package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public class ModItems
{
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(FriendsAndFoes.MOD_ID, Registry.ITEM_KEY);

	public final static Item COPPER_GOLEM_SPAWN_EGG;
	public final static Item GLARE_SPAWN_EGG;
	public final static Item ICEOLOGER_SPAWN_EGG;
	public final static Item ILLUSIONER_SPAWN_EGG;
	public final static Item MOOBLOOM_SPAWN_EGG;
	public static final Item BUTTERCUP;
	public static final Item ACACIA_BEEHIVE;
	public static final Item BIRCH_BEEHIVE;
	public static final Item CRIMSON_BEEHIVE;
	public static final Item DARK_OAK_BEEHIVE;
	public static final Item JUNGLE_BEEHIVE;
	public static final Item SPRUCE_BEEHIVE;
	public static final Item WARPED_BEEHIVE;
	public static final Item COPPER_BUTTON;
	public static final Item EXPOSED_COPPER_BUTTON;
	public static final Item WEATHERED_COPPER_BUTTON;
	public static final Item OXIDIZED_COPPER_BUTTON;
	public static final Item WAXED_COPPER_BUTTON;
	public static final Item WAXED_EXPOSED_COPPER_BUTTON;
	public static final Item WAXED_WEATHERED_COPPER_BUTTON;
	public static final Item WAXED_OXIDIZED_COPPER_BUTTON;

	static {
		COPPER_GOLEM_SPAWN_EGG = registerSpawnEgg("copper_golem", ModEntity.COPPER_GOLEM, 0x9A5038, 0xE3826C);
		GLARE_SPAWN_EGG = registerSpawnEgg("glare", ModEntity.GLARE, 0x70922D, 0x6A5227);
		ICEOLOGER_SPAWN_EGG = registerSpawnEgg("iceologer", ModEntity.ICEOLOGER, 0x173873, 0x949b9b);
		ILLUSIONER_SPAWN_EGG = registerSpawnEgg("illusioner", EntityType.ILLUSIONER, 0x603e5c, 0x888e8e);
		MOOBLOOM_SPAWN_EGG = registerSpawnEgg("moobloom", ModEntity.MOOBLOOM, 0xFACA00, 0xf7EDC1);
		BUTTERCUP = register("buttercup", new BlockItem(ModBlocks.BUTTERCUP, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		ACACIA_BEEHIVE = register("acacia_beehive", new BlockItem(ModBlocks.ACACIA_BEEHIVE, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		BIRCH_BEEHIVE = register("birch_beehive", new BlockItem(ModBlocks.BIRCH_BEEHIVE, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		CRIMSON_BEEHIVE = register("crimson_beehive", new BlockItem(ModBlocks.CRIMSON_BEEHIVE, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		DARK_OAK_BEEHIVE = register("dark_oak_beehive", new BlockItem(ModBlocks.DARK_OAK_BEEHIVE, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		JUNGLE_BEEHIVE = register("jungle_beehive", new BlockItem(ModBlocks.JUNGLE_BEEHIVE, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		SPRUCE_BEEHIVE = register("spruce_beehive", new BlockItem(ModBlocks.SPRUCE_BEEHIVE, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		WARPED_BEEHIVE = register("warped_beehive", new BlockItem(ModBlocks.WARPED_BEEHIVE, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
		COPPER_BUTTON = register("copper_button", new BlockItem(ModBlocks.COPPER_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		EXPOSED_COPPER_BUTTON = register("exposed_copper_button", new BlockItem(ModBlocks.EXPOSED_COPPER_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WEATHERED_COPPER_BUTTON = register("weathered_copper_button", new BlockItem(ModBlocks.WEATHERED_COPPER_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		OXIDIZED_COPPER_BUTTON = register("oxidized_copper_button", new BlockItem(ModBlocks.OXIDIZED_COPPER_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_COPPER_BUTTON = register("waxed_copper_button", new BlockItem(ModBlocks.WAXED_COPPER_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_EXPOSED_COPPER_BUTTON = register("waxed_exposed_copper_button", new BlockItem(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_WEATHERED_COPPER_BUTTON = register("waxed_weathered_copper_button", new BlockItem(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
		WAXED_OXIDIZED_COPPER_BUTTON = register("waxed_oxidized_copper_button", new BlockItem(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
	}

	private static Item registerSpawnEgg(
		String name,
		EntityType<? extends MobEntity> entityType,
		int backgroundColor,
		int dotsColor
	) {
		SpawnEggItem spawnEggItem = new SpawnEggItem(
			entityType,
			backgroundColor,
			dotsColor,
			new Item.Settings().maxCount(64).group(ItemGroup.MISC)
		);

		ItemDispenserBehavior behavior = new ItemDispenserBehavior()
		{
			public ItemStack dispenseSilently(
				BlockPointer pointer,
				ItemStack stack
			) {
				Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
				EntityType<?> entityType = ((SpawnEggItem) stack.getItem()).getEntityType(stack.getNbt());
				entityType.spawnFromItemStack(
					pointer.getWorld(),
					stack,
					null,
					pointer.getPos().offset(direction),
					SpawnReason.DISPENSER,
					direction != Direction.UP,
					false
				);
				stack.decrement(1);
				return stack;
			}
		};
		DispenserBlock.registerBehavior(
			spawnEggItem,
			behavior
		);
		name = name + "_spawn_egg";

		register(name, spawnEggItem);

		return spawnEggItem;
	}

	private static Item register(
		String name,
		Item item
	) {
		ITEMS.register(name, () -> item);

		return item;
	}

	public static void init() {
		ITEMS.register();
	}
}
