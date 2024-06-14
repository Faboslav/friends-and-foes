package com.faboslav.friendsandfoes.client.render.entity.renderer;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.feature.GlareFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class GlareEntityRenderer extends MobEntityRenderer<GlareEntity, GlareEntityModel<GlareEntity>>
{
	public GlareEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new GlareEntityModel(context.getPart(FriendsAndFoesEntityModelLayers.GLARE_LAYER)), 0.4F);
		this.addFeature(new GlareFlowerFeatureRenderer(this));
	}

	@Override
	protected void scale(GlareEntity glare, MatrixStack matrixStack, float amount) {
		if (glare.isBaby() == false) {
			matrixStack.scale(0.8F, 0.8F, 0.8F);
		} else {
			matrixStack.scale(0.4F, 0.4F, 0.4F);
		}
	}

	@Override
	public Identifier getTexture(GlareEntity entity) {
		return FriendsAndFoes.makeID("textures/entity/glare/glare.png");
	}
}