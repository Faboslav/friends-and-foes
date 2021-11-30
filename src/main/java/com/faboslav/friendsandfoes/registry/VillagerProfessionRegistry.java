package com.faboslav.friendsandfoes.registry;

import com.faboslav.friendsandfoes.config.Settings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Random;
import java.util.Set;

@SuppressWarnings({"rawtypes", "unchecked"})
public class VillagerProfessionRegistry {
    public static final VillagerProfession BEEKEEPER;
    //public static final VillagerProfession INVENTOR;

    static {
        BEEKEEPER = registerBeekeeper();
        //INVENTOR = registerBeekeeper();
    }

    public static VillagerProfession registerBeekeeper()
    {
        // VillagerProfession
        VillagerProfession beekeeperVillagerProfession = VillagerProfessionBuilder.create()
                .id(Settings.makeID("beekeeper"))
                .workstation(PointOfInterestType.BEEHIVE)
                // TODO custom sound for subtitles
                .workSound(SoundEvents.ENTITY_ITEM_FRAME_REMOVE_ITEM)
                .harvestableItems(Items.HONEYCOMB)
                .build();
        Registry.register(Registry.VILLAGER_PROFESSION, Settings.makeID("beekeeper"), beekeeperVillagerProfession);

        // Trades
        TradeOffers.PROFESSION_TO_LEVELED_TRADE.put(
                beekeeperVillagerProfession, new Int2ObjectOpenHashMap(ImmutableMap.of(
                        1,
                        new TradeOffers.Factory[]{
                                new BuyForOneEmeraldFactory(ItemRegistry.BUTTERCUP, 10, 16, 2),
                                new BuyForOneEmeraldFactory(Items.DANDELION, 10, 16, 2),
                                new BuyForOneEmeraldFactory(Items.SUNFLOWER, 10, 16, 2),
                        },
                        2,
                        new TradeOffers.Factory[]{
                                new BuyForOneEmeraldFactory(Items.GLASS_BOTTLE, 9, 12, 10),
                                new SellItemFactory(Items.HONEY_BOTTLE, 3, 1, 12, 5),
                        },
                        3,
                        new TradeOffers.Factory[]{
                                new BuyForOneEmeraldFactory(Items.SHEARS, 1, 12, 20),
                                new SellItemFactory(Items.HONEY_BLOCK, 10, 1, 12, 10),
                        },
                        4,
                        new TradeOffers.Factory[]{
                                new SellItemFactory(Items.HONEYCOMB, 4, 1, 12, 15),
                        },
                        5,
                        new TradeOffers.Factory[]{
                                new SellItemFactory(Items.HONEYCOMB_BLOCK, 12, 1, 12, 30),
                        }
                ))
        );

        return beekeeperVillagerProfession;
    }

    public static class SellItemFactory implements TradeOffers.Factory {
        private final ItemStack sell;
        private final int price;
        private final int count;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public SellItemFactory(Item item, int price, int count, int maxUses, int experience) {
            this(new ItemStack(item), price, count, maxUses, experience);
        }

        public SellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience) {
            this(stack, price, count, maxUses, experience, 0.05F);
        }

        public SellItemFactory(ItemStack stack, int price, int count, int maxUses, int experience, float multiplier) {
            this.sell = stack;
            this.price = price;
            this.count = count;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(new ItemStack(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static class BuyForOneEmeraldFactory implements TradeOffers.Factory {
        private final Item buy;
        private final int price;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public BuyForOneEmeraldFactory(ItemConvertible item, int price, int maxUses, int experience) {
            this.buy = item.asItem();
            this.price = price;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        public TradeOffer create(Entity entity, Random random) {
            ItemStack itemStack = new ItemStack(this.buy, this.price);
            return new TradeOffer(itemStack, new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
        }
    }

    public static void init() {}
}
