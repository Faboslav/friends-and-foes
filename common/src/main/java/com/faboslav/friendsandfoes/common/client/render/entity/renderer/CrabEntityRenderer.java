package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.CrabEntityModel;
import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.CrabRenderState;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? >=1.21.3 {
public class CrabEntityRenderer extends MobRenderer<CrabEntity, CrabRenderState, CrabEntityModel>
//?} else {
/*public final class CrabEntityRenderer extends MobRenderer<CrabEntity, CrabEntityModel<CrabEntity>>
*///?}
{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/crab/crab.png");

	public CrabEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new CrabEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.CRAB_LAYER)), 0.5F);
	}

	@Override
	//? >=1.21.3 {
	protected float getShadowRadius(CrabRenderState renderState)
	//?} else {
	/*protected float getShadowRadius(CrabEntity crab)
	*///?}
	{
		//? >=1.21.3 {
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

	//? >=1.21.3 {
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


	@Override
		//? >=1.21.3 {
	public ResourceLocation getTextureLocation(CrabRenderState renderState)
		//?} else {
		/*public ResourceLocation getTextureLocation(CrabEntity crab)
		 *///?}
	{
		return TEXTURE;
	}
}