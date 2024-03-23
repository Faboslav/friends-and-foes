package com.faboslav.friendsandfoes.client.render.entity.renderer;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.feature.BarnacleKelpFeatureRenderer;
import com.faboslav.friendsandfoes.client.render.entity.model.BarnacleEntityModel;
import com.faboslav.friendsandfoes.entity.BarnacleEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityModelLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class BarnacleEntityRenderer extends MobEntityRenderer<BarnacleEntity, BarnacleEntityModel<BarnacleEntity>>
{
	public BarnacleEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new BarnacleEntityModel<BarnacleEntity>(context.getPart(FriendsAndFoesEntityModelLayer.BARNACLE_LAYER)), 0.5F);
		this.addFeature(new BarnacleKelpFeatureRenderer(this));
	}

	@Override
	public Identifier getTexture(BarnacleEntity entity) {
		return FriendsAndFoes.makeID("textures/entity/barnacle/barnacle.png");
	}
}
