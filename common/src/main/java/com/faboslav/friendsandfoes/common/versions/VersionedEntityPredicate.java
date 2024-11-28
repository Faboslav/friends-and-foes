package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.TargetPredicate;

import java.util.function.Predicate;

public class VersionedEntityPredicate
{
	/*? >=1.21.3 {*/
	public static TargetPredicate.EntityPredicate create(Predicate<Entity> customCallback) {
		return (target, serverWorld) -> {
			return customCallback.test(target);
		};
	}
	/*?} else {*/
	/*public static Predicate<Entity> create(Predicate<Entity> customCallback) {
		return (entity) -> {
			return customCallback.test(entity);
		};
	}
	*//*?}*/
}
