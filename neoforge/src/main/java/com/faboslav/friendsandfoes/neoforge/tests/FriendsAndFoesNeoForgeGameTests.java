package com.faboslav.friendsandfoes.neoforge.tests;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.tests.FriendsAndFoesTestFunctions;

//? if >= 1.21.5 {
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;
//?} else {
/*import net.minecraft.gametest.framework.GameTestGenerator;
import net.minecraft.gametest.framework.TestFunction;
import net.neoforged.neoforge.gametest.GameTestHolder;
import java.util.Collection;
*///?}

//? if >= 1.21.5 {
@EventBusSubscriber(modid = FriendsAndFoes.MOD_ID)
//?} else {
/*@GameTestHolder(FriendsAndFoes.MOD_ID)
*///?}
public final class FriendsAndFoesNeoForgeGameTests
{
	//? if >= 1.21.5 {
	@SubscribeEvent
	public static void onRegister(RegisterEvent event) {
		event.register(Registries.TEST_FUNCTION, helper -> FriendsAndFoesTestFunctions.register(helper::register));
	}
	//?} else {
	/*@GameTestGenerator
	public Collection<TestFunction> generateTestFunctions() {
		return FriendsAndFoesTestFunctions.createTestFunctions();
	}
	*///?}
}
