package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.client.render.entity.feature.PlayerIllusionCapeLayer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.PlayerIllusionEntityModel;
import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.*;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

//? >=1.21.3 {
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
//?}

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
//? >=1.21.3 {
public final class PlayerIllusionEntityRenderer extends LivingEntityRenderer<PlayerIllusionEntity, PlayerRenderState, PlayerIllusionEntityModel>
//?} else {
/*public final class PlayerIllusionEntityRenderer extends LivingEntityRenderer<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>>
*///?}
{
	public PlayerIllusionEntityRenderer(EntityRendererProvider.Context context, boolean useSlimModel) {
		//? >=1.21.3 {
		super(context, new PlayerIllusionEntityModel(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM:ModelLayers.PLAYER), useSlimModel), 0.5F);
		this.addLayer(
			new HumanoidArmorLayer(
				this,
				new HumanoidArmorModel(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM_INNER_ARMOR:ModelLayers.PLAYER_INNER_ARMOR)),
				new HumanoidArmorModel(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR:ModelLayers.PLAYER_OUTER_ARMOR)),
				context.getEquipmentRenderer()
			)
		);
		this.addLayer(new PlayerItemInHandLayer(this));
		this.addLayer(new ArrowLayer(this, context));
		this.addLayer(new PlayerIllusionCapeLayer(this, context.getModelSet(), context.getEquipmentAssets()));
		this.addLayer(new CustomHeadLayer(this, context.getModelSet()));
		this.addLayer(new WingsLayer(this, context.getModelSet(), context.getEquipmentRenderer()));
		this.addLayer(new BeeStingerLayer(this, context));
		//?} else {
		/*super(context, new PlayerIllusionEntityModel<>(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM:ModelLayers.PLAYER), useSlimModel), 0.5F);

		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
		this.addLayer(new PlayerItemInHandLayer(this, context.getItemInHandRenderer()));
		this.addLayer(new ArrowLayer(context, this));
		this.addLayer(new PlayerIllusionCapeLayer(this));
		this.addLayer(new CustomHeadLayer(this, context.getModelSet(), context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer(this, context.getModelSet()));
		this.addLayer(new BeeStingerLayer(this));
		*///?}
	}

	//? >=1.21.3 {
	@Override
	protected void scale(PlayerRenderState renderState, PoseStack poseStack) {
		float scale = 0.9375F;
		poseStack.scale(scale, scale, scale);
	}

	@Override
	public PlayerRenderState createRenderState() {
		return new PlayerRenderState();
	}

	@Override
	public void extractRenderState(
		PlayerIllusionEntity playerIllusion,
		PlayerRenderState playerIllusionRenderState,
		float partialTick
	) {
		super.extractRenderState(playerIllusion, playerIllusionRenderState, partialTick);
	}
	//?} else {
	//?}

	@Override
	//? >=1.21.3 {
	protected void renderNameTag(PlayerRenderState renderState, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
	//?} else {
	/*protected void renderNameTag(PlayerIllusionEntity entity, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick)
	*///?}
	{
	}

	@Override
	//? >=1.21.3 {
	public ResourceLocation getTextureLocation(PlayerRenderState playerIllusionRenderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(PlayerIllusionEntity playerIllusion)
	*///?}
	{
		//? >=1.21.3 {
		return playerIllusionRenderState.skin.texture();
		//?} else {
		/*return playerIllusion.getSkinTextures().texture();
		*///?}
	}
}
