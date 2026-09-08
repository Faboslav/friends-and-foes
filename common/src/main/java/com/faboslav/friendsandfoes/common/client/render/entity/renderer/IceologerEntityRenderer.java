package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.feature.IceologerItemInHandLayer;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import net.minecraft.client.model.monster.illager.IllagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.monster.illager.SpellcasterIllager;

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.IceologerRenderState;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? if >=1.21.3 {
public class IceologerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T, IceologerRenderState>
//?} else {
/*public final class IceologerEntityRenderer<T extends SpellcasterIllager> extends IllagerRenderer<T>
*///?}
{
	private static final Identifier TEXTURE = FriendsAndFoes.makeID("textures/entity/illager/iceologer.png");

	public IceologerEntityRenderer(Context context) {
		super(context, new IllagerModel<>(context.bakeLayer(FriendsAndFoesEntityModelLayers.ICEOLOGER_LAYER)), 0.5F);

		//? if >=1.21.3 {
		this.addLayer(new IceologerItemInHandLayer(this));
		//?} else {
		/*this.addLayer(new IceologerItemInHandLayer<>(this, context.getItemInHandRenderer()));
		*///?}

		this.model.getHat().visible = true;
	}

	//? if >=1.21.3 {
	@Override
	public IceologerRenderState createRenderState() {
		return new IceologerRenderState();
	}

	@Override
	public void extractRenderState(T iceologer, IceologerRenderState renderState, float partialTick) {
		super.extractRenderState(iceologer, renderState, partialTick);
		renderState.isCastingSpell = iceologer.isCastingSpell();
	}
	//?}

	@Override
	//? if >=1.21.3 {
	public Identifier getTextureLocation(IceologerRenderState renderState)
	//?} else {
	/*public Identifier getTextureLocation(T iceologer)
	*///?}
	{
		return TEXTURE;
	}
}