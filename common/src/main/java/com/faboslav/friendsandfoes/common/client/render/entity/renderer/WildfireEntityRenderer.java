package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.WildfireEntityModel;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class WildfireEntityRenderer extends MobRenderer<WildfireEntity, WildfireEntityModel<WildfireEntity>>
{
	public static final float SCALE = 1.5F;

	public WildfireEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new WildfireEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.WILDFIRE_LAYER)), 0.35F);
	}

	@Override
	protected int getBlockLightLevel(WildfireEntity wildfire, BlockPos blockPos) {
		return 15;
	}

	@Override
	protected void scale(WildfireEntity wildfire, PoseStack matrixStack, float f) {
		matrixStack.scale(SCALE, SCALE, SCALE);
	}

	@Override
	public ResourceLocation getTextureLocation(WildfireEntity wildfire) {
		return FriendsAndFoes.makeID("textures/entity/wildfire/wildfire.png");
	}
}