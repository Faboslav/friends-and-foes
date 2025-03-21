package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.entity.PlayerIllusionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PlayerIllusionElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M>
{
	private static final Identifier SKIN = Identifier.ofVanilla("textures/entity/elytra.png");
	private final ElytraEntityModel<T> elytra;

	public PlayerIllusionElytraFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
		super(context);
		this.elytra = new ElytraEntityModel(loader.getModelPart(EntityModelLayers.ELYTRA));
	}

	public void render(
		MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider,
		int i,
		T livingEntity,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
		if (itemStack.isOf(Items.ELYTRA)) {
			Identifier identifier;
			if (livingEntity instanceof PlayerIllusionEntity playerIllusionEntity) {
				SkinTextures skinTextures = playerIllusionEntity.getSkinTextures();
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

			matrixStack.push();
			matrixStack.translate(0.0F, 0.0F, 0.125F);
			this.getContextModel().copyStateTo(this.elytra);
			this.elytra.setAngles(livingEntity, f, g, j, k, l);
			VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(identifier), itemStack.hasGlint());
			this.elytra.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
			matrixStack.pop();
		}
	}
}
