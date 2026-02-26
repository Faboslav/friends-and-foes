package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.PenguinEntityModel;
import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.PenguinRenderState;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? >=1.21.3 {
public class PenguinEntityRenderer extends MobRenderer<PenguinEntity, PenguinRenderState, PenguinEntityModel>
//?} else {
/*public final class PenguinEntityRenderer extends MobRenderer<PenguinEntity, PenguinEntityModel<PenguinEntity>>
*///?}
{
	private static final Identifier PENGUIN_TEXTURE = FriendsAndFoes.makeID("textures/entity/penguin/penguin.png");

	public PenguinEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new PenguinEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.PENGUIN_LAYER)), 0.5F);
	}

	//? >=1.21.3 {
	@Override
	public PenguinRenderState createRenderState() {
		return new PenguinRenderState();
	}

	@Override
	public void extractRenderState(PenguinEntity penguin, PenguinRenderState penguinRenderState, float partialTick) {
		super.extractRenderState(penguin, penguinRenderState, partialTick);
		penguinRenderState.penguin = penguin;
	}
	//?}

	@Override
	//? >=1.21.3 {
	public Identifier getTextureLocation(PenguinRenderState penguinRenderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(PenguinEntity penguin)
	*///?}
	{
		return PENGUIN_TEXTURE;
	}
}