package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;

public class VersionedInteractionResult
{
	public static InteractionResult success(Entity entity) {
		InteractionResult interactionResult;

		interactionResult = InteractionResult.sidedSuccess(entity.level().isClientSide());

		return interactionResult;
	}
}
