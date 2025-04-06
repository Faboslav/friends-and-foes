package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.GlareFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

//? >=1.21.3 {
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.state.GlareRenderState;
//?} else {
/*import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
*///?}

@SuppressWarnings({"all"})
//? >=1.21.3 {
public class GlareEntityRenderer extends AgeableMobRenderer<GlareEntity, GlareRenderState, GlareEntityModel>
//?} else {
/*public final class GlareEntityRenderer extends MobRenderer<GlareEntity, GlareEntityModel<GlareEntity>>
*///?}
{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/glare/glare.png");

	public GlareEntityRenderer(EntityRendererProvider.Context context) {
		//? >=1.21.3 {
		super(context, new GlareEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.GLARE_LAYER)), new GlareEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.GLARE_BABY_LAYER)), 0.7F);
		//?} else {
		/*super(context, new GlareEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.GLARE_LAYER)), 0.4F);
		*///?}
		this.addLayer(new GlareFlowerFeatureRenderer(this));
	}

	//? >=1.21.3 {
	@Override
	public GlareRenderState createRenderState() {
		return new GlareRenderState();
	}

	@Override
	public void extractRenderState(GlareEntity glare, GlareRenderState renderState, float partialTick) {
		super.extractRenderState(glare, renderState, partialTick);
		renderState.glare = glare;
	}
	//?}

	//? <1.21.3 {
	/*@Override
	protected void scale(GlareEntity glare, PoseStack poseStack, float partialTickTime) {
		float scale = glare.getAgeScale();
		poseStack.scale(scale, scale, scale);
	}
	*///?}

	@Override
	//? >=1.21.3 {
	public ResourceLocation getTextureLocation(GlareRenderState renderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(GlareEntity glare)
	*///?}
	{
		return TEXTURE;
	}
}