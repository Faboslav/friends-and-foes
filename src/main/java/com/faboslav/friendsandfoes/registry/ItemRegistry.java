package com.faboslav.friendsandfoes.registry;

import com.faboslav.friendsandfoes.config.Settings;
import com.google.common.collect.Lists;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

import java.util.List;

/**
 * @see net.minecraft.item.Items
 */
public class ItemRegistry
{
    public static final List<Item> ITEMS = Lists.newArrayList();
    public final static Item MOOBLOOM_EGG = registerSpawnEgg("moobloom", EntityRegistry.MOOBLOOM, 0xFACA00, 0xf7EDC1);
    public final static Item COPPER_GOLEM_SPAWN_EGG = registerSpawnEgg("copper_golem", EntityRegistry.COPPER_GOLEM, 0x9A5038, 0xE3826C);
    public static final Item BUTTERCUP = register("buttercup", new BlockItem(BlockRegistry.BUTTERCUP, new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(64)));
    public static final Item COPPER_BUTTON = register("copper_button", new BlockItem(BlockRegistry.COPPER_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)));
    public static final Item EXPOSED_COPPER_BUTTON = register(
            "exposed_copper_button",
            new BlockItem(
                    BlockRegistry.EXPOSED_COPPER_BUTTON,
                    new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)
            )
    );
    public static final Item OXIDIZED_COPPER_BUTTON = register(
            "oxidized_copper_button",
            new BlockItem(
                    BlockRegistry.OXIDIZED_COPPER_BUTTON,
                    new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)
            )
    );
    public static final Item WAXED_COPPER_BUTTON = register(
            "waxed_copper_button",
            new BlockItem(
                    BlockRegistry.WAXED_COPPER_BUTTON,
                    new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)
            )
    );
    public static final Item WAXED_EXPOSED_COPPER_BUTTON = register(
            "waxed_exposed_copper_button",
            new BlockItem(
                    BlockRegistry.WAXED_EXPOSED_COPPER_BUTTON,
                    new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)
            )
    );
    public static final Item WAXED_OXIDIZED_COPPER_BUTTON = register(
            "waxed_oxidized_copper_button",
            new BlockItem(
                    BlockRegistry.WAXED_OXIDIZED_COPPER_BUTTON,
                    new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)
            )
    );
    public static final Item WAXED_WEATHERED_COPPER_BUTTON = register(
            "waxed_weathered_copper_button",
            new BlockItem(
                    BlockRegistry.WAXED_WEATHERED_COPPER_BUTTON,
                    new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)
            )
    );
    public static final Item WEATHERED_COPPER_BUTTON = register(
            "weathered_copper_button",
            new BlockItem(
                    BlockRegistry.WEATHERED_COPPER_BUTTON,
                    new Item.Settings().group(ItemGroup.REDSTONE).maxCount(64)
            )
    );


    private static Item registerSpawnEgg(
            String name,
            EntityType<? extends MobEntity> entityType,
            int background,
            int dots
    ) {
        SpawnEggItem spawnEggItem = new SpawnEggItem(
                entityType,
                background,
                dots,
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
                        direction!=Direction.UP,
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

        registerItem(
                name,
                spawnEggItem
        );

        return spawnEggItem;
    }

    public static Item registerItem(
            String name,
            Item item
    ) {
        registerItem(
                name,
                item,
                ITEMS
        );
        return item;
    }

    public static Item registerItem(
            String name,
            Item item,
            List<Item> list
    ) {
        Registry.register(
                Registry.ITEM,
                Settings.makeID(name),
                item
        );
        list.add(item);

        return item;
    }


    private static Item register(
            String name,
            Item item
    ) {
        Registry.register(
                Registry.ITEM,
                Settings.makeID(name),
                item
        );

        ITEMS.add(item);

        return item;
    }

    public static void init() {
    }
}
