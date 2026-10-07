package com.faboslav.friendsandfoes.fabric.tests;

import com.faboslav.friendsandfoes.common.tests.FriendsAndFoesTestFunctions;

//? if >= 1.21.5 {
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
//?} else {
/*import net.minecraft.gametest.framework.GameTestGenerator;
import net.minecraft.gametest.framework.TestFunction;
import java.util.Collection;
*///?}

public final class FriendsAndFoesFabricGameTests
{
	//? if >= 1.21.5 {
	public FriendsAndFoesFabricGameTests() {
		FriendsAndFoesTestFunctions.register((id, test) -> Registry.register(BuiltInRegistries.TEST_FUNCTION, id, test));
	}
	//?} else {
	/*@GameTestGenerator
	public Collection<TestFunction> generateTestFunctions() {
		return FriendsAndFoesTestFunctions.createTestFunctions();
	}
	*///?}
}
