package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.WildfireEntityModel;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.WildfireRenderState;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? if >=1.21.3 {
public class WildfireEntityRenderer extends MobRenderer<WildfireEntity, WildfireRenderState, WildfireEntityModel>
//?} else {
/*public final class WildfireEntityRenderer extends MobRenderer<WildfireEntity, WildfireEntityModel<WildfireEntity>>
*///?}
{
	private static final ResourceLocation WILDFIRE_TEXTURE = FriendsAndFoes.makeID("textures/entity/wildfire/wildfire.png");

	public static final float SCALE = 1.5F;

	public WildfireEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new WildfireEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.WILDFIRE_LAYER)), 0.35F);
	}

	@Override
	protected int getBlockLightLevel(WildfireEntity wildfire, BlockPos blockPos) {
		return 15;
	}

	@Override
	protected void scale(
		//? if >=1.21.3 {
		WildfireRenderState wildfireRenderState,
		//?} else {
		/*WildfireEntity wildfire,
		*///?}
		PoseStack poseStack
		//? if <1.21.3 {
		/*,float partialTickTime
		*///?}
	) {
		poseStack.scale(SCALE, SCALE, SCALE);
	}

	//? if >=1.21.3 {
	@Override
	public WildfireRenderState createRenderState() {
		return new WildfireRenderState();
	}

	@Override
	public void extractRenderState(WildfireEntity wildfire, WildfireRenderState wildfireRenderState, float partialTick) {
		super.extractRenderState(wildfire, wildfireRenderState, partialTick);
		wildfireRenderState.wildfire = wildfire;
	}
	//?}

	@Override
	//? if >=1.21.3 {
	public ResourceLocation getTextureLocation(WildfireRenderState wildfireRenderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(WildfireEntity wildfire)
	*///?}
	{
		return WILDFIRE_TEXTURE;
	}
}