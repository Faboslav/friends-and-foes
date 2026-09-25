package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.TuffGolemClosedEyesRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.TuffGolemClothFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.TuffGolemHeldItemFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.TuffGolemEntityModel;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.TuffGolemRenderState;
//?}

//? if >=26.3 {
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? if >=1.21.3 {
public final class TuffGolemEntityRenderer extends MobRenderer<TuffGolemEntity, TuffGolemRenderState, TuffGolemEntityModel>
//?} else {
/*public final class TuffGolemEntityRenderer extends MobRenderer<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>>
*///?}
{
	private static final Identifier TEXTURE = FriendsAndFoes.makeID("textures/entity/tuff_golem/tuff_golem.png");

	public TuffGolemEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new TuffGolemEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.TUFF_GOLEM_LAYER)), 0.3F);
		this.addLayer(new TuffGolemClosedEyesRenderer(this));
		this.addLayer(new TuffGolemClothFeatureRenderer(this));
		this.addLayer(new TuffGolemHeldItemFeatureRenderer(
			this
			//? if >=1.21.3 && <26.3 {
			/*, context.getEntityRenderDispatcher().getItemInHandRenderer()
			*///?} else if <1.21.3 {
			/*, context.getItemInHandRenderer()
			*///?}
		));
	}

	//? if >=1.21.3 {
	@Override
	public TuffGolemRenderState createRenderState() {
		return new TuffGolemRenderState();
	}

	@Override
	public void extractRenderState(TuffGolemEntity tuffGolem, TuffGolemRenderState renderState, float partialTick) {
		super.extractRenderState(tuffGolem, renderState, partialTick);
		//? if <26.3 {
		/*renderState.tuffGolem = tuffGolem;
		*///?}
		renderState.showItemAnimationState.copyFrom(tuffGolem.showItemAnimationState);
		renderState.hideItemAnimationState.copyFrom(tuffGolem.hideItemAnimationState);
		renderState.sleepAnimationState.copyFrom(tuffGolem.sleepAnimationState);
		renderState.sleepWithItemAnimationState.copyFrom(tuffGolem.sleepWithItemAnimationState);
		renderState.wakeAnimationState.copyFrom(tuffGolem.wakeAnimationState);
		renderState.wakeWithItemAnimationState.copyFrom(tuffGolem.wakeWithItemAnimationState);
		renderState.wakeAndShowItemAnimationState.copyFrom(tuffGolem.wakeAndShowItemAnimationState);
		renderState.wakeAndHideItemAnimationState.copyFrom(tuffGolem.wakeAndHideItemAnimationState);
		renderState.color = tuffGolem.getColor();
		renderState.movementSpeedModifier = tuffGolem.getMovementSpeedModifier();
		renderState.isHoldingItem = tuffGolem.isHoldingItem();
		renderState.isInSleepingPose = tuffGolem.isInSleepingPose();
		renderState.isDeadOrDying = tuffGolem.isDeadOrDying();
		renderState.partialTick = partialTick;
		//? if >=26.3 {
		this.itemModelResolver.updateForLiving(renderState.heldItem, tuffGolem.getItemBySlot(EquipmentSlot.MAINHAND), ItemDisplayContext.GROUND, tuffGolem);
		//?}
	}
	//?}

	@Override
	//? if >=1.21.3 {
	public Identifier getTextureLocation(TuffGolemRenderState renderState)
	//?} else {
	/*public Identifier getTextureLocation(TuffGolemEntity tuffGolem)
	 *///?}
	{
		return TEXTURE;
	}
}