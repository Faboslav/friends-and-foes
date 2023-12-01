package com.faboslav.friendsandfoes.client.render.entity.feature;

import com.faboslav.friendsandfoes.entity.PlayerIllusionEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PlayerIllusionElytraFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M>
{
	private static final Identifier SKIN = new Identifier("textures/entity/elytra.png");
	private final ElytraEntityModel<T> elytra;

	public PlayerIllusionElytraFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
		super(context);
		this.elytra = new ElytraEntityModel(loader.getModelPart(EntityModelLayers.ELYTRA));
	}

	public void render(
		MatrixStack arg,
		VertexConsumerProvider arg2,
		int i,
		T arg3,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		ItemStack itemstack = arg3.getEquippedStack(EquipmentSlot.CHEST);
		if (this.shouldRender(itemstack, arg3)) {
			Identifier resourcelocation;
			if (arg3 instanceof PlayerIllusionEntity playerIllusion) {
				SkinTextures playerskin = playerIllusion.getSkinTextures();
				if (playerskin.elytraTexture() != null) {
					resourcelocation = playerskin.elytraTexture();
				} else if (playerskin.capeTexture() != null && playerIllusion.isPartVisible(PlayerModelPart.CAPE)) {
					resourcelocation = playerskin.capeTexture();
				} else {
					resourcelocation = this.getElytraTexture(itemstack, arg3);
				}
			} else {
				resourcelocation = this.getElytraTexture(itemstack, arg3);
			}

			arg.push();
			arg.translate(0.0F, 0.0F, 0.125F);
			this.getContextModel().copyStateTo(this.elytra);
			this.elytra.setAngles(arg3, f, g, j, k, l);
			VertexConsumer vertexconsumer = ItemRenderer.getArmorGlintConsumer(arg2, RenderLayer.getArmorCutoutNoCull(resourcelocation), false, itemstack.hasGlint());
			this.elytra.render(arg, vertexconsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
			arg.pop();
		}

	}

	public boolean shouldRender(ItemStack stack, T entity) {
		return stack.getItem() == Items.ELYTRA;
	}

	public Identifier getElytraTexture(ItemStack stack, T entity) {
		return SKIN;
	}
}
