package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.CrabEntityModel;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

//? if >=1.21.3 {
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.state.CrabRenderState;
//?} else {
/*import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
*///?}

@SuppressWarnings({"all"})
//? if >=1.21.3 {
public class CrabEntityRenderer extends AgeableMobRenderer<CrabEntity, CrabRenderState, CrabEntityModel>
//?} else {
/*public final class CrabEntityRenderer extends MobRenderer<CrabEntity, CrabEntityModel<CrabEntity>>
*///?}
{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/crab/crab.png");

	public CrabEntityRenderer(EntityRendererProvider.Context context) {
		//? if >=1.21.3 {
		super(context, new CrabEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.CRAB_LAYER)), new CrabEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.CRAB_BABY_LAYER)), 0.5F);
		//?} else {
		/*super(context, new CrabEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.CRAB_LAYER)), 0.5F);
		 *///?}
	}

	@Override
	//? if >=1.21.3 {
	protected float getShadowRadius(CrabRenderState renderState)
	//?} else {
	/*protected float getShadowRadius(CrabEntity crab)
	*///?}
	{
		//? if >=1.21.3 {
		var crab = renderState.crab;
		var shadowRadius = super.getShadowRadius(renderState);
		//?} else {
		/*var shadowRadius = super.getShadowRadius(crab);
		*///?}

		var isBaby = crab.isBaby();

		shadowRadius = shadowRadius * crab.getSize().getScaleModifier();

		if(isBaby) {
			shadowRadius = shadowRadius * 0.5F;
		}

		return shadowRadius;
	}

	//? if >=1.21.3 {
	@Override
	public CrabRenderState createRenderState() {
		return new CrabRenderState();
	}

	@Override
	public void extractRenderState(CrabEntity crab, CrabRenderState renderState, float partialTick) {
		super.extractRenderState(crab, renderState, partialTick);
		renderState.crab = crab;
	}
	//?}

	//? if <1.21.3 {
	/*@Override
	protected void scale(CrabEntity crab, PoseStack poseStack, float partialTickTime) {
		float scale = crab.getAgeScale();
		poseStack.scale(scale, scale, scale);
	}
	*///?}

	@Override
		//? if >=1.21.3 {
	public ResourceLocation getTextureLocation(CrabRenderState renderState)
		//?} else {
		/*public ResourceLocation getTextureLocation(CrabEntity crab)
		 *///?}
	{
		return TEXTURE;
	}
}