package com.faboslav.friendsandfoes.client.render.entity.model;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.mob.IceologerIceChunkEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class IceologerIceChunkModel<T extends IceologerIceChunkEntity> extends SinglePartEntityModel<T>
{
	private static final String MODEL_PART_ROOT = "root";
	private static final String MODEL_PART_FIRST_FULL_BLOCK = "firstFullBlock";
	private static final String MODEL_PART_SECOND_FULL_BLOCK = "secondFullBlock";
	private static final String MODEL_PART_THIRD_FULL_BLOCK = "thirdFullBlock";
	private static final String MODEL_PART_FIRST_VERTICAL_SLAB = "firstVerticalSlab";
	private static final String MODEL_PART_SECOND_VERTICAL_SLAB = "secondVerticalSlab";

	private final ModelPart root;
	private final ModelPart firstFullBlock;
	private final ModelPart secondFullBlock;
	private final ModelPart thirdFullBlock;
	private final ModelPart firstVerticalSlab;
	private final ModelPart secondVerticalSlab;

	public IceologerIceChunkModel(ModelPart root) {
		this.root = root;
		this.firstFullBlock = this.root.getChild(MODEL_PART_FIRST_FULL_BLOCK);
		this.secondFullBlock = this.root.getChild(MODEL_PART_SECOND_FULL_BLOCK);
		this.thirdFullBlock = this.root.getChild(MODEL_PART_THIRD_FULL_BLOCK);
		this.firstVerticalSlab = this.root.getChild(MODEL_PART_FIRST_VERTICAL_SLAB);
		this.secondVerticalSlab = this.root.getChild(MODEL_PART_SECOND_VERTICAL_SLAB);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();

		root.addChild(MODEL_PART_FIRST_FULL_BLOCK, ModelPartBuilder.create().uv(0, 0).cuboid(-16.0F, 0.0F, -4.0F, 16.0F, 16.0F, 16.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		root.addChild(MODEL_PART_SECOND_FULL_BLOCK, ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, 0.0F, -4.0F, 16.0F, 16.0F, 16.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		root.addChild(MODEL_PART_THIRD_FULL_BLOCK, ModelPartBuilder.create().uv(0, 0).cuboid(-16.0F, 0.0F, -20.0F, 16.0F, 16.0F, 16.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		root.addChild(MODEL_PART_FIRST_VERTICAL_SLAB, ModelPartBuilder.create().uv(0, 32).cuboid(-16.0F, 0.0F, 12.0F, 16.0F, 16.0F, 8.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		root.addChild(MODEL_PART_SECOND_VERTICAL_SLAB, ModelPartBuilder.create().uv(0, 32).cuboid(-20.0F, 0.0F, -8.0F, 16.0F, 16.0F, 8.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(
		T iceChunk,
		float limbAngle,
		float limbDistance,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		this.secondVerticalSlab.setAngles(0.0F, -1.5708F, 0.0F);
	}

	@Override
	public void animateModel(
		T iceChunk,
		float limbAngle,
		float limbDistance,
		float tickDelta
	) {
		if(iceChunk.getTicksUntilFall() > 0) {
			iceChunk.setPositionAboveTarget(tickDelta);
		}
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}
