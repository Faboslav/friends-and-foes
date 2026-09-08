package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.IllusionerItemInHandLayer;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.IllusionerRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.illager.SpellcasterIllager;

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.IllusionerRenderState;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? if >=1.21.3 {
public class IllusionerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T, IllusionerRenderState>
//?} else {
/*public final class IllusionerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T>
*///?}
{
	private static final Identifier TEXTURE = FriendsAndFoes.makeID("textures/entity/illusioner/illusioner.png");

	public IllusionerEntityRenderer(Context context) {
		super(context, new IllagerModel<>(context.bakeLayer(FriendsAndFoesEntityModelLayers.ILLUSIONER_LAYER)), 0.5F);

		//? if >=1.21.3 {
		this.addLayer(new IllusionerItemInHandLayer(this));
		//?} else {
		/*this.addLayer(new IllusionerItemInHandLayer<>(this, context.getItemInHandRenderer()));
		*///?}

		this.model.getHat().visible = true;
	}

	//? if >=1.21.3 {
	@Override
	public IllusionerRenderState createRenderState() {
		return new IllusionerRenderState();
	}

	@Override
	public void extractRenderState(T illusioner, IllusionerRenderState renderState, float partialTick) {
		super.extractRenderState(illusioner, renderState, partialTick);
		renderState.isCastingSpell = illusioner.isCastingSpell();
	}
	//?}

	@Override
	//? if >=1.21.3 {
	public Identifier getTextureLocation(IllusionerRenderState renderState)
	//?} else {
	/*public Identifier getTextureLocation(T illusioner)
	*///?}
	{
		return TEXTURE;
	}
}