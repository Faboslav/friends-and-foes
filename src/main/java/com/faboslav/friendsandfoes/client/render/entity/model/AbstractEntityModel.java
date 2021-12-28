package com.faboslav.friendsandfoes.client.render.entity.model;

import java.util.HashMap;
import java.util.Map;

import com.faboslav.friendsandfoes.mixin.ModelPartAccessor;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
abstract public class AbstractEntityModel <E extends Entity> extends SinglePartEntityModel<E>
{
    protected final Map<String, ModelTransform> defaultModelTransforms;
    protected final ModelPart root;

    protected AbstractEntityModel(ModelPart root) {
        this.root = root;
        this.defaultModelTransforms = new HashMap<>();
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    protected void setCurrentModelTransforms(
            Map<String, ModelTransform> modelTransforms,
            String modelPartName,
            ModelPart modelPart
    ) {
        modelTransforms.put(modelPartName, modelPart.getTransform());

        ModelPartAccessor modelPartAccessor = ((ModelPartAccessor) (Object) modelPart);
        modelPartAccessor.getChildren().forEach((childrenModelPartName, childrenModelPart) -> {
            this.setCurrentModelTransforms(modelTransforms, childrenModelPartName, childrenModelPart);
        });
    }

    protected void applyModelTransforms(
            Map<String, ModelTransform> modelTransforms,
            String modelPartName,
            ModelPart modelPart
    ) {
        ModelTransform defaultModelTransform = modelTransforms.getOrDefault(modelPartName, null);
        if (defaultModelTransform != null) {
            modelPart.setTransform(defaultModelTransform);
        }

        ModelPartAccessor modelPartAccessor = ((ModelPartAccessor) (Object) modelPart);
        modelPartAccessor.getChildren().forEach((childrenModelPartName, childrenModelPart) -> {
            this.applyModelTransforms(
                    modelTransforms,
                    childrenModelPartName,
                    childrenModelPart
            );
        });
    }
}
