package com.faboslav.friendsandfoes.modcompat.forge;

import com.faboslav.friendsandfoes.events.lifecycle.ClientSetupEvent;
import com.faboslav.friendsandfoes.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.modcompat.ModCompat;
import com.faboslav.friendsandfoes.modcompat.forge.curios.CuriosTotemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.InterModComms;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

import java.util.EnumSet;
import java.util.function.Predicate;

@SuppressWarnings({"all", "removal", "deprecated"})
public final class CuriosCompat implements ModCompat
{
	public CuriosCompat() {
		InterModComms.sendTo("curios", top.theillusivec4.curios.api.SlotTypeMessage.REGISTER_TYPE, () -> top.theillusivec4.curios.api.SlotTypePreset.CHARM.getMessageBuilder().build());
		ClientSetupEvent.EVENT.addListener(CuriosCompat::registerRenderers);
	}

	private static void registerRenderers(final ClientSetupEvent clientSetupEvent) {
		CuriosRendererRegistry.register(Items.TOTEM_OF_UNDYING, CuriosTotemRenderer::new);
		CuriosRendererRegistry.register(FriendsAndFoesItems.TOTEM_OF_FREEZING.get(), CuriosTotemRenderer::new);
		CuriosRendererRegistry.register(FriendsAndFoesItems.TOTEM_OF_ILLUSION.get(), CuriosTotemRenderer::new);
	}

	@Override
	public EnumSet<Type> compatTypes() {
		return EnumSet.of(Type.CUSTOM_EQUIPMENT_SLOTS);
	}

	@Override
	@Nullable
	public ItemStack getEquippedItemFromCustomSlots(Entity entity, Predicate<ItemStack> itemStackPredicate) {
		if (entity instanceof PlayerEntity player) {
			return CuriosApi.getCuriosHelper().findFirstCurio(player, itemStackPredicate).map(SlotResult::stack).orElse(null);
		}

		return null;
	}
}
