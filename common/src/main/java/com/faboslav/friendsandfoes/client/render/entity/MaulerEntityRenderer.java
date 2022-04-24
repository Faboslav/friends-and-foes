package com.faboslav.friendsandfoes.client.render.entity;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.MaulerEntityModel;
import com.faboslav.friendsandfoes.entity.passive.MaulerEntity;
import com.faboslav.friendsandfoes.init.ModEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class MaulerEntityRenderer extends MobEntityRenderer<MaulerEntity, MaulerEntityModel<MaulerEntity>>
{
	public MaulerEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new MaulerEntityModel(context.getPart(ModEntityRenderer.MAULER_LAYER)), 0.5F);
	}

	@Override
	public Identifier getTexture(MaulerEntity mauler) {
		return FriendsAndFoes.makeID("textures/entity/mauler/" + mauler.getMaulerType().getName() + ".png");
	}
}