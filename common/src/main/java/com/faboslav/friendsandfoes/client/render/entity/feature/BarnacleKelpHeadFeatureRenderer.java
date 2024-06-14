package com.faboslav.friendsandfoes.client.render.entity.feature;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.BarnacleEntityModel;
import com.faboslav.friendsandfoes.entity.BarnacleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class BarnacleKelpHeadFeatureRenderer extends FeatureRenderer<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>>
{
	public BarnacleKelpHeadFeatureRenderer(FeatureRendererContext<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>> featureRendererContext) {
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

		Identifier identifier = FriendsAndFoes.makeID("textures/entity/barnacle/barnacle_kelp_head_1.png");

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

