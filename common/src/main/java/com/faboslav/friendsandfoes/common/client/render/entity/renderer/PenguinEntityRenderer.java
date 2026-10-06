package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.PenguinEntityModel;
import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;

//? if >= 1.21.3 {
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.state.PenguinRenderState;
//?} else {
/*import net.minecraft.client.renderer.entity.MobRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
*///?}

@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
//? if >= 1.21.3 {
public class PenguinEntityRenderer extends AgeableMobRenderer<PenguinEntity, PenguinRenderState, PenguinEntityModel>
//?} else {
/*public final class PenguinEntityRenderer extends MobRenderer<PenguinEntity, PenguinEntityModel<PenguinEntity>>
*///?}
{
	private static final Identifier PENGUIN_TEXTURE = FriendsAndFoes.makeID("textures/entity/penguin/penguin.png");

	public PenguinEntityRenderer(EntityRendererProvider.Context context) {
		//? if >= 1.21.3 {
		super(context, new PenguinEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.PENGUIN_LAYER)), new PenguinEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.PENGUIN_BABY_LAYER)), 0.5F);
		//?} else {
		/*super(context, new PenguinEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.PENGUIN_LAYER)), 0.5F);
		*///?}
	}

	//? if >=1.21.3 {
	@Override
	public PenguinRenderState createRenderState() {
		return new PenguinRenderState();
	}

	@Override
	public void extractRenderState(PenguinEntity penguin, PenguinRenderState penguinRenderState, float partialTick) {
		super.extractRenderState(penguin, penguinRenderState, partialTick);
		penguinRenderState.idleAnimationState.copyFrom(penguin.idleAnimationState);
		penguinRenderState.idleWaterAnimationState.copyFrom(penguin.idleWaterAnimationState);
		penguinRenderState.wingFlapAnimationState.copyFrom(penguin.wingFlapAnimationState);
		penguinRenderState.swimProgress = penguin.getSwimProgress(partialTick);
		penguinRenderState.isSwimming = penguin.isSwimming();
		penguinRenderState.isUnderWater = penguin.isUnderWater();
	}
	//?}

	//? if < 1.21.3 {
	/*@Override
	protected void scale(PenguinEntity penguin, PoseStack poseStack, float partialTickTime) {
		//? if >= 1.21.1 {
		float scale = penguin.getAgeScale();
		//?} else {
		/^float scale = penguin.getScale();
		^///?}
		poseStack.scale(scale, scale, scale);
	}
	*///?}

	@Override
	//? if >=1.21.3 {
	public Identifier getTextureLocation(PenguinRenderState penguinRenderState)
	//?} else {
	/*public Identifier getTextureLocation(PenguinEntity penguin)
	*///?}
	{
		return PENGUIN_TEXTURE;
	}
}
