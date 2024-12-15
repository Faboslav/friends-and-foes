package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.client.render.entity.feature.PlayerIllusionCapeFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.PlayerIllusionDeadmau5FeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.PlayerIllusionElytraFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.PlayerIllusionHeldItemFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.PlayerIllusionEntityModel;
import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BeeStingerLayer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.SpinAttackEffectLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;

@Environment(value = EnvType.CLIENT)
public final class PlayerIllusionEntityRenderer extends MobRenderer<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>>
{
	public PlayerIllusionEntityRenderer(EntityRendererProvider.Context ctx, boolean slim) {
		super(ctx, new PlayerIllusionEntityModel<>(ctx.bakeLayer(slim ? ModelLayers.PLAYER_SLIM:ModelLayers.PLAYER), slim), 0.5F);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidArmorModel(ctx.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_INNER_ARMOR:ModelLayers.PLAYER_INNER_ARMOR)), new HumanoidArmorModel(ctx.bakeLayer(slim ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR:ModelLayers.PLAYER_OUTER_ARMOR)), ctx.getModelManager()));
		this.addLayer(new PlayerIllusionHeldItemFeatureRenderer<>(this, ctx.getItemInHandRenderer()));
		this.addLayer(new ArrowLayer<>(ctx, this));
		this.addLayer(new PlayerIllusionDeadmau5FeatureRenderer(this));
		this.addLayer(new PlayerIllusionCapeFeatureRenderer(this));
		this.addLayer(new CustomHeadLayer<>(this, ctx.getModelSet(), ctx.getItemInHandRenderer()));
		this.addLayer(new PlayerIllusionElytraFeatureRenderer<>(this, ctx.getModelSet()));
		this.addLayer(new SpinAttackEffectLayer(this, ctx.getModelSet()));
		this.addLayer(new BeeStingerLayer<>(this));
	}

	public void render(
		PlayerIllusionEntity playerIllusionEntity,
		float f,
		float g,
		PoseStack matrixStack,
		MultiBufferSource vertexConsumerProvider,
		int i
	) {
		this.setModelPose(playerIllusionEntity);
		super.render(playerIllusionEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	public Vec3 getPositionOffset(PlayerIllusionEntity playerIllusionEntity, float f) {
		return playerIllusionEntity.isCrouching() ? new Vec3(0.0, -0.125, 0.0):super.getRenderOffset(playerIllusionEntity, f);
	}

	private void setModelPose(PlayerIllusionEntity player) {
		PlayerModel<PlayerIllusionEntity> playerEntityModel = this.getModel();
		if (player.isSpectator()) {
			playerEntityModel.setAllVisible(false);
			playerEntityModel.head.visible = true;
			playerEntityModel.hat.visible = true;
		} else {
			playerEntityModel.setAllVisible(true);
			playerEntityModel.hat.visible = player.isPartVisible(PlayerModelPart.HAT);
			playerEntityModel.jacket.visible = player.isPartVisible(PlayerModelPart.JACKET);
			playerEntityModel.leftPants.visible = player.isPartVisible(PlayerModelPart.LEFT_PANTS_LEG);
			playerEntityModel.rightPants.visible = player.isPartVisible(PlayerModelPart.RIGHT_PANTS_LEG);
			playerEntityModel.leftSleeve.visible = player.isPartVisible(PlayerModelPart.LEFT_SLEEVE);
			playerEntityModel.rightSleeve.visible = player.isPartVisible(PlayerModelPart.RIGHT_SLEEVE);
			playerEntityModel.crouching = player.isCrouching();
			HumanoidModel.ArmPose armPose = getArmPose(player, InteractionHand.MAIN_HAND);
			HumanoidModel.ArmPose armPose2 = getArmPose(player, InteractionHand.OFF_HAND);
			if (armPose.isTwoHanded()) {
				armPose2 = player.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY:HumanoidModel.ArmPose.ITEM;
			}

			if (player.getMainArm() == HumanoidArm.RIGHT) {
				playerEntityModel.rightArmPose = armPose;
				playerEntityModel.leftArmPose = armPose2;
			} else {
				playerEntityModel.rightArmPose = armPose2;
				playerEntityModel.leftArmPose = armPose;
			}
		}

	}

	private static HumanoidModel.ArmPose getArmPose(PlayerIllusionEntity player, InteractionHand hand) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.isEmpty()) {
			return HumanoidModel.ArmPose.EMPTY;
		} else {
			if (player.getUsedItemHand() == hand && player.getUseItemRemainingTicks() > 0) {
				UseAnim useAction = itemStack.getUseAnimation();
				if (useAction == UseAnim.BLOCK) {
					return HumanoidModel.ArmPose.BLOCK;
				}

				if (useAction == UseAnim.BOW) {
					return HumanoidModel.ArmPose.BOW_AND_ARROW;
				}

				if (useAction == UseAnim.SPEAR) {
					return HumanoidModel.ArmPose.THROW_SPEAR;
				}

				if (useAction == UseAnim.CROSSBOW && hand == player.getUsedItemHand()) {
					return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
				}

				if (useAction == UseAnim.SPYGLASS) {
					return HumanoidModel.ArmPose.SPYGLASS;
				}

				if (useAction == UseAnim.TOOT_HORN) {
					return HumanoidModel.ArmPose.TOOT_HORN;
				}

				if (useAction == UseAnim.BRUSH) {
					return HumanoidModel.ArmPose.BRUSH;
				}
			} else if (!player.swinging && itemStack.is(Items.CROSSBOW) && CrossbowItem.isCharged(itemStack)) {
				return HumanoidModel.ArmPose.CROSSBOW_HOLD;
			}

			return HumanoidModel.ArmPose.ITEM;
		}
	}

	protected void scale(PlayerIllusionEntity playerIllusionEntity, PoseStack matrixStack, float f) {
		float g = 0.9375F;
		matrixStack.scale(0.9375F, 0.9375F, 0.9375F);
	}

	public void renderRightArm(
		PoseStack matrices,
		MultiBufferSource vertexConsumers,
		int light,
		PlayerIllusionEntity player
	) {
		this.renderArm(matrices, vertexConsumers, light, player, this.model.rightArm, this.model.rightSleeve);
	}

	public void renderLeftArm(
		PoseStack matrices,
		MultiBufferSource vertexConsumers,
		int light,
		PlayerIllusionEntity player
	) {
		this.renderArm(matrices, vertexConsumers, light, player, this.model.leftArm, this.model.leftSleeve);
	}

	private void renderArm(
		PoseStack matrices,
		MultiBufferSource vertexConsumers,
		int light,
		PlayerIllusionEntity player,
		ModelPart arm,
		ModelPart sleeve
	) {
		PlayerModel<PlayerIllusionEntity> playerEntityModel = this.getModel();
		this.setModelPose(player);
		playerEntityModel.attackTime = 0.0F;
		playerEntityModel.crouching = false;
		playerEntityModel.swimAmount = 0.0F;
		playerEntityModel.setupAnim(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		arm.xRot = 0.0F;
		ResourceLocation identifier = player.getSkinTextures().texture();
		arm.render(matrices, vertexConsumers.getBuffer(RenderType.entitySolid(identifier)), light, OverlayTexture.NO_OVERLAY);
		sleeve.xRot = 0.0F;
		sleeve.render(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(identifier)), light, OverlayTexture.NO_OVERLAY);
	}

	protected void setupTransforms(
		PlayerIllusionEntity playerIllusion,
		PoseStack matrixStack,
		float f,
		float g,
		float h,
		float i
	) {
		float j = playerIllusion.getSwimAmount(h);
		float k = playerIllusion.getViewXRot(h);
		float l;
		float m;
		if (playerIllusion.isFallFlying()) {
			super.setupRotations(playerIllusion, matrixStack, f, g, h, i);
			l = (float) playerIllusion.getFallFlyingTicks() + h;
			m = Mth.clamp(l * l / 100.0F, 0.0F, 1.0F);
			if (!playerIllusion.isAutoSpinAttack()) {
				matrixStack.mulPose(Axis.XP.rotationDegrees(m * (-90.0F - k)));
			}

			Vec3 vec3d = playerIllusion.getViewVector(h);
			Vec3 vec3d2 = playerIllusion.lerpVelocity(h);
			double d = vec3d2.horizontalDistanceSqr();
			double e = vec3d.horizontalDistanceSqr();
			if (d > 0.0 && e > 0.0) {
				double n = (vec3d2.x * vec3d.x + vec3d2.z * vec3d.z) / Math.sqrt(d * e);
				double o = vec3d2.x * vec3d.z - vec3d2.z * vec3d.x;
				matrixStack.mulPose(Axis.YP.rotation((float) (Math.signum(o) * Math.acos(n))));
			}
		} else if (j > 0.0F) {
			super.setupRotations(playerIllusion, matrixStack, f, g, h, i);
			l = playerIllusion.isInWater() ? -90.0F - k:-90.0F;
			m = Mth.lerp(j, 0.0F, l);
			matrixStack.mulPose(Axis.XP.rotationDegrees(m));
			if (playerIllusion.isVisuallySwimming()) {
				matrixStack.translate(0.0F, -1.0F, 0.3F);
			}
		} else {
			super.setupRotations(playerIllusion, matrixStack, f, g, h, i);
		}

	}

	@Override
	public ResourceLocation getTextureLocation(PlayerIllusionEntity playerIllusionEntity) {
		return playerIllusionEntity.getSkinTextures().texture();
	}

}
