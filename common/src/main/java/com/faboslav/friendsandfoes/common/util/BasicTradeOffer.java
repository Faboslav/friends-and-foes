package com.faboslav.friendsandfoes.common.util;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;

public class BasicTradeOffer implements TradeOffers.Factory {
	private final Item itemToTrade;
	private final Item itemToReceive;
	private final int amountToGive;
	private final int amountToReceive;
	protected final int maxUses;
	protected final int experience;
	protected final float multiplier;

	public BasicTradeOffer(Item itemToTrade, Item itemToReceive, int amountToGive, int amountToReceive, int maxUses, int experience, float multiplier) {
		this.itemToTrade = itemToTrade;
		this.itemToReceive = itemToReceive;
		this.amountToGive = amountToGive;
		this.amountToReceive = amountToReceive;
		this.maxUses = maxUses;
		this.experience = experience;
		this.multiplier = multiplier;
	}

	@Override
	public TradeOffer create(Entity entity, Random random) {
		TradedItem in = new TradedItem(this.itemToTrade, this.amountToGive);
		ItemStack out = new ItemStack(this.itemToReceive, this.amountToReceive);
		return new TradeOffer(in, out, this.maxUses, this.experience, this.multiplier);
	}
}