package com.faboslav.friendsandfoes.config.omegaconfig.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Originally from ΩConfig(https://github.com/Draylar/omega-config) by Draylar.
 * Inspired by use in Repurposed Structures by TelepathicGrunt(https://github.com/TelepathicGrunt/RepurposedStructures)
 * <p>
 * Represents a description on a config entry.
 *
 * <p>
 * When a configuration is serialized, any field elements with the {@link Comment} annotation
 * will be prefixed with a // comment on the previous line, with the value specified by this annotation.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Comment
{
	String value() default "";
}
