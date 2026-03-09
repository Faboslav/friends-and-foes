//? if <= 1.21.8 {
/*package com.faboslav.friendsandfoes.common.client.render.entity.renderer;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.model.CopperGolemEntityModel;
import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityModelLayers;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.WeatheringCopper;
import java.util.Map;

//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.CopperGolemRenderState;
//?}

@SuppressWarnings({"rawtypes", "unchecked"})
//? if >=1.21.3 {
public class CopperGolemEntityRenderer extends MobRenderer<CopperGolemEntity, CopperGolemRenderState, CopperGolemEntityModel>
//?} else {
/^public final class CopperGolemEntityRenderer extends MobRenderer<CopperGolemEntity, CopperGolemEntityModel<CopperGolemEntity>>
^///?}
{
	private static final Map<WeatheringCopper.WeatherState, ResourceLocation> OXIDATION_TO_TEXTURE_MAP = ImmutableMap.of(
		WeatheringCopper.WeatherState.UNAFFECTED, FriendsAndFoes.makeID("textures/entity/copper_golem/copper_golem.png"),
		WeatheringCopper.WeatherState.EXPOSED, FriendsAndFoes.makeID("textures/entity/copper_golem/exposed_copper_golem.png"),
		WeatheringCopper.WeatherState.WEATHERED, FriendsAndFoes.makeID("textures/entity/copper_golem/weathered_copper_golem.png"),
		WeatheringCopper.WeatherState.OXIDIZED, FriendsAndFoes.makeID("textures/entity/copper_golem/oxidized_copper_golem.png")
	);

	public CopperGolemEntityRenderer(EntityRendererProvider.Context context) {
		super(
			context,
			new CopperGolemEntityModel(context.bakeLayer(FriendsAndFoesEntityModelLayers.COPPER_GOLEM_LAYER)),
			0.35f
		);
	}

	//? if >=1.21.3 {
	@Override
	public CopperGolemRenderState createRenderState() {
		return new CopperGolemRenderState();
	}

	@Override
	public void extractRenderState(CopperGolemEntity copperGolem, CopperGolemRenderState renderState, float partialTick) {
		super.extractRenderState(copperGolem, renderState, partialTick);
		renderState.copperGolem = copperGolem;
	}
	//?}

	@Override
	//? if >=1.21.3 {
	public ResourceLocation getTextureLocation(CopperGolemRenderState renderState)
	//?} else {
	/^public ResourceLocation getTextureLocation(CopperGolemEntity copperGolem)
	^///?}
	{
		//? if >=1.21.3 {
		var copperGolem = renderState.copperGolem;
		//?}

		return OXIDATION_TO_TEXTURE_MAP.get(copperGolem.getOxidationLevel());
	}
}
*///?}