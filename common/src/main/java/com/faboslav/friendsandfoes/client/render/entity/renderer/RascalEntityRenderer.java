package com.faboslav.friendsandfoes.client.render.entity.renderer;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.RascalEntityModel;
import com.faboslav.friendsandfoes.entity.RascalEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class RascalEntityRenderer extends MobEntityRenderer<RascalEntity, RascalEntityModel<RascalEntity>>
{
	public RascalEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new RascalEntityModel<RascalEntity>(context.getPart(FriendsAndFoesEntityModelLayers.RASCAL_LAYER)), 0.5F);
	}

	@Override
	public Identifier getTexture(RascalEntity entity) {
		return FriendsAndFoes.makeID("textures/entity/rascal/rascal.png");
	}
}
