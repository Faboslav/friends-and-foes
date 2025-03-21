package com.faboslav.friendsandfoes.common.modcompat.neoforge;

import com.faboslav.friendsandfoes.common.events.lifecycle.ClientSetupEvent;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.modcompat.ModCompat;
import com.faboslav.friendsandfoes.common.modcompat.neoforge.curios.CuriosTotemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.EnumSet;
import java.util.function.Predicate;

@SuppressWarnings({"all", "removal"})
public final class CuriosCompat implements ModCompat
{
	public CuriosCompat() {
		ClientSetupEvent.EVENT.addListener(CuriosCompat::registerRenderers);
	}

	@Override
	public EnumSet<Type> compatTypes() {
		return EnumSet.of(Type.CUSTOM_EQUIPMENT_SLOTS);
	}

	private static void registerRenderers(final ClientSetupEvent clientSetupEvent) {
		CuriosRendererRegistry.register(Items.TOTEM_OF_UNDYING, CuriosTotemRenderer::new);
		CuriosRendererRegistry.register(FriendsAndFoesItems.TOTEM_OF_FREEZING.get(), CuriosTotemRenderer::new);
		CuriosRendererRegistry.register(FriendsAndFoesItems.TOTEM_OF_ILLUSION.get(), CuriosTotemRenderer::new);
	}

	@Override
	@Nullable
	public ItemStack getEquippedItemFromCustomSlots(Entity entity, Predicate<ItemStack> itemStackPredicate) {
		if (entity instanceof PlayerEntity player) {
			return CuriosApi.getCuriosInventory(player).map(i -> i.findFirstCurio(itemStackPredicate).map(SlotResult::stack).orElse(null)).orElse(null);
		}

		return null;
	}
}
