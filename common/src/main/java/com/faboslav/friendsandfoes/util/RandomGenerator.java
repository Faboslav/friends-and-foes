package com.faboslav.friendsandfoes.util;

import java.util.Random;

public final class RandomGenerator
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

	public static float generateRandomFloat() {
		return generateFloat(0, 1);
	}

	private RandomGenerator() {
	}
}
