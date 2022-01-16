package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.config.Settings;
import com.mojang.datafixers.util.Pair;
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
public class StructurePoolMixin
{
	@Final
	@Shadow
	private List<Pair<StructurePoolElement, Integer>> elementCounts;

	@Final
	@Shadow
	private List<StructurePoolElement> elements;

	private void addElement(String element, int weight, StructurePool.Projection projection) {
		StructurePoolElement e = StructurePoolElement.ofLegacySingle(element).apply(projection);
		elementCounts.add(new Pair<>(e, weight));
		for (int i = 0; i < weight; i++) {
			elements.add(e);
		}
	}

	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/util/Identifier;Lnet/minecraft/util/Identifier;Ljava/util/List;Lnet/minecraft/structure/pool/StructurePool$Projection;)V")
	private void addStructures(
		Identifier id,
		Identifier terminatorsId,
		List elementCounts,
		StructurePool.Projection projection,
		CallbackInfo ci
	) {
		if (FriendsAndFoes.CONFIG.generateBeekeeperAreaStructure) {
			if (Objects.equals(id.getPath(), "village/plains/houses")) {
				addElement(Settings.makeStringID("village/plains/houses/plains_beekeeper_area"), 1, projection);
			} else if (Objects.equals(id.getPath(), "village/taiga/houses")) {
				addElement(Settings.makeStringID("village/taiga/houses/taiga_beekeeper_area"), 1, projection);
			} else if (Objects.equals(id.getPath(), "village/savanna/houses")) {
				addElement(Settings.makeStringID("village/savanna/houses/savanna_beekeeper_area"), 1, projection);
			} else if (Objects.equals(id.getPath(), "village/common/bee")) {
				addElement(Settings.makeStringID("village/common/animals/bee"), 1, projection);
			}
		}

		/*
		if (FriendsAndFoes.CONFIG.generateIllusionerShackStructure) {
			if (Objects.equals(id.getPath(), "illusioner_shack/wolf")) {
				addElement(Settings.makeStringID("illusioner_shack/wolf"), 1, projection);
			} else if (Objects.equals(id.getPath(), "illusioner_shack/illusioner")) {
				addElement(Settings.makeStringID("illusioner_shack/illusioner"), 1, projection);
			} else if (Objects.equals(id.getPath(), "illusioner_shack/villager")) {
				addElement(Settings.makeStringID("illusioner_shack/villager"), 1, projection);
			}
		}*/
	}
}