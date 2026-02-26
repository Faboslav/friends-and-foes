package com.faboslav.friendsandfoes.common.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

public class BasicItemTrade implements VillagerTrades.ItemListing  {
	private final Item itemToTrade;
	private final Item itemToReceive;
	private final int amountToGive;
	private final int amountToReceive;
	protected final int maxUses;
	protected final int experience;
	protected final float multiplier;

	public BasicItemTrade(Item itemToTrade, Item itemToReceive, int amountToGive,  int amountToReceive) {
		this(itemToReceive, itemToTrade, amountToGive, amountToReceive, 20, 2, 0.05F);
	}

	public BasicItemTrade(Item itemToTrade, Item itemToReceive, int amountToGive, int amountToReceive, int maxUses, int experience, float multiplier) {
		this.itemToTrade = itemToTrade;
		this.itemToReceive = itemToReceive;
		this.amountToGive = amountToGive;
		this.amountToReceive = amountToReceive;
		this.maxUses = maxUses;
		this.experience = experience;
		this.multiplier = multiplier;
	}

	@Override
	public MerchantOffer getOffer(
		//? if >= 1.21.11 {
		ServerLevel serverLevel,
		//?}
		Entity entity,
		RandomSource random
	) {
		ItemCost in = new ItemCost(this.itemToTrade, this.amountToGive);
		ItemStack out = new ItemStack(this.itemToReceive, this.amountToReceive);
		return new MerchantOffer(in, out, this.maxUses, this.experience, this.multiplier);
	}
}