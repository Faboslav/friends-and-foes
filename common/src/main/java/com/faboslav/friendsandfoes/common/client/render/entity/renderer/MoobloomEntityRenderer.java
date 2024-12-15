package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.MoobloomFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class MoobloomEntityRenderer extends MobRenderer<MoobloomEntity, CowModel<MoobloomEntity>>
{
	public MoobloomEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new CowModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.MOOBLOOM_LAYER)), 0.7F);
		this.addLayer(new MoobloomFlowerFeatureRenderer(this));
	}

	@Override
	public ResourceLocation getTextureLocation(MoobloomEntity entity) {
		return FriendsAndFoes.makeID("textures/entity/moobloom/moobloom_" + entity.getVariant().getName() + ".png");
	}
}