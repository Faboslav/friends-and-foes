package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.MoobloomFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

//? >=1.21.3 {
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.state.MoobloomRenderState;
//?} else {
/*import net.minecraft.client.renderer.entity.MobRenderer;
*///?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? >=1.21.3 {
public final class MoobloomEntityRenderer extends AgeableMobRenderer<MoobloomEntity, MoobloomRenderState, CowModel>
//?} else {
/*public final class MoobloomEntityRenderer extends MobRenderer<MoobloomEntity, CowModel<MoobloomEntity>>
*///?}
{
	public MoobloomEntityRenderer(EntityRendererProvider.Context context) {
		//? >=1.21.3 {
		super(context, new CowModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MOOBLOOM_LAYER)), new CowModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MOOBLOOM_BABY_LAYER)), 0.7F);
		//?} else {
		/*super(context, new CowModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MOOBLOOM_LAYER)), 0.7F);
		*///?}

		//? >=1.21.3 {
		this.addLayer(new MoobloomFlowerFeatureRenderer(this, context.getBlockRenderDispatcher()));
		//?} else {
		/*this.addLayer(new MoobloomFlowerFeatureRenderer(this));
		*///?}
	}

	//? >=1.21.3 {
	@Override
	public MoobloomRenderState createRenderState() {
		return new MoobloomRenderState();
	}

	@Override
	public void extractRenderState(MoobloomEntity moobloom, MoobloomRenderState moobloomRenderState, float partialTick) {
		super.extractRenderState(moobloom, moobloomRenderState, partialTick);
		moobloomRenderState.moobloom = moobloom;
	}
	//?}

	@Override
	//? >=1.21.3 {
	public ResourceLocation getTextureLocation(MoobloomRenderState moobloomRenderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(MoobloomEntity moobloom)
	*///?}
	{
		//? >=1.21.3 {
		var moobloom = moobloomRenderState.moobloom;
		//?}

		//? >=1.21.5 {
			return FriendsAndFoes.makeID("textures/entity/moobloom/" + moobloom.getVariant().getName() + "_moobloom.png");
		//?} else {
			/*return FriendsAndFoes.makeID("textures/entity/moobloom/moobloom_" + moobloom.getVariant().getName() + ".png");
		*///?}
	}
}