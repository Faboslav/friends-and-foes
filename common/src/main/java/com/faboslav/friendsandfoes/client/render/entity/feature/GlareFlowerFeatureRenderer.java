package com.faboslav.friendsandfoes.client.render.entity.feature;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.entity.GlareEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class GlareFlowerFeatureRenderer extends FeatureRenderer<GlareEntity, GlareEntityModel<GlareEntity>>
{
	public GlareFlowerFeatureRenderer(FeatureRendererContext<GlareEntity, GlareEntityModel<GlareEntity>> featureRendererContext) {
		super(featureRendererContext);
	}

	public void render(
		MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider,
		int light,
		GlareEntity glare,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		if (glare.isInvisible()) {
			return;
		}

		String string = Formatting.strip(glare.getName().getString());

		if (
			"Anna".equals(string)
			|| glare.isTamed()
		) {
			Identifier identifier = FriendsAndFoes.makeID("textures/entity/glare/flowering_glare.png");

			renderModel(
				this.getContextModel(),
				identifier,
				matrixStack,
				vertexConsumerProvider,
				light,
				glare,
				1.0F,
				1.0F,
				1.0F
			);
		}
	}
}

