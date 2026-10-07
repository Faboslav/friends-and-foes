package com.faboslav.friendsandfoes.forge.tests;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.tests.FriendsAndFoesTestFunctions;
import net.minecraft.gametest.framework.GameTestGenerator;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraftforge.gametest.GameTestHolder;

import java.util.Collection;

@GameTestHolder(FriendsAndFoes.MOD_ID)
public final class FriendsAndFoesForgeGameTests
{
	@GameTestGenerator
	public Collection<TestFunction> generateTestFunctions() {
		return FriendsAndFoesTestFunctions.createTestFunctions();
	}
}
