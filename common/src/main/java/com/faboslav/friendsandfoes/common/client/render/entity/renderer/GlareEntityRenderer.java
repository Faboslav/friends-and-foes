package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

/*? >=1.21.2 {*/

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.GlareFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.common.client.render.entity.state.GlareEntityRenderState;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public class GlareEntityRenderer extends MobEntityRenderer<GlareEntity, GlareEntityRenderState, GlareEntityModel>
{
	private static final Identifier TEXTURE = FriendsAndFoes.makeID("textures/entity/glare/glare.png");

	public GlareEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new GlareEntityModel(context.getPart(FriendsAndFoesEntityModelLayers.GLARE_LAYER)), 0.4F);
		this.addFeature(new GlareFlowerFeatureRenderer(this));
	}

	@Override
	public Identifier getTexture(GlareEntityRenderState state) {
		return TEXTURE;
	}

	protected float getShadowRadius(GlareEntityRenderState glareEntityRenderState) {
		float shadowRadius = super.getShadowRadius(glareEntityRenderState);
		return glareEntityRenderState.baby ? shadowRadius * 0.5F : shadowRadius;
	}

	public GlareEntityRenderState createRenderState() {
		return new GlareEntityRenderState();
	}

	public void updateRenderState(GlareEntity glareEntity, GlareEntityRenderState glareEntityRenderState, float f) {
		super.updateRenderState(glareEntity, glareEntityRenderState, f);
		glareEntityRenderState.isTamed = glareEntity.isTamed();
		glareEntityRenderState.isGrumpy = glareEntity.isGrumpy();
		glareEntityRenderState.isSitting = glareEntity.isSitting();
		glareEntityRenderState.eyesPositionOffset = glareEntity.getTargetEyesPositionOffset();
	}


	@Override
	protected void scale(GlareEntityRenderState renderState, MatrixStack matrixStack) {
		float scale = renderState.ageScale;
		matrixStack.scale(scale, scale, scale);
	}
}

/*?} else {*/
/*import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.GlareFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
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
	protected void scale(GlareEntity glareEntity, MatrixStack matrixStack, float f) {
		float scaleFactor = glareEntity.getScaleFactor();
		matrixStack.scale(scaleFactor, scaleFactor, scaleFactor);
	}

	@Override
	public Identifier getTexture(GlareEntity entity) {
		return FriendsAndFoes.makeID("textures/entity/glare/glare.png");
	}
}
*//*?}*/