package com.faboslav.friendsandfoes.client.render.entity;

import com.faboslav.friendsandfoes.client.render.entity.model.CopperGolemEntityModel;
import com.faboslav.friendsandfoes.config.Settings;
import com.faboslav.friendsandfoes.entity.passive.CopperGolemEntity;
import com.faboslav.friendsandfoes.registry.EntityRendererRegistry;
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
public class CopperGolemRenderer extends MobEntityRenderer<CopperGolemEntity, CopperGolemEntityModel<CopperGolemEntity>>
{
    private static final Map<Oxidizable.OxidationLevel, Identifier> OXIDISATION_TO_TEXTURE_MAP;

    static {
        OXIDISATION_TO_TEXTURE_MAP = ImmutableMap.of(
                Oxidizable.OxidationLevel.UNAFFECTED, Settings.makeID("textures/entity/copper_golem/copper_golem.png"),
                Oxidizable.OxidationLevel.EXPOSED, Settings.makeID("textures/entity/copper_golem/exposed_copper_golem.png"),
                Oxidizable.OxidationLevel.WEATHERED, Settings.makeID("textures/entity/copper_golem/weathered_copper_golem.png"),
                Oxidizable.OxidationLevel.OXIDIZED, Settings.makeID("textures/entity/copper_golem/oxidized_copper_golem.png")
        );
    }

    public CopperGolemRenderer(EntityRendererFactory.Context context) {
        super(
                context,
                new CopperGolemEntityModel(context.getPart(EntityRendererRegistry.COPPER_GOLEM_LAYER)),
                0.35f
        );
    }

    @Override
    public Identifier getTexture(CopperGolemEntity entity) {
        return OXIDISATION_TO_TEXTURE_MAP.get(entity.getOxidationLevel());
    }
}