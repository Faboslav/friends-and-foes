package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.MaulerEntityModel;
import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import java.util.Map;

@SuppressWarnings({"rawtypes", "unchecked"})

public final class MaulerEntityRenderer extends MobRenderer<MaulerEntity, MaulerEntityModel<MaulerEntity>>

{
	private static final Map<MaulerEntity.Type, ResourceLocation> VARIANT_TEXTURE_MAP = ImmutableMap.of(
		MaulerEntity.Type.BADLANDS, FriendsAndFoes.makeID("textures/entity/mauler/mauler_badlands.png"),
		MaulerEntity.Type.DESERT, FriendsAndFoes.makeID("textures/entity/mauler/mauler_desert.png"),
		MaulerEntity.Type.SAVANNA, FriendsAndFoes.makeID("textures/entity/mauler/mauler_savanna.png")
	);

	private static final float SHADOW_RADIUS = 0.35F;

	public MaulerEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new MaulerEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MAULER_LAYER)), SHADOW_RADIUS);
	}

	@Override

	protected float getShadowRadius(MaulerEntity mauler)

	{

		return mauler.isBurrowedDown() ? 0.0F : SHADOW_RADIUS;
	}

	@Override

	public ResourceLocation getTextureLocation(MaulerEntity mauler)

	{

		return VARIANT_TEXTURE_MAP.get(mauler.getMaulerType());
	}
}