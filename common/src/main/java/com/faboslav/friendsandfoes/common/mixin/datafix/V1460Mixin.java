package com.faboslav.friendsandfoes.common.mixin.datafix;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import net.minecraft.util.datafix.schemas.V1460;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(V1460.class)
public abstract class V1460Mixin
{
	@Shadow
	protected static void registerMob(Schema schema, Map<String, Supplier<TypeTemplate>> map, String string) {
	}

	@Inject(method = "registerEntities", at = @At("RETURN"))
	public void registerModdedEntities(Schema schema, CallbackInfoReturnable<Map<String, Supplier<TypeTemplate>>> cir) {
		var registeredEntities = cir.getReturnValue();

		registerMob(schema, registeredEntities, "friendsandfoes:copper_golem");
		registerMob(schema, registeredEntities, "friendsandfoes:crab");
		registerMob(schema, registeredEntities, "friendsandfoes:glare");
		registerMob(schema, registeredEntities, "friendsandfoes:iceologer");
		registerMob(schema, registeredEntities, "friendsandfoes:illusioner");
		registerMob(schema, registeredEntities, "friendsandfoes:ice_chunk");
		registerMob(schema, registeredEntities, "friendsandfoes:mauler");
		registerMob(schema, registeredEntities, "friendsandfoes:moobloom");
		registerMob(schema, registeredEntities, "friendsandfoes:rascal");
		registerMob(schema, registeredEntities, "friendsandfoes:tuff_golem");
		registerMob(schema, registeredEntities, "friendsandfoes:wildfire");
		//? if <= 1.21.8 {
		/*registerMob(schema, registeredEntities, "friendsandfoes:player_illusion");
		*///?}
	}
}