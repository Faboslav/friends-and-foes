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
import net.minecraft.resources.ResourceLocation;

@SuppressWarnings({"rawtypes", "unchecked"})

public final class TuffGolemEntityRenderer extends MobRenderer<TuffGolemEntity, TuffGolemEntityModel<TuffGolemEntity>>

{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/tuff_golem/tuff_golem.png");

	public TuffGolemEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new TuffGolemEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.TUFF_GOLEM_LAYER)), 0.3F);
		this.addLayer(new TuffGolemClosedEyesRenderer(this));
		this.addLayer(new TuffGolemClothFeatureRenderer(this));
		this.addLayer(new TuffGolemHeldItemFeatureRenderer(
			this,

			context.getItemInHandRenderer()

		));
	}

	@Override

	public ResourceLocation getTextureLocation(TuffGolemEntity tuffGolem)

	{
		return TEXTURE;
	}
}