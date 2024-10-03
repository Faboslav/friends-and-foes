package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.MoobloomFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.CowEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class MoobloomEntityRenderer extends MobEntityRenderer<MoobloomEntity, CowEntityModel<MoobloomEntity>>
{
	public MoobloomEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new CowEntityModel(context.getPart(FriendsAndFoesEntityModelLayers.MOOBLOOM_LAYER)), 0.7F);
		this.addFeature(new MoobloomFlowerFeatureRenderer(this));
	}

	@Override
	public Identifier getTexture(MoobloomEntity entity) {
		return FriendsAndFoes.makeID("textures/entity/moobloom/moobloom_" + entity.getVariant().getName() + ".png");
	}
}