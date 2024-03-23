package com.faboslav.friendsandfoes.client.render.entity.renderer;

import com.faboslav.friendsandfoes.client.render.entity.feature.PlayerIllusionCapeFeatureRenderer;
import com.faboslav.friendsandfoes.client.render.entity.feature.PlayerIllusionDeadmau5FeatureRenderer;
import com.faboslav.friendsandfoes.client.render.entity.feature.PlayerIllusionElytraFeatureRenderer;
import com.faboslav.friendsandfoes.client.render.entity.feature.PlayerIllusionHeldItemFeatureRenderer;
import com.faboslav.friendsandfoes.client.render.entity.model.PlayerIllusionEntityModel;
import com.faboslav.friendsandfoes.entity.PlayerIllusionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

@Environment(value = EnvType.CLIENT)
public final class PlayerIllusionEntityRenderer extends MobEntityRenderer<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>>
{
	public PlayerIllusionEntityRenderer(EntityRendererFactory.Context ctx, boolean slim) {
		super(ctx, new PlayerIllusionEntityModel<>(ctx.getPart(slim ? EntityModelLayers.PLAYER_SLIM:EntityModelLayers.PLAYER), slim), 0.5F);
		this.addFeature(new ArmorFeatureRenderer<>(this, new BipedEntityModel(ctx.getPart(slim ? EntityModelLayers.PLAYER_SLIM_INNER_ARMOR:EntityModelLayers.PLAYER_INNER_ARMOR)), new BipedEntityModel(ctx.getPart(slim ? EntityModelLayers.PLAYER_SLIM_OUTER_ARMOR:EntityModelLayers.PLAYER_OUTER_ARMOR))));
		this.addFeature(new PlayerIllusionHeldItemFeatureRenderer<>(this, ctx.getHeldItemRenderer()));
		this.addFeature(new StuckArrowsFeatureRenderer<>(ctx, this));
		this.addFeature(new PlayerIllusionDeadmau5FeatureRenderer(this));
		this.addFeature(new PlayerIllusionCapeFeatureRenderer(this));
		this.addFeature(new HeadFeatureRenderer<>(this, ctx.getModelLoader(), ctx.getHeldItemRenderer()));
		this.addFeature(new PlayerIllusionElytraFeatureRenderer<>(this, ctx.getModelLoader()));
		this.addFeature(new TridentRiptideFeatureRenderer(this, ctx.getModelLoader()));
		this.addFeature(new StuckStingersFeatureRenderer<>(this));
	}

	public void render(
		PlayerIllusionEntity playerIllusionEntity,
		float f,
		float g,
		MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider,
		int i
	) {
		this.setModelPose(playerIllusionEntity);
		super.render(playerIllusionEntity, f, g, matrixStack, vertexConsumerProvider, i);
	}

	public Vec3d getPositionOffset(PlayerIllusionEntity playerIllusionEntity, float f) {
		return playerIllusionEntity.isInSneakingPose() ? new Vec3d(0.0, -0.125, 0.0):super.getPositionOffset(playerIllusionEntity, f);
	}

	private void setModelPose(PlayerIllusionEntity player) {
		PlayerIllusionEntityModel<PlayerIllusionEntity> playerEntityModel = this.getModel();
		if (player.isSpectator()) {
			playerEntityModel.setVisible(false);
			playerEntityModel.head.visible = true;
			playerEntityModel.hat.visible = true;
		} else {
			playerEntityModel.setVisible(true);
			playerEntityModel.hat.visible = player.isPartVisible(PlayerModelPart.HAT);
			playerEntityModel.jacket.visible = player.isPartVisible(PlayerModelPart.JACKET);
			playerEntityModel.leftPants.visible = player.isPartVisible(PlayerModelPart.LEFT_PANTS_LEG);
			playerEntityModel.rightPants.visible = player.isPartVisible(PlayerModelPart.RIGHT_PANTS_LEG);
			playerEntityModel.leftSleeve.visible = player.isPartVisible(PlayerModelPart.LEFT_SLEEVE);
			playerEntityModel.rightSleeve.visible = player.isPartVisible(PlayerModelPart.RIGHT_SLEEVE);
			playerEntityModel.sneaking = player.isInSneakingPose();
			BipedEntityModel.ArmPose armPose = getArmPose(player, Hand.MAIN_HAND);
			BipedEntityModel.ArmPose armPose2 = getArmPose(player, Hand.OFF_HAND);
			if (armPose.isTwoHanded()) {
				armPose2 = player.getOffHandStack().isEmpty() ? BipedEntityModel.ArmPose.EMPTY:BipedEntityModel.ArmPose.ITEM;
			}

			if (player.getMainArm() == Arm.RIGHT) {
				playerEntityModel.rightArmPose = armPose;
				playerEntityModel.leftArmPose = armPose2;
			} else {
				playerEntityModel.rightArmPose = armPose2;
				playerEntityModel.leftArmPose = armPose;
			}
		}

	}

