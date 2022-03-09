package com.faboslav.friendsandfoes.util;

import java.util.Random;

public class RandomGenerator
{
	private static final Random random;

	static {
		random = new Random();
	}

	public static int generateInt(
		int min,
		int max
	) {
		return random.nextInt((max - min) + 1) + min;
	}

	public static float generateFloat(
		float min,
		float max
	) {
		return min + random.nextFloat() * (max - min);
	}
}
