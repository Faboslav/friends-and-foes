package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.BarnacleKelpFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.BarnacleKelpHeadFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.BarnacleEntityModel;
import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.BarnacleRenderState;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? >=1.21.3 {
public class BarnacleEntityRenderer extends MobRenderer<BarnacleEntity, BarnacleRenderState, BarnacleEntityModel>
//?} else {
/*public final class BarnacleEntityRenderer extends MobRenderer<WildfireEntity, WildfireEntityModel<WildfireEntity>>
*///?}
{
	private static final ResourceLocation BARNACLE_TEXTURE = FriendsAndFoes.makeID("textures/entity/barnacle/barnacle.png");

	public static final float SCALE = 1.5F;

	public BarnacleEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new BarnacleEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.BARNACLE_LAYER)), 0.5F);

		this.addLayer(new BarnacleKelpFeatureRenderer(this));
		this.addLayer(new BarnacleKelpHeadFeatureRenderer(this));
	}

	@Override
	protected void scale(
		//? >=1.21.3 {
		BarnacleRenderState barnacleRenderState,
		//?} else {
		/*BarnacleEntity barnacle,
		*///?}
		PoseStack poseStack
		//? <1.21.3 {
		/*,float partialTickTime
		*///?}
	) {
		poseStack.scale(SCALE, SCALE, SCALE);
	}

	//? >=1.21.3 {
	@Override
	public BarnacleRenderState createRenderState() {
		return new BarnacleRenderState();
	}

	@Override
	public void extractRenderState(BarnacleEntity barnacle, BarnacleRenderState barnacleRenderState, float partialTick) {
		super.extractRenderState(barnacle, barnacleRenderState, partialTick);
		barnacleRenderState.barnacle = barnacle;
	}
	//?}

	@Override
	//? >=1.21.3 {
	public ResourceLocation getTextureLocation(BarnacleRenderState barnacleRenderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(BarnacleEntity barnacle)
	*///?}
	{
		return BARNACLE_TEXTURE;
	}
}