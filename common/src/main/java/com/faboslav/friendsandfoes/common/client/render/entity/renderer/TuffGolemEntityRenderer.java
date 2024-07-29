package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.TuffGolemClosedEyesRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.TuffGolemClothFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.TuffGolemHeldItemFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.TuffGolemEntityModel;
import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class TuffGolemEntityRenderer extends MobEntityRenderer<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>>
{
	public TuffGolemEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new TuffGolemEntityModel(context.getPart(FriendsAndFoesEntityModelLayers.TUFF_GOLEM_LAYER)), 0.3F);
		this.addFeature(new TuffGolemClosedEyesRenderer(this));
		this.addFeature(new TuffGolemClothFeatureRenderer(this));
		this.addFeature(new TuffGolemHeldItemFeatureRenderer(this, context.getHeldItemRenderer(), context.getItemRenderer()));
	}

	@Override
	public Identifier getTexture(TuffGolemEntity entity) {
		return FriendsAndFoes.makeID("textures/entity/tuff_golem/tuff_golem.png");
	}
}