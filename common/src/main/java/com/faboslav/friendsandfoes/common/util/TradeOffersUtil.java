package com.faboslav.friendsandfoes.common.util;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;

public final class TradeOffersUtil
{
	public static class SellItemFactory implements VillagerTrades.ItemListing
	{
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

		public MerchantOffer getOffer(Entity entity, RandomSource random) {
			return new MerchantOffer(new ItemCost(Items.EMERALD, this.price), new ItemStack(this.sell.getItem(), this.count), this.maxUses, this.experience, this.multiplier);
		}
	}

	public static class BuyForOneEmeraldFactory implements VillagerTrades.ItemListing
	{
		private final Item buy;
		private final int price;
		private final int maxUses;
		private final int experience;
		private final float multiplier;

		public BuyForOneEmeraldFactory(ItemLike item, int price, int maxUses, int experience) {
			this.buy = item.asItem();
			this.price = price;
			this.maxUses = maxUses;
			this.experience = experience;
			this.multiplier = 0.05F;
		}

		public MerchantOffer getOffer(Entity entity, RandomSource random) {
			ItemStack itemStack = new ItemStack(this.buy, this.price);
			return new MerchantOffer(new ItemCost(itemStack.getItem(), 1), new ItemStack(Items.EMERALD), this.maxUses, this.experience, this.multiplier);
		}
	}

	private TradeOffersUtil() {
	}
}
