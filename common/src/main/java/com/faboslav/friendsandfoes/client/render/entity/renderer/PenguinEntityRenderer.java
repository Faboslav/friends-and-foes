package com.faboslav.friendsandfoes.client.render.entity.renderer;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.PenguinEntityModel;
import com.faboslav.friendsandfoes.entity.PenguinEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class PenguinEntityRenderer extends MobEntityRenderer<PenguinEntity, PenguinEntityModel<PenguinEntity>>
{
	private static final float SHADOW_RADIUS = 0.5F;

	public PenguinEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new PenguinEntityModel<>(context.getPart(FriendsAndFoesEntityModelLayers.PENGUIN_LAYER)), SHADOW_RADIUS);
	}

	@Override
	public Identifier getTexture(PenguinEntity penguin) {
		return FriendsAndFoes.makeID("textures/entity/penguin/penguin.png");
	}
}