package com.faboslav.friendsandfoes.common.client.render.entity.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

//? if >=1.21.3 {
import net.minecraft.client.model.EntityModel;
import com.faboslav.friendsandfoes.common.client.render.entity.state.IceologerIceChunkRenderState;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
import com.faboslav.friendsandfoes.common.entity.IceologerIceChunkEntity;
*///?}

//? if >=1.21.3 {
public final class IceologerIceChunkModel extends EntityModel<IceologerIceChunkRenderState>
//?} else {
/*public final class IceologerIceChunkModel<T extends IceologerIceChunkEntity> extends HierarchicalModel<T>
*///?}
{
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
		//? if >=1.21.3 {
		super(root);
		//?}

		this.root = root;
		this.firstFullBlock = this.root.getChild(MODEL_PART_FIRST_FULL_BLOCK);
		this.secondFullBlock = this.root.getChild(MODEL_PART_SECOND_FULL_BLOCK);
		this.thirdFullBlock = this.root.getChild(MODEL_PART_THIRD_FULL_BLOCK);
		this.firstVerticalSlab = this.root.getChild(MODEL_PART_FIRST_VERTICAL_SLAB);
		this.secondVerticalSlab = this.root.getChild(MODEL_PART_SECOND_VERTICAL_SLAB);
	}

	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition root = modelData.getRoot();

		root.addOrReplaceChild(MODEL_PART_FIRST_FULL_BLOCK, CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, 0.0F, -4.0F, 16.0F, 16.0F, 16.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild(MODEL_PART_SECOND_FULL_BLOCK, CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -4.0F, 16.0F, 16.0F, 16.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild(MODEL_PART_THIRD_FULL_BLOCK, CubeListBuilder.create().texOffs(0, 0).addBox(-16.0F, 0.0F, -20.0F, 16.0F, 16.0F, 16.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild(MODEL_PART_FIRST_VERTICAL_SLAB, CubeListBuilder.create().texOffs(0, 32).addBox(-16.0F, 0.0F, 12.0F, 16.0F, 16.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));
		root.addOrReplaceChild(MODEL_PART_SECOND_VERTICAL_SLAB, CubeListBuilder.create().texOffs(0, 32).addBox(-20.0F, 0.0F, -8.0F, 16.0F, 16.0F, 8.0F), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(modelData, 64, 64);
	}

	//? if <1.21.3 {
	/*@Override
	public ModelPart root() {
		return this.root;
	}
	*///?}

	@Override
	//? if >=1.21.3 {
	public void setupAnim(IceologerIceChunkRenderState renderState)
	//?} else {
	/*public void setupAnim(T glare, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch)
	*///?}
	{
		this.secondVerticalSlab.setRotation(0.0F, -1.5708F, 0.0F);
	}
}
