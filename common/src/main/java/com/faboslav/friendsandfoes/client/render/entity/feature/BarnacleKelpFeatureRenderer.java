package com.faboslav.friendsandfoes.client.render.entity.feature;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.BarnacleEntityModel;
import com.faboslav.friendsandfoes.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.entity.BarnacleEntity;
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
public final class BarnacleKelpFeatureRenderer extends FeatureRenderer<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>>
{
	public BarnacleKelpFeatureRenderer(FeatureRendererContext<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>> featureRendererContext) {
		super(featureRendererContext);
	}

	public void render(
		MatrixStack matrixStack,
		VertexConsumerProvider vertexConsumerProvider,
		int light,
		BarnacleEntity barnacle,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		if (barnacle.isInvisible()) {
			return;
		}

		Identifier identifier = FriendsAndFoes.makeID("textures/entity/barnacle/barnacle_kelp.png");

		renderModel(
			this.getContextModel(),
			identifier,
			matrixStack,
			vertexConsumerProvider,
			light,
			barnacle,
			1.0F,
			1.0F,
			1.0F
		);
	}
}

