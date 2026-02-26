package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.MaulerEntityModel;
import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import java.util.Map;

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.MaulerRenderState;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? if >=1.21.3 {
public class MaulerEntityRenderer extends MobRenderer<MaulerEntity, MaulerRenderState, MaulerEntityModel>
//?} else {
/*public final class MaulerEntityRenderer extends MobRenderer<MaulerEntity, MaulerEntityModel<MaulerEntity>>
*///?}
{
	private static final Map<MaulerEntity.Type, Identifier> VARIANT_TEXTURE_MAP = ImmutableMap.of(
		MaulerEntity.Type.BADLANDS, FriendsAndFoes.makeID("textures/entity/mauler/mauler_badlands.png"),
		MaulerEntity.Type.DESERT, FriendsAndFoes.makeID("textures/entity/mauler/mauler_desert.png"),
		MaulerEntity.Type.SAVANNA, FriendsAndFoes.makeID("textures/entity/mauler/mauler_savanna.png")
	);

	private static final float SHADOW_RADIUS = 0.35F;

	public MaulerEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new MaulerEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MAULER_LAYER)), SHADOW_RADIUS);
	}

	//? if >=1.21.3 {
	@Override
	public MaulerRenderState createRenderState() {
		return new MaulerRenderState();
	}

	@Override
	public void extractRenderState(MaulerEntity mauler, MaulerRenderState renderState, float partialTick) {
		super.extractRenderState(mauler, renderState, partialTick);
		renderState.mauler = mauler;
	}
	//?}

	@Override
	//? if >=1.21.3 {
	protected float getShadowRadius(MaulerRenderState renderState)
	//?} else {
	/*protected float getShadowRadius(MaulerEntity mauler)
	*///?}
	{
		//? if >=1.21.3 {
		var mauler = renderState.mauler;
		 //?}

		return mauler.isBurrowedDown() ? 0.0F : SHADOW_RADIUS;
	}

	@Override
	//? if >=1.21.3 {
	public Identifier getTextureLocation(MaulerRenderState renderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(MaulerEntity mauler)
	*///?}
	{
		//? if >=1.21.3 {
		var mauler = renderState.mauler;
		//?}

		return VARIANT_TEXTURE_MAP.get(mauler.getMaulerType());
	}
}