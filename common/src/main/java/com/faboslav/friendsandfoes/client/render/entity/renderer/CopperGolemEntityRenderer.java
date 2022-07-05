package com.faboslav.friendsandfoes.client.render.entity.renderer;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.model.CopperGolemEntityModel;
import com.faboslav.friendsandfoes.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityModelLayer;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Oxidizable;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

import java.util.Map;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public final class CopperGolemEntityRenderer extends MobEntityRenderer<CopperGolemEntity, CopperGolemEntityModel<CopperGolemEntity>>
{
	private static final Map<Oxidizable.OxidationLevel, Identifier> OXIDATION_TO_TEXTURE_MAP;

	static {
		OXIDATION_TO_TEXTURE_MAP = ImmutableMap.of(
			Oxidizable.OxidationLevel.UNAFFECTED, FriendsAndFoes.makeID("textures/entity/copper_golem/copper_golem.png"),
			Oxidizable.OxidationLevel.EXPOSED, FriendsAndFoes.makeID("textures/entity/copper_golem/exposed_copper_golem.png"),
			Oxidizable.OxidationLevel.WEATHERED, FriendsAndFoes.makeID("textures/entity/copper_golem/weathered_copper_golem.png"),
			Oxidizable.OxidationLevel.OXIDIZED, FriendsAndFoes.makeID("textures/entity/copper_golem/oxidized_copper_golem.png")
		);
	}

	public CopperGolemEntityRenderer(EntityRendererFactory.Context context) {
		super(
			context,
			new CopperGolemEntityModel(context.getPart(FriendsAndFoesEntityModelLayer.COPPER_GOLEM_LAYER)),
			0.35f
		);
	}

	@Override
	public Identifier getTexture(CopperGolemEntity entity) {
		return OXIDATION_TO_TEXTURE_MAP.get(entity.getOxidationLevel());
	}
}