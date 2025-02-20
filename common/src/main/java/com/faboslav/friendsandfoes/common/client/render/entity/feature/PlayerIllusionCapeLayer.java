package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.client.render.entity.model.PlayerIllusionEntityModel;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

//? >=1.21.3 {
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerCapeModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.PlayerRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.EquipmentModelSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.EquipmentModel;
import net.minecraft.world.item.equipment.Equippable;

@Environment(EnvType.CLIENT)
public class PlayerIllusionCapeLayer extends RenderLayer<PlayerRenderState, PlayerIllusionEntityModel> {
	private final HumanoidModel<PlayerRenderState> model;
	private final EquipmentModelSet equipmentModels;

	public PlayerIllusionCapeLayer(RenderLayerParent<PlayerRenderState, PlayerIllusionEntityModel> renderer, EntityModelSet entityModels, EquipmentModelSet equipmentModels) {
		super(renderer);
		this.model = new PlayerCapeModel<>(entityModels.bakeLayer(ModelLayers.PLAYER_CAPE));
		this.equipmentModels = equipmentModels;
	}

	private boolean hasLayer(ItemStack stack, EquipmentModel.LayerType layerType) {
		Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
		if (equippable != null && !equippable.model().isEmpty()) {
			EquipmentModel equipmentModel = this.equipmentModels.get((ResourceLocation)equippable.model().get());
			return !equipmentModel.getLayers(layerType).isEmpty();
		} else {
			return false;
		}
	}

	public void render(PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, PlayerRenderState renderState, float yRot, float xRot) {
		if (!renderState.isInvisible && renderState.showCape) {
			PlayerSkin playerSkin = renderState.skin;
			if (playerSkin.capeTexture() != null) {
				if (!this.hasLayer(renderState.chestItem, EquipmentModel.LayerType.WINGS)) {
					poseStack.pushPose();
					if (this.hasLayer(renderState.chestItem, EquipmentModel.LayerType.HUMANOID)) {
						poseStack.translate(0.0F, -0.053125F, 0.06875F);
					}

					VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.entitySolid(playerSkin.capeTexture()));
					this.getParentModel().copyPropertiesTo(this.model);
					this.model.setupAnim(renderState);
					this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY);
					poseStack.popPose();
				}
			}
		}
	}
}
//?} else {

/*import com.faboslav.friendsandfoes.common.client.render.entity.model.PlayerIllusionEntityModel;
import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
public class PlayerIllusionCapeLayer extends RenderLayer<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>>
{
	public PlayerIllusionCapeLayer(RenderLayerParent<PlayerIllusionEntity, PlayerIllusionEntityModel<PlayerIllusionEntity>> renderer) {
		super(renderer);
	}

	public void render(
		PoseStack poseStack,
		MultiBufferSource buffer,
		int packedLight,
		PlayerIllusionEntity livingEntity,
		float limbSwing,
		float limbSwingAmount,
		float partialTicks,
		float ageInTicks,
		float netHeadYaw,
		float headPitch
	) {
		if (!livingEntity.isInvisible() && livingEntity.isPartVisible(PlayerModelPart.CAPE)) {
			PlayerSkin playerSkin = livingEntity.getSkinTextures();
			if (playerSkin.capeTexture() != null) {
				ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
				if (!itemStack.is(Items.ELYTRA)) {
					poseStack.pushPose();
					poseStack.translate(0.0F, 0.0F, 0.125F);
					double d = Mth.lerp(partialTicks, livingEntity.prevCapeX, livingEntity.capeX) - Mth.lerp(partialTicks, livingEntity.xo, livingEntity.getX());
					double e = Mth.lerp(partialTicks, livingEntity.prevCapeY, livingEntity.capeY) - Mth.lerp(partialTicks, livingEntity.yo, livingEntity.getY());
					double f = Mth.lerp(partialTicks, livingEntity.prevCapeZ, livingEntity.capeZ) - Mth.lerp(partialTicks, livingEntity.zo, livingEntity.getZ());
					float g = Mth.rotLerp(partialTicks, livingEntity.yBodyRotO, livingEntity.yBodyRot);
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

					float m = Mth.lerp(partialTicks, livingEntity.prevStrideDistance, livingEntity.strideDistance);
					j += Mth.sin(Mth.lerp(partialTicks, livingEntity.walkDistO, livingEntity.walkDist) * 6.0F) * 32.0F * m;
					if (livingEntity.isCrouching()) {
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
