
package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.client.render.entity.feature.PlayerIllusionCapeLayer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.PlayerIllusionEntityModel;
import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.common.util.PlayerSkinProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.*;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.StyledFormat;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

@SuppressWarnings({"rawtypes", "unchecked"})

public final class PlayerIllusionEntityRenderer extends LivingEntityRenderer<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>>

{
	public PlayerIllusionEntityRenderer(EntityRendererProvider.Context context, boolean useSlimModel) {

		super(context, new PlayerIllusionEntityModel<>(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM:ModelLayers.PLAYER), useSlimModel), 0.5F);

		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
		this.addLayer(new PlayerItemInHandLayer(this, context.getItemInHandRenderer()));
		this.addLayer(new ArrowLayer(context, this));
		this.addLayer(new PlayerIllusionCapeLayer(this));
		this.addLayer(new CustomHeadLayer(this, context.getModelSet(), context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer(this, context.getModelSet()));
		this.addLayer(new BeeStingerLayer(this));

	}

	@Override

	protected void renderNameTag(PlayerIllusionEntity entity, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick)

	{
	}

	@Override

	public ResourceLocation getTextureLocation(PlayerIllusionEntity playerIllusion)

	{

		return PlayerSkinProvider.getSkinTextures(playerIllusion).texture();

	}
}
