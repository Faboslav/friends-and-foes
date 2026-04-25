package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.MoobloomFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

//? if >= 26.1 {
import net.minecraft.client.renderer.block.BlockModelResolver;
import net.minecraft.client.renderer.block.model.BlockDisplayContext;
import net.minecraft.client.model.animal.cow.BabyCowModel;
//?}

//? if >=1.21.3 {
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.state.MoobloomRenderState;
//?} else {
/*import net.minecraft.client.renderer.entity.MobRenderer;
*///?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? if >=1.21.3 {
public final class MoobloomEntityRenderer extends AgeableMobRenderer<MoobloomEntity, MoobloomRenderState, CowModel>
//?} else {
/*public final class MoobloomEntityRenderer extends MobRenderer<MoobloomEntity, CowModel<MoobloomEntity>>
*///?}
{
	//? if >= 26.1 {
	public static final BlockDisplayContext BLOCK_DISPLAY_CONTEXT = BlockDisplayContext.create();
	private final BlockModelResolver blockModelResolver;
	//?}

	public MoobloomEntityRenderer(EntityRendererProvider.Context context) {
		//? if >= 26.1 {
		super(context, new CowModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MOOBLOOM_LAYER)), new BabyCowModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MOOBLOOM_BABY_LAYER)), 0.7F);
		//?} else if >=1.21.3 {
		/*super(context, new CowModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MOOBLOOM_LAYER)), new CowModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MOOBLOOM_BABY_LAYER)), 0.7F);
		*///?} else {
		/*super(context, new CowModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MOOBLOOM_LAYER)), 0.7F);
		*///?}

		//? if >= 26.1 {
		this.blockModelResolver = context.getBlockModelResolver();
		//?}

		//? if >=1.21.3 {
		this.addLayer(new MoobloomFlowerFeatureRenderer(
			this
			//? if <= 1.21.11 {
			/*, context.getBlockRenderDispatcher()
			*///?}
		));
		//?} else {
		/*this.addLayer(new MoobloomFlowerFeatureRenderer(this));
		*///?}
	}

	//? if >=1.21.3 {
	@Override
	public MoobloomRenderState createRenderState() {
		return new MoobloomRenderState();
	}

	@Override
	public void extractRenderState(MoobloomEntity moobloom, MoobloomRenderState moobloomRenderState, float partialTick) {
		super.extractRenderState(moobloom, moobloomRenderState, partialTick);
		moobloomRenderState.moobloom = moobloom;
		//? if >= 26.1 {
		this.blockModelResolver.update(moobloomRenderState.flowerModel, moobloom.getVariant().getFlower().defaultBlockState(), BLOCK_DISPLAY_CONTEXT);
		//?}
	}
	//?}

	@Override
	//? if >=1.21.3 {
	public Identifier getTextureLocation(MoobloomRenderState moobloomRenderState)
	//?} else {
	/*public Identifier getTextureLocation(MoobloomEntity moobloom)
	*///?}
	{
		//? if >=1.21.3 {
		var moobloom = moobloomRenderState.moobloom;
		//?}

		var textureId = "textures/entity/moobloom/moobloom_" + moobloom.getVariant().getName();

		//? if >= 26.1 {
		if(moobloomRenderState.isBaby) {
			textureId += "_baby";
		}
		//?}

		return FriendsAndFoes.makeID(textureId + ".png");
	}
}