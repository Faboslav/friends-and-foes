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

//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.RascalRenderState;
//?}

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
//? >=1.21.3 {
public class RascalEntityRenderer extends MobRenderer<RascalEntity, RascalRenderState, RascalEntityModel>
//?} else {
/*public final class RascalEntityRenderer extends MobRenderer<RascalEntity, RascalEntityModel<RascalEntity>>
*///?}
{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/rascal/rascal.png");

	public RascalEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new RascalEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.RASCAL_LAYER)), 0.5F);
	}

	//? >=1.21.3 {
	@Override
	public RascalRenderState createRenderState() {
		return new RascalRenderState();
	}

	@Override
	public void extractRenderState(RascalEntity rascal, RascalRenderState renderState, float partialTick) {
		super.extractRenderState(rascal, renderState, partialTick);
		renderState.rascal = rascal;
	}
	//?}

	@Override
	//? >=1.21.3 {
	public ResourceLocation getTextureLocation(RascalRenderState renderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(RascalEntity rascal)
	*///?}
	{
		return TEXTURE;
	}
}
