package com.faboslav.friendsandfoes.config;

import net.minecraft.util.Identifier;

public class Settings
{
	public static final String MOD_ID = "friendsandfoes";

	public static Identifier makeID(String path) {
		return new Identifier(
			MOD_ID,
			path
		);
	}

	public static String makeStringID(String name) {
		return MOD_ID + ":" + name;
	}

	private Settings() {
	}
}