package com.faboslav.friendsandfoes.common.client.render.entity.feature;

//? >=1.21.4 {
import com.faboslav.friendsandfoes.common.client.render.entity.model.PlayerIllusionEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerCapeModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.EquipmentAssetManager;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.client.resources.model.EquipmentClientInfo.LayerType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;

public class PlayerIllusionCapeLayer extends RenderLayer<PlayerRenderState, PlayerIllusionEntityModel>
{
	private final HumanoidModel<PlayerRenderState> model;
	private final EquipmentAssetManager equipmentAssets;

	public PlayerIllusionCapeLayer(
		RenderLayerParent<PlayerRenderState, PlayerIllusionEntityModel> renderer,
		EntityModelSet modelSet,
		EquipmentAssetManager equipmentAssets
	) {
		super(renderer);
		this.model = new PlayerCapeModel<>(modelSet.bakeLayer(ModelLayers.PLAYER_CAPE));
		this.equipmentAssets = equipmentAssets;
	}

	private boolean hasLayer(ItemStack stack, EquipmentClientInfo.LayerType layer) {
		Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
		if (equippable != null && equippable.assetId().isPresent()) {
			EquipmentClientInfo equipmentClientInfo = this.equipmentAssets.get(equippable.assetId().get());
			return !equipmentClientInfo.getLayers(layer).isEmpty();
		} else {
			return false;
		}
	}

	public void render(
		PoseStack poseStack,
		MultiBufferSource multiBufferSource,
		int i,
		PlayerRenderState playerRenderState,
		float f,
		float g
	) {
		if (!playerRenderState.isInvisible && playerRenderState.showCape) {
			PlayerSkin playerSkin = playerRenderState.skin;
			if (playerSkin.capeTexture() != null) {
				if (!this.hasLayer(playerRenderState.chestEquipment, LayerType.WINGS)) {
					poseStack.pushPose();
					if (this.hasLayer(playerRenderState.chestEquipment, LayerType.HUMANOID)) {
						poseStack.translate(0.0F, -0.053125F, 0.06875F);
					}

					VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entitySolid(playerSkin.capeTexture()));
					this.getParentModel().copyPropertiesTo(this.model);
					this.model.setupAnim(playerRenderState);
					this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
					poseStack.popPose();
				}
			}
		}
	}
}
//?} else {

/*import com.faboslav.friendsandfoes.common.client.render.entity.model.PlayerIllusionEntityModel;
import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.faboslav.friendsandfoes.common.util.PlayerSkinProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.UUID;

public class PlayerIllusionCapeLayer extends RenderLayer<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>>
{
	public PlayerIllusionCapeLayer(RenderLayerParent<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>> renderer) {
		super(renderer);
	}

	public void render(
		PoseStack poseStack,
		MultiBufferSource buffer,
		int packedLight,
		PlayerIllusionEntity playerIllusion,
		float limbSwing,
		float limbSwingAmount,
		float partialTicks,
		float ageInTicks,
		float netHeadYaw,
		float headPitch
	) {
		if (!playerIllusion.isInvisible() && playerIllusion.isPartVisible(PlayerModelPart.CAPE)) {
			PlayerSkin playerSkin = PlayerSkinProvider.getSkinTextures(playerIllusion);
			if (playerSkin.capeTexture() != null) {
				ItemStack itemStack = playerIllusion.getItemBySlot(EquipmentSlot.CHEST);
				if (!itemStack.is(Items.ELYTRA)) {
					poseStack.pushPose();
					poseStack.translate(0.0F, 0.0F, 0.125F);
					double d = Mth.lerp(partialTicks, playerIllusion.prevCapeX, playerIllusion.capeX) - Mth.lerp(partialTicks, playerIllusion.xo, playerIllusion.getX());
					double e = Mth.lerp(partialTicks, playerIllusion.prevCapeY, playerIllusion.capeY) - Mth.lerp(partialTicks, playerIllusion.yo, playerIllusion.getY());
					double f = Mth.lerp(partialTicks, playerIllusion.prevCapeZ, playerIllusion.capeZ) - Mth.lerp(partialTicks, playerIllusion.zo, playerIllusion.getZ());
					float g = Mth.rotLerp(partialTicks, playerIllusion.yBodyRotO, playerIllusion.yBodyRot);
					double h = Mth.sin(g * (float) (Math.PI / 180.0));
					double i = -Mth.cos(g * (float) (Math.PI / 180.0));
					float j = (float)e * 10.0F;
					j = Mth.clamp(j, -6.0F, 32.0F);
					float k = (float)(d * h + f * i) * 100.0F;
					k = Mth.clamp(k, 0.0F, 150.0F);
					float l = (float)(d * i - f * h) * 100.0F;
					l = Mth.clamp(l, -20.0F, 20.0F);
					if (k < 0.0F) {
						k = 0.0F;
					}

					float m = Mth.lerp(partialTicks, playerIllusion.prevStrideDistance, playerIllusion.strideDistance);
					j += Mth.sin(Mth.lerp(partialTicks, playerIllusion.walkDistO, playerIllusion.walkDist) * 6.0F) * 32.0F * m;
					if (playerIllusion.isCrouching()) {
						j += 25.0F;
					}

					poseStack.mulPose(Axis.XP.rotationDegrees(6.0F + k / 2.0F + j));
					poseStack.mulPose(Axis.ZP.rotationDegrees(l / 2.0F));
					poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - l / 2.0F));
					VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(playerSkin.capeTexture()));
					this.getParentModel().renderCloak(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
					poseStack.popPose();
				}
			}
		}
	}
}
*///?}
