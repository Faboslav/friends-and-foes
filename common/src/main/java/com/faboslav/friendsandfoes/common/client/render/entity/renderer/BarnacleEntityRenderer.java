package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.BarnacleKelpFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.BarnacleKelpHeadFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.BarnacleEntityModel;
import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.BarnacleRenderState;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? if >=1.21.3 {
public class BarnacleEntityRenderer extends MobRenderer<BarnacleEntity, BarnacleRenderState, BarnacleEntityModel>
//?} else {
/*public final class BarnacleEntityRenderer extends MobRenderer<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>>
*///?}
{
	private static final Identifier BARNACLE_TEXTURE = FriendsAndFoes.makeID("textures/entity/barnacle/barnacle.png");

	public static final float SCALE = 1.5F;

	public BarnacleEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new BarnacleEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.BARNACLE_LAYER)), 0.5F);

		this.addLayer(new BarnacleKelpFeatureRenderer(this));
		this.addLayer(new BarnacleKelpHeadFeatureRenderer(this));
	}

	@Override
	protected void scale(
		//? if >=1.21.3 {
		BarnacleRenderState barnacleRenderState,
		//?} else {
		/*BarnacleEntity barnacle,
		*///?}
		PoseStack poseStack
		//? if <1.21.3 {
		/*,float partialTickTime
		*///?}
	) {
		poseStack.scale(SCALE, SCALE, SCALE);
	}

	@Override
	//? if >=26.3 {
	public boolean shouldRender(BarnacleEntity barnacle, Frustum frustum, double cameraX, double cameraY, double cameraZ, float partialTick)
	//?} else {
	/*public boolean shouldRender(BarnacleEntity barnacle, Frustum frustum, double cameraX, double cameraY, double cameraZ)
	*///?}
	{
		if (super.shouldRender(barnacle, frustum, cameraX, cameraY, cameraZ/*? if >=26.3 {*/, partialTick/*?}*/)) {
			return true;
		}

		Entity tentacleTarget = barnacle.getTentacleTarget();

		return tentacleTarget != null && frustum.isVisible(new AABB(barnacle.position(), tentacleTarget.position()).inflate(1.0D));
	}

	//? if >=1.21.3 {
	@Override
	public BarnacleRenderState createRenderState() {
		return new BarnacleRenderState();
	}

	@Override
	public void extractRenderState(BarnacleEntity barnacle, BarnacleRenderState barnacleRenderState, float partialTick) {
		super.extractRenderState(barnacle, barnacleRenderState, partialTick);
		barnacleRenderState.idleAnimationState.copyFrom(barnacle.idleAnimationState);
		barnacleRenderState.tentacleAttackAnimationState.copyFrom(barnacle.tentacleAttackAnimationState);
		barnacleRenderState.attackAnimationState.copyFrom(barnacle.attackAnimationState);
		barnacleRenderState.isUnderWater = barnacle.isUnderWater();

		Entity tentacleTarget = barnacle.getTentacleTarget();

		if (tentacleTarget != null) {
			barnacleRenderState.tentacleTargetPosition = BarnacleEntity.getTentacleAttachPosition(tentacleTarget, partialTick);
			barnacleRenderState.tentacleExtension = barnacle.getTentacleExtension(partialTick);
		} else {
			barnacleRenderState.tentacleTargetPosition = null;
			barnacleRenderState.tentacleExtension = 0.0F;
		}
	}
	//?}

	@Override
	//? if >=1.21.3 {
	public Identifier getTextureLocation(BarnacleRenderState barnacleRenderState)
	//?} else {
	/*public Identifier getTextureLocation(BarnacleEntity barnacle)
	*///?}
	{
		return BARNACLE_TEXTURE;
	}
}
