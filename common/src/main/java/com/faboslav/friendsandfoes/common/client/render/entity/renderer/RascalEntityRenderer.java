package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.RascalEntityModel;
import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class RascalEntityRenderer extends MobRenderer<RascalEntity, RascalEntityModel<RascalEntity>>
{
	public RascalEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new RascalEntityModel<RascalEntity>(context.bakeLayer(FriendsAndFoesEntityModelLayers.RASCAL_LAYER)), 0.5F);
	}

	@Override
	public ResourceLocation getTextureLocation(RascalEntity entity) {
		return FriendsAndFoes.makeID("textures/entity/rascal/rascal.png");
	}
}
