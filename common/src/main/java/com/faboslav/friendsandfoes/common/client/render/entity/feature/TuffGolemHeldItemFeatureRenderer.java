package com.faboslav.friendsandfoes.common.client.render.entity.feature;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public final class TuffGolemHeldItemFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M>
{
	private final HeldItemRenderer heldItemRenderer;
	private final ItemRenderer itemRenderer;

	public TuffGolemHeldItemFeatureRenderer(
		FeatureRendererContext<T, M> context,
		HeldItemRenderer heldItemRenderer,
		ItemRenderer itemRenderer
	) {
		super(context);
		this.heldItemRenderer = heldItemRenderer;
		this.itemRenderer = itemRenderer;
	}

	public void render(
		MatrixStack matrices,
		VertexConsumerProvider vertexConsumers,
		int light,
		T tuffGolem,
		float limbAngle,
		float limbDistance,
		float tickDelta,
		float animationProgress,
		float headYaw,
		float headPitch
	) {
		if (
			tuffGolem.isDead()
			|| ((TuffGolemEntity) tuffGolem).isHoldingItem() == false
		) {
			return;
		}

		ItemStack itemStack = tuffGolem.getEquippedStack(EquipmentSlot.MAINHAND);
		BakedModel itemBakedModel = this.itemRenderer.getModel(itemStack, null, null, tuffGolem.getId());
		float yItemOffset = itemBakedModel.getTransformation().getTransformation(ModelTransformation.Mode.GROUND).scale.getY();
		float levitationOffset = MathHelper.sin(((float) tuffGolem.age + tickDelta) / 10.0F + 3.1415927F) * 0.1F + 0.1F;
		float yOffset = levitationOffset + (0.85F - yItemOffset * 0.5F);
		float rotationAngle = (float) Math.toDegrees((animationProgress * 0.05F) % (2.0F * (float) Math.PI));
		matrices.push();
		matrices.translate(0.0, yOffset, -0.575);
		matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
		matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(rotationAngle));

		this.heldItemRenderer.renderItem(
			tuffGolem,
			itemStack,
			ModelTransformation.Mode.GROUND,
			false,
			matrices,
			vertexConsumers,
			light
		);
		matrices.pop();
	}
}