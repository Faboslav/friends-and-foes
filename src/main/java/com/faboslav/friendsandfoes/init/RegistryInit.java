package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.registry.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class RegistryInit
{
	public static void init() {
		System.out.println("registry init");
		BlockRegistry.init();
		BlockTagRegistry.init();
		CriteriaRegistry.init();
		EntityRegistry.init();
		ItemRegistry.init();
		OxidizableBlockRegistry.init();
		SoundRegistry.init();
		VillagerProfessionRegistry.init();
	}

	@Environment(EnvType.CLIENT)
	public static void initClient() {
		BlockRenderLayerMapRegistry.init();
		EntityRendererRegistry.init();
	}
}
