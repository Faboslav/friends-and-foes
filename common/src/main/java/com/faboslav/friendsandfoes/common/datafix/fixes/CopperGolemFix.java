//? if >= 1.21.9 {
package com.faboslav.friendsandfoes.common.datafix.fixes;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.util.datafix.fixes.SimpleEntityRenameFix;

import java.util.Objects;

public class CopperGolemFix extends SimpleEntityRenameFix
{
	public CopperGolemFix(Schema outputSchema) {
		super("CopperGolemFix", outputSchema, false);
	}

	@Override
	protected Pair<String, Dynamic<?>> getNewNameAndTag(String name, Dynamic<?> tag) {
		if (Objects.equals("friendsandfoes:copper_golem", name)) {
			int oxidationLevel = tag.get("OxidationLevel").asInt(0);
			String weatherState = switch (oxidationLevel) {
				case 1 -> "exposed";
				case 2 -> "weathered";
				case 3 -> "oxidized";
				default -> "unaffected";
			};
			boolean isWaxed = tag.get("IsWaxed").asBoolean(false);

			tag = tag.set("next_weather_age", tag.createInt(isWaxed ? -2 : -1));
			tag = tag.set("weather_state", tag.createString(weatherState));
			return Pair.of("minecraft:copper_golem", tag);

		}
		return Pair.of(name, tag);
	}
}
//?}