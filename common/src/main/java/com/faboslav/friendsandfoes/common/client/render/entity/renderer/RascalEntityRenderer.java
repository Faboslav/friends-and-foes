package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.RascalEntityModel;
import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings({"rawtypes", "unchecked"})

public final class RascalEntityRenderer extends MobRenderer<RascalEntity, RascalEntityModel<RascalEntity>>

{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/rascal/rascal.png");

	public RascalEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new RascalEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.RASCAL_LAYER)), 0.5F);
	}

	@Override

	public ResourceLocation getTextureLocation(RascalEntity rascal)

	{
		return TEXTURE;
	}
}
