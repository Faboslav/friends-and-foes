package com.faboslav.friendsandfoes.neoforge.platform;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.platform.CustomSpawnGroup;
import com.faboslav.friendsandfoes.common.util.CustomRaidMember;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

import java.util.function.Supplier;

public final class NeoForgeEnumExtensions
{
	public static final EnumProxy<MobCategory> FRIENDSANDFOES_GLARES = new EnumProxy<>(
		MobCategory.class,
		CustomSpawnGroup.GLARES_INTERNAL_NAME,
		//? if >=26.2 {
		"GL",
		//?}
		CustomSpawnGroup.GLARES_SPAWN_CAP,
		CustomSpawnGroup.GLARES_PEACEFUL,
		CustomSpawnGroup.GLARES_RARE,
		CustomSpawnGroup.GLARES_IMMEDIATE_DESPAWN_RANGE
	);

	public static final EnumProxy<MobCategory> FRIENDSANDFOES_RASCALS = new EnumProxy<>(
		MobCategory.class,
		CustomSpawnGroup.RASCALS_INTERNAL_NAME,
		//? if >=26.2 {
		"RA",
		//?}
		CustomSpawnGroup.RASCALS_SPAWN_CAP,
		CustomSpawnGroup.RASCALS_PEACEFUL,
		CustomSpawnGroup.RASCALS_RARE,
		CustomSpawnGroup.RASCALS_IMMEDIATE_DESPAWN_RANGE
	);

	public static final EnumProxy<Raid.RaiderType> FRIENDSANDFOES_ICEOLOGER = new EnumProxy<>(
		Raid.RaiderType.class,
		(Supplier<EntityType<? extends Raider>>) () -> FriendsAndFoesEntityTypes.ICEOLOGER.get(),
		CustomRaidMember.ICEOLOGER_COUNT_IN_WAVE
	);

	public static final EnumProxy<Raid.RaiderType> FRIENDSANDFOES_ILLUSIONER = new EnumProxy<>(
		Raid.RaiderType.class,
		(Supplier<EntityType<? extends Raider>>) () -> FriendsAndFoesEntityTypes.ILLUSIONER.get(),
		CustomRaidMember.ILLUSIONER_COUNT_IN_WAVE
	);

	private NeoForgeEnumExtensions() {
	}
}
