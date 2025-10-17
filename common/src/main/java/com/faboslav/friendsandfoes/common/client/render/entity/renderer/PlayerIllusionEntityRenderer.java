//? if <= 1.21.8 {
/*package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

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

//? if >=1.21.3 {
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import com.faboslav.friendsandfoes.common.client.render.entity.state.PlayerIllusionRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.*;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ReadOnlyScoreInfo;
import net.minecraft.world.scores.Scoreboard;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? if >=1.21.3 {
public final class PlayerIllusionEntityRenderer extends LivingEntityRenderer<PlayerIllusionEntity, PlayerRenderState, PlayerIllusionEntityModel>
//?} else {
/^public final class PlayerIllusionEntityRenderer extends LivingEntityRenderer<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>>
^///?}
{
	public PlayerIllusionEntityRenderer(EntityRendererProvider.Context context, boolean useSlimModel) {
		//? if >=1.21.3 {
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
		/^super(context, new PlayerIllusionEntityModel<>(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM:ModelLayers.PLAYER), useSlimModel), 0.5F);

		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(context.bakeLayer(useSlimModel ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR)), context.getModelManager()));
		this.addLayer(new PlayerItemInHandLayer(this, context.getItemInHandRenderer()));
		this.addLayer(new ArrowLayer(context, this));
		this.addLayer(new PlayerIllusionCapeLayer(this));
		this.addLayer(new CustomHeadLayer(this, context.getModelSet(), context.getItemInHandRenderer()));
		this.addLayer(new ElytraLayer(this, context.getModelSet()));
		this.addLayer(new BeeStingerLayer(this));
		^///?}
	}

	//? if >=1.21.3 {
	@Override
	protected void scale(PlayerRenderState renderState, PoseStack poseStack) {
		float scale = 0.9375F;
		poseStack.scale(scale, scale, scale);
	}

	@Override
	public PlayerIllusionRenderState createRenderState() {
		return new PlayerIllusionRenderState();
	}

	@Override
	public void extractRenderState(
		PlayerIllusionEntity playerIllusion,
		PlayerRenderState playerIllusionRenderState,
		float partialTick
	) {
		super.extractRenderState(playerIllusion, playerIllusionRenderState, partialTick);

		HumanoidMobRenderer.extractHumanoidRenderState(playerIllusion, playerIllusionRenderState, partialTick, this.itemModelResolver);
		playerIllusionRenderState.leftArmPose = getArmPose(playerIllusion, HumanoidArm.LEFT);
		playerIllusionRenderState.rightArmPose = getArmPose(playerIllusion, HumanoidArm.RIGHT);
		playerIllusionRenderState.skin = PlayerSkinProvider.getSkinTextures(playerIllusion);
		playerIllusionRenderState.arrowCount = playerIllusion.getArrowCount();
		playerIllusionRenderState.stingerCount = playerIllusion.getStingerCount();
		playerIllusionRenderState.useItemRemainingTicks = playerIllusion.getUseItemRemainingTicks();
		playerIllusionRenderState.showHat = playerIllusion.isPartVisible(PlayerModelPart.HAT);
		playerIllusionRenderState.showJacket = playerIllusion.isPartVisible(PlayerModelPart.JACKET);
		playerIllusionRenderState.showLeftPants = playerIllusion.isPartVisible(PlayerModelPart.LEFT_PANTS_LEG);
		playerIllusionRenderState.showRightPants =playerIllusion.isPartVisible(PlayerModelPart.RIGHT_PANTS_LEG);
		playerIllusionRenderState.showLeftSleeve = playerIllusion.isPartVisible(PlayerModelPart.LEFT_SLEEVE);
		playerIllusionRenderState.showRightSleeve = playerIllusion.isPartVisible(PlayerModelPart.RIGHT_SLEEVE);
		playerIllusionRenderState.showCape = playerIllusion.isPartVisible(PlayerModelPart.CAPE);
	}

	private static HumanoidModel.ArmPose getArmPose(PlayerIllusionEntity playerIllusion, HumanoidArm humanoidArm) {
		ItemStack itemStack = playerIllusion.getItemInHand(InteractionHand.MAIN_HAND);
		ItemStack itemStack2 = playerIllusion.getItemInHand(InteractionHand.OFF_HAND);
		HumanoidModel.ArmPose armPose = getArmPose(playerIllusion, itemStack, InteractionHand.MAIN_HAND);
		HumanoidModel.ArmPose armPose2 = getArmPose(playerIllusion, itemStack2, InteractionHand.OFF_HAND);
		if (armPose.isTwoHanded()) {
			armPose2 = itemStack2.isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
		}

		return playerIllusion.getMainArm() == humanoidArm ? armPose : armPose2;
	}

	private static HumanoidModel.ArmPose getArmPose(PlayerIllusionEntity playerIllusion, ItemStack itemStack, InteractionHand interactionHand) {
		if (itemStack.isEmpty()) {
			return HumanoidModel.ArmPose.EMPTY;
		} else if (!playerIllusion.swinging && itemStack.is(Items.CROSSBOW) && CrossbowItem.isCharged(itemStack)) {
			return HumanoidModel.ArmPose.CROSSBOW_HOLD;
		} else {
			if (playerIllusion.getUsedItemHand() == interactionHand && playerIllusion.getUseItemRemainingTicks() > 0) {
				ItemUseAnimation itemUseAnimation = itemStack.getUseAnimation();
				if (itemUseAnimation == ItemUseAnimation.BLOCK) {
					return HumanoidModel.ArmPose.BLOCK;
				}

				if (itemUseAnimation == ItemUseAnimation.BOW) {
					return HumanoidModel.ArmPose.BOW_AND_ARROW;
				}

				if (itemUseAnimation == ItemUseAnimation.SPEAR) {
					return HumanoidModel.ArmPose.THROW_SPEAR;
				}

				if (itemUseAnimation == ItemUseAnimation.CROSSBOW) {
					return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
				}

				if (itemUseAnimation == ItemUseAnimation.SPYGLASS) {
					return HumanoidModel.ArmPose.SPYGLASS;
				}

				if (itemUseAnimation == ItemUseAnimation.TOOT_HORN) {
					return HumanoidModel.ArmPose.TOOT_HORN;
				}

				if (itemUseAnimation == ItemUseAnimation.BRUSH) {
					return HumanoidModel.ArmPose.BRUSH;
				}
			}

			return HumanoidModel.ArmPose.ITEM;
		}
	}
	//?} else {
	//?}

	@Override
	//? if >=1.21.3 {
	protected void renderNameTag(PlayerRenderState renderState, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
	//?} else {
	/^protected void renderNameTag(PlayerIllusionEntity entity, Component displayName, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick)
	^///?}
	{
	}

	@Override
	//? if >=1.21.3 {
	public ResourceLocation getTextureLocation(PlayerRenderState playerIllusionRenderState)
	//?} else {
	/^public ResourceLocation getTextureLocation(PlayerIllusionEntity playerIllusion)
	^///?}
	{
		//? if >=1.21.3 {
		return playerIllusionRenderState.skin.texture();
		//?} else {
		/^return PlayerSkinProvider.getSkinTextures(playerIllusion).texture();
		^///?}
	}
}
*///?}