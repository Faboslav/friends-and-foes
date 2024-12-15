package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Environment(EnvType.CLIENT)
public class PlayerIllusionElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M>
{
	private static final ResourceLocation SKIN = ResourceLocation.withDefaultNamespace("textures/entity/elytra.png");
	private final ElytraModel<T> elytra;

	public PlayerIllusionElytraFeatureRenderer(RenderLayerParent<T, M> context, EntityModelSet loader) {
		super(context);
		this.elytra = new ElytraModel(loader.bakeLayer(ModelLayers.ELYTRA));
	}

	public void render(
		PoseStack matrixStack,
		MultiBufferSource vertexConsumerProvider,
		int i,
		T livingEntity,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
		if (itemStack.is(Items.ELYTRA)) {
			ResourceLocation identifier;
			if (livingEntity instanceof PlayerIllusionEntity playerIllusionEntity) {
				PlayerSkin skinTextures = playerIllusionEntity.getSkinTextures();
				if (skinTextures.elytraTexture() != null) {
					identifier = skinTextures.elytraTexture();
				} else if (skinTextures.capeTexture() != null && playerIllusionEntity.isPartVisible(PlayerModelPart.CAPE)) {
					identifier = skinTextures.capeTexture();
				} else {
					identifier = SKIN;
				}
			} else {
				identifier = SKIN;
			}

			matrixStack.pushPose();
			matrixStack.translate(0.0F, 0.0F, 0.125F);
			this.getParentModel().copyPropertiesTo(this.elytra);
			this.elytra.setupAnim(livingEntity, f, g, j, k, l);
			VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(vertexConsumerProvider, RenderType.armorCutoutNoCull(identifier), itemStack.hasFoil());
			this.elytra.renderToBuffer(matrixStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
			matrixStack.popPose();
		}
	}
}
