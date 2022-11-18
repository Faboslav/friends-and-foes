package com.faboslav.friendsandfoes.client.render.entity.renderer;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.WildfireEntityModel;
import com.faboslav.friendsandfoes.entity.WildfireEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityModelLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class WildfireEntityRenderer extends MobEntityRenderer<WildfireEntity, WildfireEntityModel<WildfireEntity>>
{
	public static final float SCALE = 1.6F;

	public WildfireEntityRenderer(EntityRendererFactory.Context context) {
		super(context, new WildfireEntityModel(context.getPart(FriendsAndFoesEntityModelLayer.WILDFIRE_LAYER)), 0.35F);
	}

	@Override
	protected int getBlockLight(WildfireEntity wildfire, BlockPos blockPos) {
		return 15;
	}

	@Override
	protected void scale(WildfireEntity wildfire, MatrixStack matrixStack, float f) {
		matrixStack.scale(SCALE, SCALE, SCALE);
	}

	@Override
	public Identifier getTexture(WildfireEntity wildfire) {
		return FriendsAndFoes.makeID("textures/entity/wildfire/wildfire.png");
	}
}