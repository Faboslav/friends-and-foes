package com.faboslav.friendsandfoes.common.mixin.datafix;

import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.util.datafix.DataFixers;

//? if >= 1.21.9 {
import com.faboslav.friendsandfoes.common.datafix.fixes.CopperGolemFix;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.fixes.BlockRenameFix;
import net.minecraft.util.datafix.fixes.ItemRenameFix;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
//?}

@Mixin(DataFixers.class)
public abstract class DataFixersMixin
{
	//? if >= 1.21.9 {
	@Definition(id = "builder", local = @Local(type = DataFixerBuilder.class, argsOnly = true))
	@Definition(id = "addSchema", method = "Lcom/mojang/datafixers/DataFixerBuilder;addSchema(ILjava/util/function/BiFunction;)Lcom/mojang/datafixers/schemas/Schema;", remap = false)
	@Definition(id = "SAME_NAMESPACED", field = "Lnet/minecraft/util/datafix/DataFixers;SAME_NAMESPACED:Ljava/util/function/BiFunction;")
	@Expression("builder.addSchema(4544, SAME_NAMESPACED)")
	@WrapOperation(method = "addFixers", at = @At("MIXINEXTRAS:EXPRESSION"))
	private static Schema addCopperAgeVersionFixes(
		DataFixerBuilder builder,
		int version,
		BiFunction<Integer, Schema, Schema> factory,
		Operation<Schema> original
	) {
		var schema = original.call(builder, version, factory);
		builder.addFixer(new CopperGolemFix(schema));

		var copperAgeRenames = Map.of(
			"friendsandfoes:exposed_lightning_rod", "minecraft:exposed_lightning_rod",
			"friendsandfoes:weathered_lightning_rod", "minecraft:weathered_lightning_rod",
			"friendsandfoes:oxidized_lightning_rod", "minecraft:oxidized_lightning_rod",
			"friendsandfoes:waxed_lightning_rod", "minecraft:waxed_lightning_rod",
			"friendsandfoes:waxed_exposed_lightning_rod", "minecraft:waxed_exposed_lightning_rod",
			"friendsandfoes:waxed_weathered_lightning_rod", "minecraft:waxed_weathered_lightning_rod",
			"friendsandfoes:waxed_oxidized_lightning_rod", "minecraft:waxed_oxidized_lightning_rod"
		);

		builder.addFixer(BlockRenameFix.create(schema, "Rename friends&foes lightning rod blocks to vanilla", createRenamer(copperAgeRenames)));
		builder.addFixer(ItemRenameFix.create(schema, "Rename friends&foes lightning rods items to vanilla", createRenamer(copperAgeRenames)));
		return schema;
	}

	@Unique
	private static UnaryOperator<String> createRenamer(Map<String, String> map) {
		return (string) -> map.getOrDefault(NamespacedSchema.ensureNamespaced(string), string);
	}
	//?}
}