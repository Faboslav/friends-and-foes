package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(StructurePool.class)
public final class StructurePoolMixin
{
	@Final
	@Shadow
	private List<Pair<StructurePoolElement, Integer>> elementCounts;

	@Final
	@Shadow
	private ObjectArrayList<StructurePoolElement> elements;

	private void addElement(String element, int weight, StructurePool.Projection projection) {
		StructurePoolElement e = StructurePoolElement.ofLegacySingle(element).apply(projection);
		elementCounts.add(new Pair<>(e, weight));
		for (int i = 0; i < weight; i++) {
			elements.add(e);
		}
	}

	@Inject(
		at = @At("TAIL"),
		method = "<init>(Lnet/minecraft/util/Identifier;Lnet/minecraft/util/Identifier;Ljava/util/List;Lnet/minecraft/structure/pool/StructurePool$Projection;)V"
	)
	private void friendsandfoes_addCustomStructures(
		Identifier id,
		Identifier terminatorsId,
		List elementCounts,
		StructurePool.Projection projection,
		CallbackInfo ci
	) {
		if (FriendsAndFoes.getConfig().generateBeekeeperAreaStructure) {
			if (Objects.equals(id.getPath(), "village/plains/houses")) {
				addElement(FriendsAndFoes.makeStringID("village/plains/houses/plains_beekeeper_area"), FriendsAndFoes.getConfig().beekeeperAreaStructureWeight, projection);
			} else if (Objects.equals(id.getPath(), "village/savanna/houses")) {
				addElement(FriendsAndFoes.makeStringID("village/savanna/houses/savanna_beekeeper_area"), FriendsAndFoes.getConfig().beekeeperAreaStructureWeight, projection);
			} else if (Objects.equals(id.getPath(), "village/taiga/houses")) {
				addElement(FriendsAndFoes.makeStringID("village/taiga/houses/taiga_beekeeper_area"), FriendsAndFoes.getConfig().beekeeperAreaStructureWeight, projection);
			}
		}

		if (FriendsAndFoes.getConfig().generateCopperGolemAreaStructure) {
			if (Objects.equals(id.getPath(), "village/desert/houses")) {
				addElement(FriendsAndFoes.makeStringID("village/desert/houses/desert_copper_golem_area"), FriendsAndFoes.getConfig().copperGolemAreaStructureWeight, projection);
			} else if (Objects.equals(id.getPath(), "village/plains/houses")) {
				addElement(FriendsAndFoes.makeStringID("village/plains/houses/plains_copper_golem_area"), FriendsAndFoes.getConfig().copperGolemAreaStructureWeight, projection);
			} else if (Objects.equals(id.getPath(), "village/savanna/houses")) {
				addElement(FriendsAndFoes.makeStringID("village/savanna/houses/savanna_copper_golem_area"), FriendsAndFoes.getConfig().copperGolemAreaStructureWeight, projection);
			} else if (Objects.equals(id.getPath(), "village/taiga/houses")) {
				addElement(FriendsAndFoes.makeStringID("village/taiga/houses/taiga_copper_golem_area"), FriendsAndFoes.getConfig().copperGolemAreaStructureWeight, projection);
			}
		}

		if (FriendsAndFoes.getConfig().generateCopperGolemInAncientCity) {
			if (Objects.equals(id.getPath(), "ancient_city/city_center")) {
				addElement(FriendsAndFoes.makeStringID("ancient_city/city_center/city_center_1"), FriendsAndFoes.getConfig().copperGolemAncientCityCenterWeight, projection);
				addElement(FriendsAndFoes.makeStringID("ancient_city/city_center/city_center_2"), FriendsAndFoes.getConfig().copperGolemAncientCityCenterWeight, projection);
				addElement(FriendsAndFoes.makeStringID("ancient_city/city_center/city_center_3"), FriendsAndFoes.getConfig().copperGolemAncientCityCenterWeight, projection);
			}
		}
	}
}