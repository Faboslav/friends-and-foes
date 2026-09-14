package com.faboslav.friendsandfoes.common.mixin.plugin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FriendsAndFoesMixinPlugin implements IMixinConfigPlugin
{
	private String mixinPackage;

	@Override
	public void onLoad(String mixinPackage) {
		this.mixinPackage = mixinPackage;
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if (mixinClassName.equals("com.faboslav.friendsandfoes.common.mixin.IntegratedServerLoaderMixin")) {
			return this.isClassAvailable("me.earth.mc_runtime_test.McRuntimeTest");
		}

		/*
		if (
			mixinClassName.equals("com.faboslav.friendsandfoes.common.mixin.AddCustomSpawnGroupMixin")
			|| mixinClassName.equals("com.faboslav.friendsandfoes.common.mixin.AddCustomRaidMemberMixin")
		) {
			return !this.isClassAvailable("net.minecraftforge.fml.common.asm.RuntimeEnumExtender");
		}*/

		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins() {
		List<String> mixins = new ArrayList<>();

		if (this.mixinPackage.equals("com.faboslav.friendsandfoes.common.mixin")) {
			if (this.isClassAvailable("me.earth.mc_runtime_test.McRuntimeTest") && this.isClassAvailable("com.faboslav.friendsandfoes.common.mixin.GameTestRegistryMixin")) {
				mixins.add("GameTestRegistryMixin");
			}

			if (this.isClassAvailable("me.earth.mc_runtime_test.McRuntimeTest") && this.isClassAvailable("com.faboslav.friendsandfoes.common.mixin.CommandsMixin")) {
				mixins.add("CommandsMixin");
			}
		}

		return mixins;
	}
	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	private boolean isClassAvailable(String className) {
		String classPath = className.replace('.', '/') + ".class";
		return getClass().getClassLoader().getResource(classPath) != null;
	}
}