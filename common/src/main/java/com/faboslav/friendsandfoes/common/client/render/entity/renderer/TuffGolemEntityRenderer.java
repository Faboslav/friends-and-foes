package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.TuffGolemClosedEyesRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.TuffGolemClothFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.TuffGolemHeldItemFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.TuffGolemEntityModel;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.TuffGolemRenderState;
//?}

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
//? >=1.21.3 {
public final class TuffGolemEntityRenderer extends MobRenderer<TuffGolemEntity, TuffGolemRenderState, TuffGolemEntityModel>
//?} else {
/*public final class TuffGolemEntityRenderer extends MobRenderer<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>>
*///?}
{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/tuff_golem/tuff_golem.png");

	public TuffGolemEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new TuffGolemEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.TUFF_GOLEM_LAYER)), 0.3F);
		this.addLayer(new TuffGolemClosedEyesRenderer(this));
		this.addLayer(new TuffGolemClothFeatureRenderer(this));
		this.addLayer(new TuffGolemHeldItemFeatureRenderer(
			this,
			//? >=1.21.3 {
			context.getEntityRenderDispatcher().getItemInHandRenderer(),
			//?} else {
			/*context.getItemInHandRenderer(),
			*///?}
			context.getItemRenderer())
		);
	}

	//? >=1.21.3 {
	@Override
	public TuffGolemRenderState createRenderState() {
		return new TuffGolemRenderState();
	}

	@Override
	public void extractRenderState(TuffGolemEntity tuffGolem, TuffGolemRenderState renderState, float partialTick) {
		super.extractRenderState(tuffGolem, renderState, partialTick);
		renderState.tuffGolem = tuffGolem;
		renderState.partialTick = partialTick;
	}
	//?}

	@Override
	//? >=1.21.3 {
	public ResourceLocation getTextureLocation(TuffGolemRenderState renderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(TuffGolemEntity tuffGolem)
	 *///?}
	{
		return TEXTURE;
	}
}