	private static BipedEntityModel.ArmPose getArmPose(PlayerIllusionEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isEmpty()) {
			return BipedEntityModel.ArmPose.EMPTY;
		} else {
			if (player.getActiveHand() == hand && player.getItemUseTimeLeft() > 0) {
				UseAction useAction = itemStack.getUseAction();
				if (useAction == UseAction.BLOCK) {
					return BipedEntityModel.ArmPose.BLOCK;
				}

				if (useAction == UseAction.BOW) {
					return BipedEntityModel.ArmPose.BOW_AND_ARROW;
				}

				if (useAction == UseAction.SPEAR) {
					return BipedEntityModel.ArmPose.THROW_SPEAR;
				}

				if (useAction == UseAction.CROSSBOW && hand == player.getActiveHand()) {
					return BipedEntityModel.ArmPose.CROSSBOW_CHARGE;
				}

				if (useAction == UseAction.SPYGLASS) {
					return BipedEntityModel.ArmPose.SPYGLASS;
				}

				if (useAction == UseAction.TOOT_HORN) {
					return BipedEntityModel.ArmPose.TOOT_HORN;
				}
			} else if (!player.handSwinging && itemStack.isOf(Items.CROSSBOW) && CrossbowItem.isCharged(itemStack)) {
				return BipedEntityModel.ArmPose.CROSSBOW_HOLD;
			}

			return BipedEntityModel.ArmPose.ITEM;
		}
	}

	public Identifier getTexture(PlayerIllusionEntity playerIllusionEntity) {
		return playerIllusionEntity.getSkinTexture();
	}

	protected void scale(PlayerIllusionEntity playerIllusionEntity, MatrixStack matrixStack, float f) {
		float g = 0.9375F;
		matrixStack.scale(0.9375F, 0.9375F, 0.9375F);
	}

	public void renderRightArm(
		MatrixStack matrices,
		VertexConsumerProvider vertexConsumers,
		int light,
		PlayerIllusionEntity player
	) {
		this.renderArm(matrices, vertexConsumers, light, player, this.model.rightArm, this.model.rightSleeve);
	}

	public void renderLeftArm(
		MatrixStack matrices,
		VertexConsumerProvider vertexConsumers,
		int light,
		PlayerIllusionEntity player
	) {
		this.renderArm(matrices, vertexConsumers, light, player, this.model.leftArm, this.model.leftSleeve);
	}

	private void renderArm(
		MatrixStack matrices,
		VertexConsumerProvider vertexConsumers,
		int light,
		PlayerIllusionEntity player,
		ModelPart arm,
		ModelPart sleeve
	) {
		PlayerIllusionEntityModel<PlayerIllusionEntity> playerEntityModel = this.getModel();
		this.setModelPose(player);
		playerEntityModel.handSwingProgress = 0.0F;
		playerEntityModel.sneaking = false;
		playerEntityModel.leaningPitch = 0.0F;
		playerEntityModel.setAngles(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		arm.pitch = 0.0F;
		arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(player.getSkinTexture())), light, OverlayTexture.DEFAULT_UV);
		sleeve.pitch = 0.0F;
		sleeve.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(player.getSkinTexture())), light, OverlayTexture.DEFAULT_UV);
	}

	protected void setupTransforms(
		PlayerIllusionEntity playerIllusionEntity,
		MatrixStack matrixStack,
		float f,
		float g,
		float h
	) {
		float i = playerIllusionEntity.getLeaningPitch(h);
		float j;
		float k;
		if (playerIllusionEntity.isFallFlying()) {
			super.setupTransforms(playerIllusionEntity, matrixStack, f, g, h);
			j = (float) playerIllusionEntity.getRoll() + h;
			k = MathHelper.clamp(j * j / 100.0F, 0.0F, 1.0F);
			if (!playerIllusionEntity.isUsingRiptide()) {
				matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(k * (-90.0F - playerIllusionEntity.getPitch())));
			}

			Vec3d vec3d = playerIllusionEntity.getRotationVec(h);
			Vec3d vec3d2 = playerIllusionEntity.getVelocity();
			double d = vec3d2.horizontalLengthSquared();
			double e = vec3d.horizontalLengthSquared();
			if (d > 0.0 && e > 0.0) {
				double l = (vec3d2.x * vec3d.x + vec3d2.z * vec3d.z) / Math.sqrt(d * e);
				double m = vec3d2.x * vec3d.z - vec3d2.z * vec3d.x;
				matrixStack.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion((float) (Math.signum(m) * Math.acos(l))));
			}
		} else if (i > 0.0F) {
			super.setupTransforms(playerIllusionEntity, matrixStack, f, g, h);
			j = playerIllusionEntity.isTouchingWater() ? -90.0F - playerIllusionEntity.getPitch():-90.0F;
			k = MathHelper.lerp(i, 0.0F, j);
			matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(k));
			if (playerIllusionEntity.isInSwimmingPose()) {
				matrixStack.translate(0.0, -1.0, 0.30000001192092896);
			}
		} else {
			super.setupTransforms(playerIllusionEntity, matrixStack, f, g, h);
		}

	}
}
