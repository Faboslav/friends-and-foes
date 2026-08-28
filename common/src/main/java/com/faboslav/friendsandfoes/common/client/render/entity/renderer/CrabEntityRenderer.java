package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.CrabEntityModel;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

//? if >=1.21.3 {
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.state.CrabRenderState;
//?} else {
/*import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
*///?}

@SuppressWarnings({"all"})
//? if >=1.21.3 {
public class CrabEntityRenderer extends AgeableMobRenderer<CrabEntity, CrabRenderState, CrabEntityModel>
//?} else {
/*public final class CrabEntityRenderer extends MobRenderer<CrabEntity, CrabEntityModel<CrabEntity>>
*///?}
{
	private static final Identifier TEXTURE = FriendsAndFoes.makeID("textures/entity/crab/crab.png");
	private static final float SHADOW_RADIUS = 0.5F;

	public CrabEntityRenderer(EntityRendererProvider.Context context) {
		//? if >=1.21.3 {
		super(context, new CrabEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.CRAB_LAYER)), new CrabEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.CRAB_BABY_LAYER)), SHADOW_RADIUS);
		//?} else {
		/*super(context, new CrabEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.CRAB_LAYER)), SHADOW_RADIUS);
		 *///?}
	}

	//? if >= 1.21.1 {
	@Override
	protected float getShadowRadius(
		//? if >=1.21.3 {
		CrabRenderState renderState
		//?} else {
		/*CrabEntity crab
		*///?}
	) {
		//? if >=1.21.3 {
		var crab = renderState.crab;
		//?}
		var shadowRadius = super.getShadowRadius(
			//? if >=1.21.3 {
			renderState
			//?} else {
			/*crab
			*///?}
		);

		var isBaby = crab.isBaby();

		shadowRadius = shadowRadius * crab.getSize().getScaleModifier();

		if(isBaby) {
			shadowRadius = shadowRadius * 0.5F;
		}

		return shadowRadius;
	}
	//?} else {
	/*@Override
	public void render(CrabEntity crab, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
		var shadowRadius = SHADOW_RADIUS * crab.getSize().getScaleModifier();

		if (crab.isBaby()) {
			shadowRadius = shadowRadius * 0.5F;
		}

		this.shadowRadius = shadowRadius;
		super.render(crab, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
	}
	*///?}

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
		//? if >= 1.21.1 {
		float scale = crab.getAgeScale();
		//?} else {
		/^float scale = crab.getScale();
		^///?}
		poseStack.scale(scale, scale, scale);
	}
	*///?}

	@Override
		//? if >=1.21.3 {
	public Identifier getTextureLocation(CrabRenderState renderState)
		//?} else {
		/*public Identifier getTextureLocation(CrabEntity crab)
		 *///?}
	{
		return TEXTURE;
	}
}