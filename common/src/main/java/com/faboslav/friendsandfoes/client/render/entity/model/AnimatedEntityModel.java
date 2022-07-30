package com.faboslav.friendsandfoes.client.render.entity.model;

import com.faboslav.friendsandfoes.client.render.entity.animation.ModelAnimator;
import com.faboslav.friendsandfoes.mixin.ModelPartAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.entity.Entity;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public abstract class AnimatedEntityModel<E extends Entity> extends BaseEntityModel<E>
{
	protected final ModelAnimator modelAnimator;
	protected final Map<String, ModelTransform> defaultModelTransforms;

	protected AnimatedEntityModel(ModelPart root) {
		super(root);

		this.defaultModelTransforms = new HashMap<>();

		this.setCurrentModelTransforms(
			MODEL_PART_ROOT,
			this.root
		);

		this.modelAnimator = new ModelAnimator();
	}

	protected void setCurrentModelTransforms(
		String modelPartName,
		ModelPart modelPart
	) {
		this.defaultModelTransforms.put(modelPartName, modelPart.getTransform());

		ModelPartAccessor modelPartAccessor = ((ModelPartAccessor) (Object) modelPart);
		modelPartAccessor.getChildren().forEach(this::setCurrentModelTransforms);
	}

	protected void applyModelTransforms(
		String modelPartName,
		ModelPart modelPart
	) {
		ModelTransform defaultModelTransform = this.defaultModelTransforms.get(modelPartName);
		modelPart.setTransform(defaultModelTransform);

		ModelPartAccessor modelPartAccessor = ((ModelPartAccessor) (Object) modelPart);
		modelPartAccessor.getChildren().forEach(this::applyModelTransforms);
	}
}