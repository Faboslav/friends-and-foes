package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.GlareFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.GlareRenderState;
//?}

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
//? >=1.21.3 {
public class GlareEntityRenderer extends MobRenderer<GlareEntity, GlareRenderState, GlareEntityModel>
//?} else {
/*public final class GlareEntityRenderer extends MobRenderer<GlareEntity, GlareEntityModel<GlareEntity>>
*///?}
{
	private static final ResourceLocation TEXTURE = FriendsAndFoes.makeID("textures/entity/glare/glare.png");

	public GlareEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new GlareEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.GLARE_LAYER)), 0.4F);
		this.addLayer(new GlareFlowerFeatureRenderer(this));
	}

	//? >=1.21.3 {
	@Override
	public GlareRenderState createRenderState() {
		return new GlareRenderState();
	}

	@Override
	public void extractRenderState(GlareEntity glare, GlareRenderState renderState, float partialTick) {
		super.extractRenderState(glare, renderState, partialTick);
		renderState.glare = glare;
	}
	//?}

	@Override
	//? >=1.21.3 {
	public ResourceLocation getTextureLocation(GlareRenderState renderState)
	//?} else {
	/*public ResourceLocation getTextureLocation(GlareEntity glare)
	*///?}
	{
		return TEXTURE;
	}
}