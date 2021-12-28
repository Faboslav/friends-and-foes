package com.faboslav.friendsandfoes.client.render.entity;

import com.faboslav.friendsandfoes.client.render.entity.feature.GlareFlowerFeatureRenderer;
import com.faboslav.friendsandfoes.client.render.entity.model.GlareEntityModel;
import com.faboslav.friendsandfoes.config.Settings;
import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import com.faboslav.friendsandfoes.registry.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
public class GlareEntityRenderer extends MobEntityRenderer<GlareEntity, GlareEntityModel<GlareEntity>>
{
    public GlareEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GlareEntityModel(context.getPart(EntityRendererRegistry.GLARE_LAYER)), 0.45F);
        this.addFeature(new GlareFlowerFeatureRenderer(this));
    }

    @Override
        public Identifier getTexture(GlareEntity entity) {
        return Settings.makeID("textures/entity/glare/glare.png");
    }
}