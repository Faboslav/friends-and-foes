package com.faboslav.friendsandfoes.client.render.entity.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class TuffGolemHeldItemFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M>
{
	private final HeldItemRenderer heldItemRenderer;

	public TuffGolemHeldItemFeatureRenderer(FeatureRendererContext<T, M> context, HeldItemRenderer heldItemRenderer) {
		super(context);
		this.heldItemRenderer = heldItemRenderer;
	}

	public void render(
		MatrixStack arg,
		VertexConsumerProvider arg2,
		int i,
		T arg3,
		float f,
		float g,
		float h,
		float j,
		float k,
		float l
	) {
		arg.push();
		arg.translate(0.0, 0.75, -0.5);
		arg.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
		ItemStack itemStack = arg3.getEquippedStack(EquipmentSlot.MAINHAND);
		this.heldItemRenderer.renderItem(arg3, itemStack, ModelTransformation.Mode.GROUND, false, arg, arg2, i);
		arg.pop();
	}
}